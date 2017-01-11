package org.jiucai.appframework.common.excel;

import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jiucai.appframework.base.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ExcelReader, using apache poi 3.11+
 *
 * @author zhaidangwei
 */
public class ExcelReader {

    protected static Logger logger = LoggerFactory.getLogger(ExcelReader.class);

    protected static final String dateTimeFmtPattern = "yyyy-MM-dd HH:mm:ss";

    protected static final String dateFmtPattern = "yyyy-MM-dd";

    protected static final DataFormatter formatter = new DataFormatter();

    public static void main(String[] args) throws Exception {
        List<Map<String, String>> list = read("e:/test1.xls");
        List<Map<String, String>> list2 = read("e:/test1.xlsx");

        logger.info("list:\n" + list);
        logger.info("lis2t:\n" + list2);
    }

    /**
     * 读取excel文件（同时支持2003和2007格式）
     *
     * @param fis
     *            文件输入流
     * @param extension
     *            文件名扩展名: xls 或 xlsx 不区分大小写
     * @return list中的map的key是列的序号
     * @throws Exception
     *             io异常等
     */
    public static List<Map<String, String>> read(FileInputStream fis, String extension)
            throws Exception {

        Workbook wb = null;
        List<Map<String, String>> list = null;
        try {

            if ("xls".equalsIgnoreCase(extension)) {
                wb = new HSSFWorkbook(fis);
            } else if ("xlsx".equalsIgnoreCase(extension)) {
                wb = new XSSFWorkbook(fis);
            } else {
                logger.error("file is not office excel");
                throw new IllegalArgumentException("file is not office excel");
            }

            list = readWorkbook(wb);

            return list;

        } finally {
            if (null != wb) {
                wb.close();
            }
        }

    }

    /**
     * 读取excel文件（同时支持2003和2007格式）
     *
     * @param fileName
     *            文件名，绝对路径
     * @return list中的map的key是列的序号
     * @throws Exception
     *             io异常等
     */
    public static List<Map<String, String>> read(String fileName) throws Exception {
        FileInputStream fis = null;
        Workbook wb = null;
        List<Map<String, String>> list = null;
        try {
            String extension = FilenameUtils.getExtension(fileName);

            fis = new FileInputStream(fileName);
            list = read(fis, extension);

            return list;

        } finally {
            if (null != wb) {
                wb.close();
            }

            if (null != fis) {
                fis.close();
            }
        }

    }

    @SuppressWarnings("deprecation")
    private static String formatDate(Date d, String sdf) {
        String value = null;

        if (d.getSeconds() == 0 && d.getMinutes() == 0 && d.getHours() == 0) {
            value = DateTimeUtil.getFormatedDate(d, dateFmtPattern);
        } else {
            value = DateTimeUtil.getFormatedDate(d, sdf);

        }

        return value;
    }

    @SuppressWarnings("deprecation")
    protected static String getCellValue(Cell cell) {
        String value = null;
        //
        // if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
        // value = cell.getStringCellValue();
        // } else if (Cell.CELL_TYPE_BLANK == cell.getCellType()) {
        // value = "";
        // } else if (isCellDateFormatted(cell)) {
        // value = formatDate(cell);
        // } else {
        // value = formatter.formatCellValue(cell);
        // }

        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_FORMULA: // 公式
        case Cell.CELL_TYPE_NUMERIC: // 数字
            // if (DateUtil.isCellDateFormatted(cell)) { // 如果是日期类型
            //
            // value = formatDate(cell);
            // } else {
            // value = String.valueOf(cell.getNumericCellValue());
            // }

            // 所有日期格式都可以通过getDataFormat()值来判断
            // yyyy-MM-dd----- 14
            // yyyy年m月d日--- 31,177,180
            // 二○一五年三月二十三日 -- 179
            // 二○一五年三月 -- 181
            // 三月二十三日 -- 176
            // yyyy年m月------- 57,182
            // m月d日 ---------- 58,183
            // HH:mm----------- 20
            // HH:mm:ss----------- 187
            // h时mm分 ------- 32
            double doubleVal = cell.getNumericCellValue();
            short format = cell.getCellStyle().getDataFormat();
            String formatString = cell.getCellStyle().getDataFormatString();

            if (format > 0) {
                logger.info("format: " + format + "　formatString: " + formatString);
            }
            if (format == 14 || format == 31 || format == 57 || format == 58
                    || (format >= 176 && format <= 183)) {
                // 日期
                Date date = DateUtil.getJavaDate(doubleVal);
                value = formatDate(date, dateFmtPattern);
            } else if (format == 20 || format == 32 || (format >= 184 && format <= 187)) {
                // 时间
                Date date = DateUtil.getJavaDate(doubleVal);
                value = formatDate(date, "HH:mm");
            } else {
                value = String.valueOf(doubleVal);
            }

            break;
        case Cell.CELL_TYPE_STRING: // 字符串
            value = cell.getStringCellValue();
            break;
        // case Cell.CELL_TYPE_FORMULA: // 公式
        // // 用数字方式获取公式结果，根据值判断是否为日期类型
        // double numericValue = cell.getNumericCellValue();
        // if (DateUtil.isValidExcelDate(numericValue)) { // 如果是日期类型
        // value = formatDate(cell);
        // } else {
        // value = String.valueOf(numericValue);
        // }
        // break;
        case Cell.CELL_TYPE_BLANK: // 空白
            value = "";
            break;
        case Cell.CELL_TYPE_BOOLEAN: // Boolean
            value = String.valueOf(cell.getBooleanCellValue());
            break;
        case Cell.CELL_TYPE_ERROR: // Error，返回错误码
            value = String.valueOf(cell.getErrorCellValue());
            break;
        default:
            value = "";
            break;
        }
        return value;
    }

    protected static List<Map<String, String>> readWorkbook(Workbook wb) throws Exception {
        List<Map<String, String>> list = new LinkedList<Map<String, String>>();

        for (int k = 0; k < wb.getNumberOfSheets(); k++) {
            Sheet sheet = wb.getSheetAt(k);
            int rows = sheet.getPhysicalNumberOfRows();
            // System.out.println("Sheet " + k + " \"" + wb.getSheetName(k) +
            // "\" has " + rows
            // + " row(s).");
            for (int r = 0; r < rows; r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }
                Map<String, String> map = new HashMap<String, String>();
                int cells = row.getPhysicalNumberOfCells();
                // System.out.println("\nROW " + row.getRowNum() + " has " +
                // cells + " cell(s).");
                for (int c = 0; c < cells; c++) {
                    Cell cell = row.getCell(c);
                    if (cell == null) {
                        continue;
                    }
                    String value = getCellValue(cell);
                    map.put(String.valueOf(cell.getColumnIndex() + 1), value);
                }
                list.add(map);
            }

        }

        return list;
    }

}
