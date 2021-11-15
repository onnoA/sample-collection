package com.onnoa.shop.demo.video.utils;

import com.onnoa.shop.demo.video.constant.VideoConstants;
import com.onnoa.shop.demo.video.exception.VideoException;
import com.onnoa.shop.demo.video.properties.FTPPathConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Date;

@Slf4j
public class FtpUtil {

    /**
     * 上传文件
     * <p>
     * ftpHost ftp服务器地址
     * ftpUserName anonymous匿名用户登录，不需要密码。administrator指定用户登录
     * ftpPassword 指定用户密码
     * ftpPort ftp服务员器端口号
     * ftpPath  ftp文件存放物理路径
     * fileName 文件路径
     * input 文件输入流，即从本地服务器读取文件的IO输入流
     */
    public static Boolean uploadFile(FTPPathConfig ftpPathConfig, String ftpPath, String filename, InputStream input, FTPClient ftpClient) {
        try {
            log.info(ftpPath + " 开始上传 开始时间:{}", new Date());
            //进入指定路径
            FtpUtil.changeWorkingDirectory(ftpPathConfig.getFtpPrefixPath(), ftpClient);
            //判断当前文件夹是否存在
            if (!FtpUtil.existFile(ftpPath + "/", ftpClient)) {
                FtpUtil.makeDirectory(ftpPath, ftpClient);
            }
            FtpUtil.changeWorkingDirectory(ftpPath, ftpClient);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            //当前分辨率拼接后逇文件名
            String fileName = new String(filename.getBytes("UTF-8"), "iso-8859-1");
            ftpClient.storeFile(fileName, input);

            log.info(ftpPath + " 上传结束 结束时间:{}", new Date());
        } catch (Exception e) {
            log.error("上传ftp服务器失败,文件ID:{}", ftpPath, e);
            throw VideoException.VIDEO_UPLOAD_FAILED;
        } finally {
            try {
                input.close();
            } catch (Exception e) {
                log.error("文件ID:{},文件流关闭失败", ftpPath, e);
            } finally {
                try {
                    ftpClient.logout();
                } catch (Exception e) {
                    log.error("文件ID:{},ftp关闭连接失败", ftpPath, e);
                }
            }

        }
        return true;
    }

    /**
     * 下载视频到本地
     * 必须传入ftppath 和 DownloadFileName文件名
     * ftpPathConfig.getFtpPrefixPath()+ftpPath  下载文件路径
     * ftpPathConfig.getDownloadFilePath()  ftp文件存放物理路径(如果不是特殊路径存储 可以不用传)
     * fileName 文件路径
     */
    public static String downloadFile(FTPPathConfig ftpPathConfig, String ftpPath, String downloadFileName, FTPClient ftpClient) {
        String filepath = "";
        OutputStream outputStream = null;
        try {
            log.info("开始下载:{},文件ID:{}", new Date(), ftpPath);
            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            log.info("要下载的文件的 ftp路径 {}", ftpPathConfig.getFtpPrefixPath() + ftpPath);
            ftpClient.changeWorkingDirectory(ftpPathConfig.getFtpPrefixPath() + ftpPath);
            //判断FTP服务器中当前文件是否存在
            log.info("所需视频在ftp服务器的路径:{}", ftpPathConfig.getFtpPrefixPath() + ftpPath + "/" + downloadFileName);
            if (!FtpUtil.existFile(ftpPathConfig.getFtpPrefixPath() + ftpPath + "/" + downloadFileName, ftpClient)) {
                throw VideoException.FTPFILE_NOT_EXIST;
            }
            filepath = ftpPathConfig.getLocalPrefixPath() + ftpPath;
            File fp = new File(filepath + "/" + downloadFileName);
            // 创建目录
            //判断文件是否存在 判断时间是否大于15分钟
            if (ftpPathConfig.getReadLocal() == 1 && fp.exists() && !DateUtils.compareTime(filepath, ftpPathConfig.getInterval())) {
                return filepath + "/" + downloadFileName;
            }
            fp = new File(filepath);
            //判断文件夹是否存在
            if (!fp.exists()) {
                // 目录不存在的情况下，创建目录。
                fp.mkdirs();
            }
            File localFile = new File(filepath + File.separatorChar + downloadFileName);
            log.info("下载到本地的路径: {} 文件名: {}", filepath, downloadFileName);
            outputStream = new FileOutputStream(localFile);
            //从服务器检索命名文件并将其写入给定的OutputStream中。
            //如果成功完成返回True，否则为false。
            ftpClient.retrieveFile(downloadFileName, outputStream);
            log.info("下载成功 结束时间:{}", new Date());
        } catch (FileNotFoundException e) {
            log.error("没有找到文件", e);
            throw VideoException.VIDEO_UPLOAD_FAILED;
        } catch (SocketException e) {
            log.error("连接FTP失败", e);
            throw VideoException.FTP_CONNECT_VERIFY;
        } catch (IOException e) {
            log.error("文件读取错误", e);
            throw VideoException.FTPFILE_READ;
        } finally {
            try {
                outputStream.close();
            } catch (Exception e) {
                log.error("文件ID:{},文件流关闭失败", ftpPath, e);
            } finally {
                try {
                    ftpClient.logout();
                } catch (Exception e) {
                    log.error("文件ID:{},ftp关闭连接失败", ftpPath, e);
                }
            }

        }
        return filepath + "/" + downloadFileName;
    }

