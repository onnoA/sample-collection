package com.onnoa.shop.common.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "ftp")
public class FtpClientProperties {

    /**
     *  ftp ip 地址
     */
    private InetAddress host ;

    private Integer port = 22;

    private String username;

    private String pwd;

    private ContainerType containerType = ContainerType.SIMPLE;

    private Host testHost;

    // ... getter and setters

    public enum ContainerType {

        SIMPLE,
        DIRECT

    }

    // ... getter and setters

    @Data
    public static class Host {

        private String ip;

        private int port;

        // ... getter and setters

    }


}
