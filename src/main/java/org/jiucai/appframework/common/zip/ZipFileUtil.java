package org.jiucai.appframework.common.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.Deflater;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * zip 文件压缩解压缩工具类
 * 
 * <pre>
 * 特色功能
 * 1、 支持中文目录和中文文件夹(JDK原始工具类不支持)，此功能依赖于 Apache ant 包
 * 2、 支持多层次文件夹打包压缩
 * 3、 无文件夹时，支持为  zip 的的文件实体起别名
 * </pre>
 * 
 * @author zhaidw
 *
 */
public class ZipFileUtil {

    protected static Logger logger = LoggerFactory.getLogger(ZipFileUtil.class);

    public static final int BUFFER_SIZE = 4096;

    public static void main(String[] args) throws Exception {

        if (args.length != 3) {
            printUsage();
            return;
        }
        // String currentPath = new File("./").getAbsolutePath();
        // File zipFile = new File(currentPath + args[1]);

        File zipFile = new File(args[1]);
        if ("zip".equalsIgnoreCase(args[0])) {
            System.out.println("zip file will create at " + zipFile.getAbsolutePath());

            String[] files = args[2].split(",");

            List<String> filePathList = Arrays.asList(files);
            List<File> fileList = Collections.synchronizedList(new LinkedList<File>());
            for (String f : filePathList) {
                fileList.add(new File(f));
            }

            List<String> entryNameList = Collections.synchronizedList(new LinkedList<String>());

            zip(zipFile, entryNameList, fileList, "GBK", "test", false);

            System.out.println("zip file will created.");
        } else if ("unzip".equalsIgnoreCase(args[0])) {
            String targetDir = args[2];
            unzip(args[1], "GBK", targetDir);

        } else {
            printUsage();
            return;
        }

        // zip c:\test_001.zip c:\a1,c:\中文1.txt
        // unzip c:\测试压缩4.zip c:\a2

    }

    /**
     * 解压缩
     * 
     * @param zipFile
     *            ZIP文件
     * @param encoding
     *            压缩包中的文件名和注释编码
     * @param descDir
     *            解压缩的目标目录
     * @return 解压缩后的文件列表,如果解压缩失败，返回空的列表
     */
    public static List<String> unzip(String zipFile, String encoding, String descDir) {

        List<String> fileList = Collections.synchronizedList(new ArrayList<String>());
        ZipFile zf = null;
        try {
            zf = new ZipFile(new File(zipFile), encoding);

            Enumeration<ZipEntry> entries = zf.getEntries();

            // 创建输入目录
            File outPath = new File(descDir);
            if (!outPath.exists()) {
                outPath.mkdirs();
            }

            String outPathDir = descDir;

            while (entries.hasMoreElements()) {
                // Get the entry name
                ZipEntry entry = entries.nextElement();

                String entryName = entry.getName();

                String outFileName = "";

                if (entry.isDirectory()) {
                    logger.debug("[DIR ] creating " + entryName);
                    File decompressDirFile = new File(outPathDir + "/" + entryName);
                    if (!decompressDirFile.exists()) {
                        decompressDirFile.mkdirs();
                    }
                } else {
                    outFileName = outPathDir + "/" + entryName;
                    logger.debug("outFileName: " + outFileName);

                    OutputStream out = new FileOutputStream(outFileName);

                    logger.debug("[FILE] creating " + entryName + " [" + entry.getCompressedSize()
                            + "/" + entry.getSize() + " bytes]");

                    byte[] buf1 = new byte[BUFFER_SIZE];
                    int len;
                    InputStream in = zf.getInputStream(entry);
                    while ((len = in.read(buf1)) > 0) {
                        out.write(buf1, 0, len);
                    }
                    // Close the file and stream
                    in.close();
                    out.close();
                }

                if (StringUtils.isNotBlank(outFileName)) {
                    fileList.add(entryName);
                }

            }

            logger.debug("ZIP file " + zipFile + " unziped successed.");
        } catch (Throwable ex) {
            logger.error("failed to unzip: ", ex);
        } finally {
            if (null != zf) {
                try {
                    // Complete the ZIP file
                    zf.close();
                } catch (IOException e) {
                    logger.error("Can not close ZipFile: ", e);
                }
            }
        }

        return fileList;

    }

