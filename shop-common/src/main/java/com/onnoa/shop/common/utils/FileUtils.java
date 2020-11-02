package com.onnoa.shop.common.utils;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;

public final class FileUtils {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(FileUtils.class);

    private FileUtils() {

    }

    public static String getLocalAddr() {
        String localAddr = "";
        //本地配置文件下载存放路径
        try {
            File directory = new File("");
            // 参数为空
            localAddr = directory.getCanonicalPath();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return localAddr + "/";
    }

    public static boolean createFile(File file) {
        try {
            boolean mkFilePath = createFilePath(file);
            return mkFilePath && file.createNewFile();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public static boolean createFilePath(File file) {
        try {
            File parent = file.getParentFile();
            return parent == null || parent.exists() || parent.mkdirs();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public static boolean deleteFolder(String folderPath) {
        File folder = new File(folderPath);
        return deleteFolder(folder);
    }

    public static boolean deleteFolder(File folder) {
        if (folder != null && folder.exists()) {
            String[] fileNameList = folder.list();
            if (fileNameList != null) {
                for (String fileName : fileNameList) {
                    String filePath = folder.getPath() + File.separator + fileName;
                    File file = new File(filePath);
                    if (file.isFile()) {
                        deleteFile(file);
                    } else {
                        deleteFolder(file);
                    }
                }
            }
            try {
                return folder.delete();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return false;
            }
        }
        return true;
    }

    public static boolean deleteFile(File file) {
        boolean success = true;
        try {
            if (file != null && file.exists()) {
                success = file.delete();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            success = false;
        }
        return success;
    }

    public static void downloadLocalFile(HttpServletRequest request, HttpServletResponse response, InputStream inputStream, String exportFileName) {
        ServletOutputStream outputStream = null;
        try {
            // 安全扫描要求，文件上传/下载路径不能包含./或../
//            if (filePath.contains("./") || filePath.contains("../")) {
//                throw new OcrRunTimeException(ErrorCodeEnum.FTP_DOWNLOAD_ERROR, "下载文件路径不能包含./或../");
//            }
            String encodeFileName = java.net.URLEncoder.encode(exportFileName, "UTF-8");
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("UTF-8");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + encodeFileName);
            outputStream = response.getOutputStream();
            int b;
            byte[] buffer = new byte[1024];
            while ((b = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, b);
            }
            outputStream.flush();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

}
