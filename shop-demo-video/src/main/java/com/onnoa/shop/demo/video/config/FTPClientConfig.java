package com.onnoa.shop.demo.video.config;

import com.onnoa.shop.demo.video.exception.VideoException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
@Configuration
public class FTPClientConfig {
    @Value("${ftp.ftpHost}")
    private String ftpHost;
    @Value("${ftp.ftpPort}")
    private Integer ftpPort;
    @Value("${ftp.ftpUserName}")
    private String ftpUserName;
    @Value("${ftp.ftpPassword}")
    private String ftpPassword;


    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public FTPClient ftpClient() {
        FTPClient ftpClient = new FTPClient();
        try {

            //设置缓存区大小
            ftpClient.setBufferSize(1024 * 1024);
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
        log.info("FTP建连接成功" + ftpClient.hashCode());
        return ftpClient;
    }

    public FTPClient getFTPClient() {
        FTPClient ftpClient = new FTPClient();
        try {
            //设置缓存区大小
            ftpClient.setBufferSize(1024 * 1024);
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
        log.info("FTP建连接成功" + ftpClient.hashCode());
        return ftpClient;
    }
}