    /**
     * 获取FTPClient对象
     *
     * @param ftpHost     FTP主机服务器
     * @param ftpPassword FTP 登录密码
     * @param ftpUserName FTP登录用户名
     * @param ftpPort     FTP端口 默认为21
     * @return
     */
    public static FTPClient getFTPClient(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort, FTPClient ftpClient) {
        try {
            //设置缓存区大小
            ftpClient.setBufferSize(VideoConstants.cache);
            // 连接FTP服务器
            ftpClient.connect(ftpHost, ftpPort);
            // 登陆FTP服务器
            ftpClient.login(ftpUserName, ftpPassword);

            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                log.error("未连接到FTP，用户名或密码错误。");
                ftpClient.disconnect();
                throw VideoException.FTP_CONNECT_VERIFY;
            }
        } catch (Exception e) {
            log.error("FTP的IP地址可能错误，请正确配置。", e);
            throw VideoException.FTP_CONNECT;
        }
        return ftpClient;
    }

    //下面方法 为生成多级路径准备
    //改变目录路径
    public static boolean changeWorkingDirectory(String directory, FTPClient ftpClient) {
        boolean flag = true;
        try {
            flag = ftpClient.changeWorkingDirectory(directory);
            if (flag) {
                log.info("进入文件夹 {} 成功！", directory);
            } else {
                log.error("进入文件夹 {} 失败！,请确认文件路径是否正确", directory);
                throw VideoException.FTP_CHANGE_DIRECTORY;
            }
        } catch (IOException e) {
            log.error("进入文件夹 {} 时,发生异常！", e);
            throw VideoException.FTP_CHANGE_DIRECTORY;
        }
        return flag;
    }


    //创建目录
    public static boolean makeDirectory(String dir, FTPClient ftpClient) {
        boolean flag = true;
        try {
            flag = ftpClient.makeDirectory(dir);
            if (flag) {
                log.info("创建文件夹 {} 成功！", dir);

            } else {
                log.info("创建文件夹 {} 失败！", dir);
            }
        } catch (Exception e) {
            log.error("创建文件夹 {} 时,发生错误！", dir, e);
            throw VideoException.FTP_MAKE_DIRECTORY;
        }
        return flag;
    }

    //判断ftp服务器文件是否存在
    public static boolean existFile(String path, FTPClient ftpClient) {
        boolean flag = false;
        FTPFile[] ftpFileArr = new FTPFile[0];
        try {
            ftpFileArr = ftpClient.listFiles(path);
        } catch (IOException e) {
        }
        if (ftpFileArr.length > 0) {
            flag = true;
        }
        return flag;
    }
}
