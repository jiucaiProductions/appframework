package org.jiucai.appframework.common.deflater.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.jiucai.appframework.common.util.LogUtil;
import org.jiucai.appframework.common.util.Logs;

/**
 *
 * 提供对单个文件与目录的压缩，并支持是否需要创建压缩源目录、中文路径
 *
 * @author jzj
 */
public class Zip {

    public static void doUncompressFile(String inFileName) {

        try {
            GZIPInputStream in = null;

            try {

                in = new GZIPInputStream(new FileInputStream(inFileName));

            } catch (FileNotFoundException e) {

                System.err.println("File not found. " + inFileName);

                System.exit(1);

            }

            System.out.println("Open the output file.");

            String outFileName = getFileName(inFileName);

            FileOutputStream out = null;

            try {

                out = new FileOutputStream(outFileName);

            } catch (FileNotFoundException e) {

                System.err.println("Could not write to file. " + outFileName);

                System.exit(1);

            }

            System.out.println("Transfering bytes from compressed file to the output file.");

            byte[] buf = new byte[1024];

            int len;

            while ((len = in.read(buf)) > 0) {

                out.write(buf, 0, len);

            }

            System.out.println("Closing the file and stream");

            in.close();

            out.close();

        } catch (IOException e) {

            e.printStackTrace();

            System.exit(1);

        }

    }

    public static String getFileName(String f) {

        String fname = "";

        int i = f.lastIndexOf('.');

        if (i > 0 && i < f.length() - 1) {

            fname = f.substring(0, i);

        }

        return fname;

    }

    public static void main(String[] args) throws Exception {
        String src = "d:/Enter_Page_ID.log";
        ;// 指定压缩源，可以是目录或文件

        String archive = "d:/log3.zip";// 压缩包路径
        String decompressDir = "d:/log4";// 解压路径

        long start, end;

        start = System.currentTimeMillis();

        System.out.println("start zip file ...");
        // setEncoding("GBK");
        zip(src, archive);
        end = System.currentTimeMillis();
        System.out.println("zip time:" + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("start unzip file ...");
        unzip(archive, decompressDir);
        end = System.currentTimeMillis();
        System.out.println("unzip time:" + (end - start) + " ms");

    }

    /**
     * 读取压缩包中的指定文件
     *
     * @param archive
     *            压缩包文件
     * @param filePath
     *            指定的文件名
     * @return InputStream InputStream
     * @throws IOException
     *             IOException
     */
    public synchronized static InputStream readFile(String archive, String filePath)
            throws IOException {
        InputStream in = null;
        ZipFile zf = new ZipFile(archive, encoding);// 支持中文

        Enumeration<ZipEntry> e = zf.getEntries();

        while (e.hasMoreElements()) {
            ZipEntry ze = e.nextElement();
            String entryName = ze.getName();
            if (entryName.equals(filePath)) {
                in = zf.getInputStream(ze);
                break;
            }
        }

        return in;
    }

    /**
     * 默认编码 UFT-8
     *
     * 设置为 UTF-8 时，压缩包的注释乱码，但可以正常使用 unzip / unzip2( Java API 标准解压缩) 类解压文件 设置为
     * GBK 时，压缩包的注释正常，只能是用 unzip 方法解压缩
     *
     * @param charset
     *            charset
     */
    public static void setEncoding(String charset) {
        encoding = charset;
    }

    /**
     * 使用 org.apache.tools.zip.ZipFile 解压文件，它与 java 类库中的 java.util.zip.ZipFile
     * 使用方式是一新的，只不过多了设置编码方式的 接口。
     *
     * 注，apache 没有提供 ZipInputStream 类，所以只能使用它提供的ZipFile 来读取压缩文件。
     *
     * @param archive
     *            压缩包路径
     * @param decompressDir
     *            解压路径
     * @throws IOException
     *             IOException
     * @throws FileNotFoundException
     *             FileNotFoundException
     * @throws ZipException
     *             ZipException
     */
    public synchronized static void unzip(String archive, String decompressDir) throws IOException,
            FileNotFoundException, ZipException {
        BufferedInputStream bi;

        ZipFile zf = new ZipFile(archive, encoding);// 支持中文

        Enumeration<ZipEntry> e = zf.getEntries();
        while (e.hasMoreElements()) {
            ZipEntry ze = e.nextElement();
            String entryName = ze.getName();
            String path = decompressDir + "/" + entryName;
            if (ze.isDirectory()) {
                log.debug("正在创建解压目录 - " + entryName);
                File decompressDirFile = new File(path);
                if (!decompressDirFile.exists()) {
                    decompressDirFile.mkdirs();
                }
            } else {
                log.debug("正在创建解压文件 - " + entryName);
                String fileDir = path.substring(0, path.lastIndexOf("/"));
                File fileDirFile = new File(fileDir);
                if (!fileDirFile.exists()) {
                    fileDirFile.mkdirs();
                }
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(
                        decompressDir + "/" + entryName));

                bi = new BufferedInputStream(zf.getInputStream(ze));
                byte[] readContent = new byte[1024];
                int readCount = bi.read(readContent);
                while (readCount != -1) {
                    bos.write(readContent, 0, readCount);
                    readCount = bi.read(readContent);
                }
                bos.close();
                bi.close();
            }
        }
        zf.close();
    }

