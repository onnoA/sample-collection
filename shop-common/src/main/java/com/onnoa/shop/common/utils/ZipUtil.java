package com.onnoa.shop.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 解压缩工具类
 */
@Slf4j
public class ZipUtil {

    private static final int  BUFFER_SIZE = 5 * 1024;


    /**
     * 只传一个压缩文件的
     * @param sourceDir
     */
    public static String toZip(String sourceDir) {
        return toZip(sourceDir,null,true);
    }

    /**
     * 目标文件  +  压缩文件的位置  (名字用默认)
     * @param sourceDir
     * @param target
     */
    public static String toZip(String sourceDir,String target) {
        String fileName = new File(sourceDir).getName();
        return toZip(sourceDir,target,true);
    }

    /**
     * 压缩成ZIP 方法1
     * 目标文件 + 压缩文件的位置  + 目标文件命名  + 文件夹中的文件是否在源目录
     * @param sourceDir 压缩文件夹路径
     * @param targetFileName  压缩后文件名称
     * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构;
     *                          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static String toZip(String sourceDir, String targetFileName, boolean KeepDirStructure)
            throws RuntimeException{
        //Files.getDir(CstDir.config)
        File sourceFile = new File(sourceDir);
        String sourcePath = sourceFile.getParentFile().toString();
        String fileName = sourceFile.getName();

        String targetPath = sourcePath;

        long start = System.currentTimeMillis();
        ZipOutputStream zos = null ;
        try {
            FileOutputStream out = null;
            if(Strings.isEmpty(targetFileName)) {
                if(sourceFile.isDirectory()) {
                    targetPath =  targetPath + File.separator+fileName+".zip";
                    out = new FileOutputStream(new File(targetPath));
                }else {
                    targetPath =  targetPath + File.separator+fileName.substring(0, fileName.lastIndexOf('.'))+".zip";
                    out = new FileOutputStream(new File(targetPath));
                }
            }else {
                targetPath = targetPath + targetFileName +".zip";
                out = new FileOutputStream(new File(targetPath));
            }

            zos = new ZipOutputStream(out);
            compress(sourceFile,zos,sourceFile.getName(),KeepDirStructure);
            long end = System.currentTimeMillis();
            log.info("文件【" + sourceDir + "】压缩完成，耗时：" + (end - start) +" ms");
            return targetPath;
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils",e);
        }finally{
            if(zos != null){
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
     * @param srcFiles 需要压缩的文件列表
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */

    public static void toZips(List<File> srcFiles , String targetDir)throws RuntimeException {
        long start = System.currentTimeMillis();

        FileOutputStream out = null;
        String targetName = "";
        String sourcePath = srcFiles.get(0).getParent();

        ZipOutputStream zos = null ;
        try {
            if(Strings.isEmpty(targetDir)) {
                if(srcFiles.size()>0 && srcFiles!=null) {
                    targetName = srcFiles.get(srcFiles.size()-1).getName();//获得最后一个文件的名字
                    targetName = targetName.substring(0, targetName.lastIndexOf('.'));
                }
                out = new FileOutputStream(new File(sourcePath+"/"+targetName+".zip"));

            }else {
                out = new FileOutputStream(new File(targetDir));
            }
            zos = new ZipOutputStream(out);
            for (File srcFile : srcFiles) {
                compress(srcFile,zos,srcFile.getName(),true);
            }
            long end = System.currentTimeMillis();
            log.info("压缩完成，耗时：" + (end - start) +" ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils",e);
        }finally{
            if(zos != null){
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
     * @param sourceFile 源文件
     * @param zos        zip输出流
     * @param name       压缩后的名称
     * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构;
     *                          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name,
                                 boolean KeepDirStructure) throws Exception{
        byte[] buf = new byte[BUFFER_SIZE];
        if(sourceFile.isFile()){
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1){
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if(listFiles == null || listFiles.length == 0){
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if(KeepDirStructure){
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }

            }else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zos, name + "/" + file.getName(),KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(),KeepDirStructure);
                    }
                }
            }
        }
    }


}