    /**
     * 压缩文件
     * 
     * @param zipfile
     *            zip文件
     * @param entryNameList
     *            文件别名列表
     * @param fileList
     *            文件列表
     * @param encoding
     *            zip的实体名称和注释编码，推荐使用GBK
     * @param comment
     *            zip文件的注释
     * @param useSameDir
     *            值为 false 时，保存原有目录结构，fileListMap 中的文件别名 key 将被忽略; true
     *            的时候fileList 中的文件夹将被被忽略
     * @return 是否压缩成功
     */
    public static Boolean zip(File zipfile, List<String> entryNameList, List<File> fileList,
            String encoding, String comment, Boolean useSameDir) {
        Boolean isSuccess = false;
        // Create the ZIP file
        ZipOutputStream zos = null;
        try {

            if (zipfile.exists()) {
                zipfile.delete();
            }

            if (!zipfile.getParentFile().exists()) {
                zipfile.getParentFile().mkdirs();
            }

            // Create the ZIP file
            zos = new ZipOutputStream(new FileOutputStream(zipfile));

            setZipOutputStream(zos, encoding, comment);

            // Compress the files
            for (int i = 0; i < fileList.size(); i++) {
                try {
                    String entryName = null;
                    if (null != entryNameList && entryNameList.size() > 0) {
                        entryName = entryNameList.get(i);
                    }

                    addEntry(entryName, fileList.get(i), zos, useSameDir);
                } catch (Throwable e1) {
                    logger.error("add file " + fileList.get(i) + " to zip file failed: "
                            + ExceptionUtils.getRootCauseMessage(e1));
                }

            }

            zos.flush();
            logger.debug("ZIP file " + zipfile.getPath() + " created.");
            isSuccess = true;

        } catch (Throwable ex) {
            isSuccess = false;
            logger.error("failed to zip: ", ex);
        } finally {
            if (null != zos) {
                try {
                    // Complete the ZIP file
                    zos.close();
                } catch (Throwable e) {
                    logger.error("Can not close ZipOutputStream: ", e);
                }
            }
        }

        return isSuccess;
    }

    /**
     * 把 map 中的文件值加入压缩包
     * 
     * @param zipfile
     *            zip 文件
     * @param fileListMap
     *            map的key是压缩后的文件名，值是原始文件名
     * @param encoding
     *            zip的实体名称和注释编码，推荐使用GBK
     * @param comment
     *            zip文件的注释
     * @param useSameDir
     *            值为 false 时，保存原有目录结构，fileListMap 中的文件别名 key 将被忽略
     * @return 是否压缩成功
     */
    public static Boolean zip(File zipfile, Map<String, File> fileListMap, String encoding,
            String comment, Boolean useSameDir) {
        List<String> entryNameList = Collections.synchronizedList(new LinkedList<String>());
        List<File> fileList = Collections.synchronizedList(new LinkedList<File>());
        Iterator<Entry<String, File>> set = fileListMap.entrySet().iterator();

        while (set.hasNext()) {
            Entry<String, File> entry = set.next();
            entryNameList.add(entry.getKey());
            fileList.add(entry.getValue());
        }

        return zip(zipfile, entryNameList, fileList, encoding, comment, useSameDir);

    }

    /**
     * 压缩文件
     * 
     * @param out
     *            OutputStream 输出流
     * @param entryNameList
     *            文件别名列表
     * @param fileList
     *            文件列表
     * @param encoding
     *            zip的实体名称和注释编码，推荐使用GBK
     * @param comment
     *            zip文件的注释
     * @param useSameDir
     *            值为 false 时，保存原有目录结构，fileListMap 中的文件别名 key 将被忽略; true
     *            的时候fileList 中的文件夹将被被忽略
     * @return 是否压缩成功
     */
    public static Boolean zip(OutputStream out, List<String> entryNameList, List<String> fileList,
            String encoding, String comment, Boolean useSameDir) {
        Boolean isSuccess = false;
        try {
            // Create the ZIP file
            ZipOutputStream zos = new ZipOutputStream(out);
            setZipOutputStream(zos, encoding, comment);
            // Compress the files
            for (int i = 0; i < fileList.size(); i++) {
                try {
                    String entryName = null;
                    if (null != entryNameList && entryNameList.size() > 0) {
                        entryName = entryNameList.get(i);
                    }

                    addEntry(entryName, new File(fileList.get(i)), zos, useSameDir);
                } catch (Throwable e1) {
                    logger.error("add file " + fileList.get(i) + " to zip file failed: "
                            + ExceptionUtils.getRootCauseMessage(e1));
                }
            }
            zos.flush();
            // Complete the ZIP file
            zos.close();
            logger.debug("ZIP file content wireted to OutputStream successed.");
            isSuccess = true;
        } catch (IOException ex) {
            isSuccess = false;
            logger.error("failed to zip: ", ex);
        }

        return isSuccess;
    }

