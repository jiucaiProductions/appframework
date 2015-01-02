package org.jiucai.appframework.base.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jiucai.appframework.base.domain.KeyValuePair;
import org.jiucai.appframework.common.csv.CsvWriter;
import org.jiucai.appframework.common.util.BaseUtil;

/**
 * CSV 下载工具
 * 
 * @author zhaidw
 *
 */
public class CsvDownloadUtil extends BaseUtil {

    /**
     * getFileName
     * 
     * @param displayName
     *            download file displayName
     * @return String
     */
    public static String getFileName(String displayName) {
        // 显示下载文件名称
        String displayFileName = displayName;
        SimpleDateFormat df = new SimpleDateFormat("MMddHHmmss");
        String dateStr = df.format(new Date());
        displayFileName += "_" + dateStr;

        // 如果现实名称为空 则使用fileName
        if (!displayFileName.endsWith(".csv")) {
            displayFileName += ".csv";
        }
        return displayFileName;
    }

    /**
     * 
     * @param filePath
     *            输出文件路径
     * @param fileName
     *            输出文件
     * @param headList
     *            表头列表
     * @param dataList
     *            数据列表
     * @param sumList
     *            总计列表
     * @param isAppend
     *            是否文件追加数据
     * @return 生成的文件全路径
     */
    @SuppressWarnings("unchecked")
    public static final String saveFile(final String filePath, String fileName,
            List<KeyValuePair> headList, List<Map<String, Object>> dataList,
            List<Map<String, Object>> sumList, Boolean isAppend) {
        String basePath = filePath;
        StringBuffer fileSavedName = new StringBuffer();

        // 默认存储空间地址
        if (StringUtils.isEmpty(filePath)) {
            basePath = "/tmp/report";
            log.debug("use default file path:  " + basePath);
        } else {
            log.debug("use user set file path:  " + filePath);
        }

        fileSavedName.append(basePath);

        if (!filePath.endsWith("/")) {
            fileSavedName.append("/");
        }

        if (!fileName.endsWith(".csv")) {
            fileName += ".csv";
        }

        // 用于创建没有的文件夹
        final String createFilePath = fileSavedName.toString();

        log.info("FilePath: " + createFilePath);

        // 生成文件名称

        fileSavedName.append(fileName);

        try {

            File file = new File(createFilePath);
            // 判断文件夹是否存在
            if (!file.isDirectory()) {
                file.mkdirs();
            }

            OutputStream out = new FileOutputStream(fileSavedName.toString(), isAppend); // 是否追加
            CsvWriter wr = new CsvWriter(out, ',', Charset.forName(fileCharset));
            wr.setForceQualifier(true);

            // 添加表头信息
            if (CollectionUtils.isNotEmpty(headList) && !isAppend) {
                List<String> headNamesList = new ArrayList<String>();
                for (KeyValuePair kvp : headList) {
                    headNamesList.add(getConvertedMapValue(kvp.getText()));
                }

                // 防止生成sylk文件时，首行内容出现ID字符而导致文件无法打开问题
                for (int i = 0; i < headNamesList.size(); i++) {
                    headNamesList.set(i, headNamesList.get(i));
                }
                String[] rowData = headNamesList.toArray(new String[headNamesList.size()]);
                wr.writeRecord(rowData, preserveSpaces);
            }

            // 添加总计信息
            if (CollectionUtils.isNotEmpty(sumList) && !isAppend) {
                List<List<String>> datas = getCsvHeadList(headList, sumList);
                List<String>[] data = datas.toArray(new ArrayList[datas.size()]);
                for (List<String> da : data) {
                    for (int i = 0; i < da.size(); i++) {
                        da.set(i, da.get(i));
                    }
                    String[] rowData = da.toArray(new String[da.size()]);
                    wr.writeRecord(rowData, preserveSpaces);
                }
            }

            // 添加列表数据信息
            if (CollectionUtils.isNotEmpty(dataList)) {
                List<List<String>> datas = getCsvHeadList(headList, dataList);
                List<String>[] data = datas.toArray(new ArrayList[datas.size()]);
                for (List<String> da : data) {
                    for (int i = 0; i < da.size(); i++) {
                        da.set(i, da.get(i));
                    }
                    String[] rowData = da.toArray(new String[da.size()]);
                    wr.writeRecord(rowData, preserveSpaces);
                }
            }

            wr.close();
            out.close();

            wr = null;
            out = null;

        } catch (FileNotFoundException e) {
            log.error("文件 " + fileSavedName.toString() + " 不存在: " + e);
        } catch (IOException e) {
            log.error("下载文件异常: " + e);
        }
        return fileSavedName.toString();
    }

    public static void setCellPrefix(String prefix) {
        cellPrefix = prefix;
    }

    public static void setCellSuffix(String suffix) {
        cellSuffix = suffix;
    }

    public static void setCharset(String charset) {
        fileCharset = charset;
    }

    public static void setPreserveSpaces(Boolean allowSpaces) {
        preserveSpaces = allowSpaces;
    }

    // 根据用户所选表头进行筛选
    private static List<List<String>> getCsvHeadList(List<KeyValuePair> headList,
            List<Map<String, Object>> dataList) {

        List<List<String>> dataBody = new ArrayList<List<String>>();
        List<String> data = null;

        List<String> headCode = new ArrayList<String>();

        if (null != headList) {
            for (KeyValuePair kvp : headList) {
                headCode.add(kvp.getCode().toLowerCase());
            }
        }

        for (Map<String, Object> map : dataList) {
            data = new ArrayList<String>();
            for (String code : headCode) {
                data.add(getConvertedMapValue(map.get(code)));
            }
            dataBody.add(data);
        }

        return dataBody;
    }

    protected static String getConvertedMapValue(Object val) {

        return new StringBuffer(cellPrefix).append(convertMapValue(val)).append(cellSuffix)
                .toString();
    }

    protected static String fileCharset = "GBK";

    /**
     * 为单元格数据增加后缀，用来支持 Excel 中正确显示格式
     */
    protected static String cellPrefix = "";;

    /**
     * 为单元格数据增加后缀，用来支持 Excel 中正确显示格式
     */
    protected static String cellSuffix = "";

    /**
     * 是否保留数据两边的空格
     */
    protected static Boolean preserveSpaces = true;

}
