package com.onnoa.shop.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.util.Strings;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * 解压缩工具类
 */
@Slf4j
public class ZipUtil {

    private static final int BUFFER_SIZE = 5 * 1024;


    /**
     * 只传一个压缩文件的
     *
     * @param sourceDir
     */
    public static String toZip(String sourceDir) {
        return toZip(sourceDir, null, true);
    }

    /**
     * 目标文件  +  压缩文件的位置  (名字用默认)
     *
     * @param sourceDir
     * @param target
     */
    public static String toZip(String sourceDir, String target) {
        String fileName = new File(sourceDir).getName();
        return toZip(sourceDir, target, true);
    }

    /**
     * 压缩成ZIP 方法1
     * 目标文件 + 压缩文件的位置  + 目标文件命名  + 文件夹中的文件是否在源目录
     *
     * @param sourceDir        压缩文件夹路径
     * @param targetFileName   压缩后文件名称
     * @param KeepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     *                         false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static String toZip(String sourceDir, String targetFileName, boolean KeepDirStructure)
            throws RuntimeException {
        //Files.getDir(CstDir.config)
        File sourceFile = new File(sourceDir);
        String sourcePath = sourceFile.getParentFile().toString();
        String fileName = sourceFile.getName();

        String targetPath = sourcePath;

        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            FileOutputStream out = null;
            if (Strings.isEmpty(targetFileName)) {
                if (sourceFile.isDirectory()) {
                    targetPath = targetPath + File.separator + fileName + ".zip";
                    out = new FileOutputStream(new File(targetPath));
                } else {
                    targetPath = targetPath + File.separator + fileName.substring(0, fileName.lastIndexOf('.')) + ".zip";
                    out = new FileOutputStream(new File(targetPath));
                }
            } else {
                targetPath = targetPath + targetFileName + ".zip";
                out = new FileOutputStream(new File(targetPath));
            }

            zos = new ZipOutputStream(out);
            compress(sourceFile, zos, sourceFile.getName(), KeepDirStructure);
            long end = System.currentTimeMillis();
            log.info("文件【" + sourceDir + "】压缩完成，耗时：" + (end - start) + " ms");
            return targetPath;
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 多个文件一起压缩，若上级目录为根节点，则压缩后的名字与最后一个文件的命名相同
     * 若上级目录不空，则压缩后的名字为上级目录的名字
     * 压缩成ZIP 方法2
     *
     * @param srcFiles 需要压缩的文件列表
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */

    public static void toZips(List<File> srcFiles, String targetDir) throws RuntimeException {
        long start = System.currentTimeMillis();

        FileOutputStream out = null;
        String targetName = "";
        String sourcePath = srcFiles.get(0).getParent();

        ZipOutputStream zos = null;
        try {
            if (Strings.isEmpty(targetDir)) {
                if (srcFiles.size() > 0 && srcFiles != null) {
                    targetName = srcFiles.get(srcFiles.size() - 1).getName();//获得最后一个文件的名字
                    targetName = targetName.substring(0, targetName.lastIndexOf('.'));
                }
                out = new FileOutputStream(new File(sourcePath + "/" + targetName + ".zip"));

            } else {
                out = new FileOutputStream(new File(targetDir));
            }
            zos = new ZipOutputStream(out);
            for (File srcFile : srcFiles) {
                compress(srcFile, zos, srcFile.getName(), true);
            }
            long end = System.currentTimeMillis();
            log.info("压缩完成，耗时：" + (end - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 递归压缩方法
     *
     * @param sourceFile       源文件
     * @param zos              zip输出流
     * @param name             压缩后的名称
     * @param KeepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     *                         false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name,
                                 boolean KeepDirStructure) throws Exception {
        byte[] buf = new byte[BUFFER_SIZE];
        if (sourceFile.isFile()) {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if (KeepDirStructure) {
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }

            } else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zos, name + "/" + file.getName(), KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(), KeepDirStructure);
                    }
                }
            }
        }
    }

    private static final int buffer = 2048;

    /**
     * 解压Zip文件
     *
     * @param path 文件目录
     */
    public static void unZip(String path) {
        int count = -1;
        String savepath = "";
        File file = null;
        InputStream is = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        savepath = path.substring(0, path.lastIndexOf(".")) + File.separator; //保存解压文件目录
        new File(savepath).mkdir(); //创建保存目录
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(path, "gbk"); //解决中文乱码问题
            Enumeration<?> entries = zipFile.getEntries();
            while (entries.hasMoreElements()) {
                byte buf[] = new byte[buffer];
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String filename = entry.getName();
                boolean ismkdir = false;
                if (filename.lastIndexOf("/") != -1) { //检查此文件是否带有文件夹
                    ismkdir = true;
                }
                filename = savepath + filename;
                if (entry.isDirectory()) { //如果是文件夹先创建
                    file = new File(filename);
                    file.mkdirs();
                    continue;
                }
                file = new File(filename);
                if (!file.exists()) { //如果是目录先创建
                    if (ismkdir) {
                        new File(filename.substring(0, filename.lastIndexOf("/"))).mkdirs(); //目录先创建
                    }
                }
                file.createNewFile(); //创建文件
                is = zipFile.getInputStream(entry);
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos, buffer);
                while ((count = is.read(buf)) > -1) {
                    bos.write(buf, 0, count);
                }
                bos.flush();
                bos.close();
                fos.close();
                is.close();
            }
            zipFile.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();
                }
                if (zipFile != null) {
                    zipFile.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static List<File> upzipFile(String zipPath, String descDir) {
        return upzipFile(new File(zipPath), descDir);
    }

    public static List<File> upzipFile(File zipFile, String descDir) {
        List<File> list = new ArrayList<>();
        // 防止文件名中有中文时出错
        System.setProperty("sun.zip.encoding", System.getProperty("sun.jnu.encoding"));
        try {
            if (!zipFile.exists()) {
                throw new RuntimeException("解压失败，文件 " + zipFile + " 不存在!");
            }
            ZipFile zFile = new ZipFile(zipFile, "GBK");
            InputStream in = null;
            OutputStream out = null;
            for (Enumeration entries = zFile.getEntries(); entries.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                File file = new File(descDir + File.separator + entry.getName());
                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    in = zFile.getInputStream(entry);
                    out = new FileOutputStream(file);
                    IOUtils.copy(in, out);
                    out.flush();
                    list.add(file);
                }
            }
            zFile.close();
            in.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return list;
    }


}