    /**
     * 向 zip输出流 增加文件实体
     * 
     * @param zipEntryName
     *            zip中文件实体名称
     * @param file
     *            物理文件
     * @param zos
     *            ZipOutputStream
     * @param useSameDir
     *            值为 false 时，保存原有目录结构，zipEntryName 将被忽略
     * @throws IOException
     *             IO异常
     */
    private static void addEntry(String zipEntryName, File file, ZipOutputStream zos,
            Boolean useSameDir) throws IOException {

        String entryName = zipEntryName;

        if (file.isDirectory()) {

            // 使用1层目录才可以用别名，否则还是原始文件名,且忽略目录
            if (useSameDir) {
                // File[] fl = file.listFiles();
                // for (int i = 0; i < fl.length; i++) {
                // addEntry(entryName,fl[i], zos,useSameDir);
                // }
            } else {
                entryName = getDirectoryPath(file) + "/";
                logger.debug("add zip entry directory: " + entryName);
                zos.putNextEntry(new ZipEntry(entryName));

                File[] fl = file.listFiles();
                for (int i = 0; i < fl.length; i++) {
                    addEntry(entryName, fl[i], zos, useSameDir);
                }
            }

        } else {

            byte[] buf = new byte[BUFFER_SIZE];

            FileInputStream in = new FileInputStream(file);

            if (useSameDir) {
                entryName = zipEntryName;
            } else {
                entryName = getFilePath(file);
                // entryName = new File(entryName).getName();
            }

            ZipEntry entry = new ZipEntry(entryName);
            entry.setTime(file.lastModified());

            logger.debug("add zip entry file: " + entryName);

            zos.putNextEntry(entry);
            int len;
            while ((len = in.read(buf)) > 0) {
                zos.write(buf, 0, len);
            }
            in.close();
            zos.flush();
        }

        zos.closeEntry();
    }

    /**
     * 获取 去除了绝对路径的相对路径文件夹名称
     * 
     * <pre>
     * 比如: c:/windows/test 返回  windows/test
     * 比如: /etc/test/t.txt 返回 etc/test
     * </pre>
     * 
     * @param file
     *            是1个文件路径的 File 对象
     * @return 去除了绝对路径的相对路径文件夹名称
     */
    private static String getDirectoryPath(File file) {
        String entryName = file.getAbsolutePath();

        int index = entryName.indexOf(File.separator);
        if (index > 0) {
            entryName = entryName.substring(index + 1);
        }
        entryName = entryName.replace("\\", "/");
        return entryName;
    }

    /**
     * 获取 去除了绝对路径的相对路径文件名
     * 
     * <pre>
     * 比如: c:/windows/test/t.txt 返回  windows/test/t.txt
     * 比如: /etc/test/t.txt 返回 etc/test/t.txt
     * </pre>
     * 
     * @param file
     *            是1个文件的 File 对象
     * @return 去除了绝对路径的相对路径文件名
     */
    private static String getFilePath(File file) {
        String entryName = file.getPath();

        int index = entryName.indexOf(File.separator);
        if (index > 0) {
            entryName = entryName.substring(index + 1);
        }
        entryName = entryName.replace("\\", "/");

        return entryName;
    }

    private static void printUsage() {
        System.out.println("Usage: ");
        System.out.println("\tzip test.zip dir|file,dir|file,...");
        System.out.println("\tunzip test.zip targetDir");

    }

    protected static void setZipOutputStream(ZipOutputStream zos, String encoding, String comment) {

        zos.setLevel(Deflater.BEST_COMPRESSION);
        if (StringUtils.isNotBlank(encoding)) {
            zos.setEncoding(encoding);
        }

        if (StringUtils.isNotBlank(comment)) {
            zos.setComment(comment);
        }
    }
}