    /**
     * 使用 java api 中的 ZipInputStream 类解压文件，但如果压缩时采用了
     * org.apache.tools.zip.ZipOutputStream时，而不是 java 类库中的
     * java.util.zip.ZipOutputStream时，该方法不能使用，原因就是编码方 式不一致导致，运行时会抛如下异常：
     * java.lang.IllegalArgumentException at
     * java.util.zip.ZipInputStream.getUTF8String(ZipInputStream.java:290)
     *
     * 当然，如果压缩包使用的是java类库的java.util.zip.ZipOutputStream 压缩而成是不会有问题的，但它不支持中文
     *
     * @param archive
     *            压缩包路径
     * @param decompressDir
     *            解压路径
     * @throws FileNotFoundException
     *             FileNotFoundException
     * @throws IOException
     *             IOException
     */
    public synchronized static void unzip2(String archive, String decompressDir)
            throws FileNotFoundException, IOException {
        BufferedInputStream bi;
        // ----解压文件(ZIP文件的解压缩实质上就是从输入流中读取数据):
        log.debug("开始读压缩文件");

        FileInputStream fi = new FileInputStream(archive);
        CheckedInputStream csumi = new CheckedInputStream(fi, new CRC32());
        ZipInputStream in2 = new ZipInputStream(csumi);
        bi = new BufferedInputStream(in2);
        java.util.zip.ZipEntry ze;// 压缩文件条目
        // 遍历压缩包中的文件条目
        while ((ze = in2.getNextEntry()) != null) {
            String entryName = ze.getName();
            if (ze.isDirectory()) {
                log.debug("正在创建解压目录 - " + entryName);
                File decompressDirFile = new File(decompressDir + "/" + entryName);
                if (!decompressDirFile.exists()) {
                    decompressDirFile.mkdirs();
                }
            } else {
                log.debug("正在创建解压文件 - " + entryName);
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(
                        decompressDir + "/" + entryName));
                byte[] buffer = new byte[1024];
                int readCount = bi.read(buffer);

                while (readCount != -1) {
                    bos.write(buffer, 0, readCount);
                    readCount = bi.read(buffer);
                }
                bos.close();
            }
        }
        bi.close();
        log.debug("Checksum: " + csumi.getChecksum().getValue());
    }

    public synchronized static void zip(String src, String archive) throws FileNotFoundException,
            IOException {
        zip(src, archive, "");
    }

    /**
     * 压缩文件夹或文件
     *
     * @param src
     *            src
     * @param archive
     *            archive
     * @param comment
     *            压缩包注释
     * @throws FileNotFoundException
     *             FileNotFoundException
     * @throws IOException
     *             IOException
     */
    public static void zip(String src, String archive, String comment)
            throws FileNotFoundException, IOException {
        // ----压缩文件：
        FileOutputStream f = new FileOutputStream(archive);
        // 使用指定校验和创建输出流
        CheckedOutputStream csum = new CheckedOutputStream(f, new CRC32());

        ZipOutputStream zos = new ZipOutputStream(csum);
        // 支持中文
        zos.setEncoding(encoding);
        BufferedOutputStream out = new BufferedOutputStream(zos);
        // 设置压缩包注释
        zos.setComment(comment);

        // 启用压缩
        zos.setMethod(ZipOutputStream.DEFLATED);
        // 压缩级别为最强压缩，但时间要花得多一点
        zos.setLevel(Deflater.BEST_COMPRESSION);

        File srcFile = new File(src);

        if (!srcFile.exists() || (srcFile.isDirectory() && srcFile.list().length == 0)) {
            out.close();
            throw new FileNotFoundException(
                    "File must exist and  ZIP file must have at least one entry.");
        }
        // 获取压缩源所在父目录
        src = src.replaceAll("\\\\", "/");
        String prefixDir = null;
        if (srcFile.isFile()) {
            prefixDir = src.substring(0, src.lastIndexOf("/") + 1);
        } else {
            prefixDir = (src.replaceAll("/$", "") + "/");
        }

        // 如果不是根目录
        if (prefixDir.indexOf("/") != (prefixDir.length() - 1) && isCreateSrcDir) {
            prefixDir = prefixDir.replaceAll("[^/]+/$", "");
        }

        // 开始压缩
        saveZipFile(zos, out, srcFile, prefixDir);

        out.close();
        // 注：校验和要在流关闭后才准备，一定要放在流被关闭后使用
        log.debug("Checksum: " + csum.getChecksum().getValue());

    }

    /**
     * 递归压缩
     *
     * 使用 org.apache.tools.zip.ZipOutputStream 类进行压缩，它的好处就是支持中文路径， 而Java类库中的
     * java.util.zip.ZipOutputStream 压缩中文文件名时压缩包会出现乱码。 使用 apache 中的这个类与 java
     * 类库中的用法是一新的，只是能设置编码方式了。
     *
     * @param zos
     *            ZipOutputStream
     * @param bo
     *            BufferedOutputStream
     * @param srcFile
     *            srcFile
     * @param prefixDir
     *            IOException
     * @throws IOException
     * @throws FileNotFoundException
     *             FileNotFoundException
     */
    private synchronized static void saveZipFile(ZipOutputStream zos, BufferedOutputStream bo,
            File srcFile, String prefixDir) throws IOException, FileNotFoundException {
        ZipEntry zipEntry;

        String filePath = srcFile.getAbsolutePath().replaceAll("\\\\", "/").replaceAll("//", "/");
        if (srcFile.isDirectory()) {
            filePath = filePath.replaceAll("/$", "") + "/";
        }
        String entryName = filePath.replace(prefixDir, "").replaceAll("/$", "");
        if (srcFile.isDirectory()) {
            if (!"".equals(entryName)) {
                log.debug("正在创建目录 - " + srcFile.getAbsolutePath() + "  entryName=" + entryName);

                // 如果是目录，则需要在写目录后面加上 /
                zipEntry = new ZipEntry(entryName + "/");
                zos.putNextEntry(zipEntry);
            }

            File srcFiles[] = srcFile.listFiles();
            for (int i = 0; i < srcFiles.length; i++) {
                saveZipFile(zos, bo, srcFiles[i], prefixDir);
            }
        } else {
            log.debug("正在写文件 - " + srcFile.getAbsolutePath() + "  entryName=" + entryName);
            BufferedInputStream bi = new BufferedInputStream(new FileInputStream(srcFile));

            // 开始写入新的ZIP文件条目并将流定位到条目数据的开始处
            zipEntry = new ZipEntry(entryName);
            zos.putNextEntry(zipEntry);
            byte[] buffer = new byte[1024];
            int readCount = bi.read(buffer);

            while (readCount != -1) {
                bo.write(buffer, 0, readCount);
                readCount = bi.read(buffer);
            }
            // 注，在使用缓冲流写压缩文件时，一个条件完后一定要刷新一把，不
            // 然可能有的内容就会存入到后面条目中去了
            bo.flush();
            // 文件读完后关闭
            bi.close();
        }
    }

    private static boolean isCreateSrcDir = true;// 是否创建源目录

    private static String encoding = "UTF-8";// 是否创建源目录

    protected static Logs log = LogUtil.getLog(Zip.class);

}