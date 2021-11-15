package com.onnoa.shop.demo.video.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description 七牛配置信息
 * @author onnoA
 * @date 2021/7/6 23:18
 */
//@Component
public class QiniuConfig {

    /**
     * salt
     */
    public static String accessKey;

    @Value("${qiniu.accessKey}")
    public void setAccessKey(String accessKeyConf){
        accessKey = accessKeyConf;
    }
    /**
     * 密钥
     */
    public static String secretKey;

    @Value("${qiniu.secretKey}")
    public void setSecretKey(String secretKeyConf){
        secretKey = secretKeyConf;
    }
    /**
     * bucketname
     */
    public static String bucketname;

    @Value("${qiniu.bucketname}")
    public void setBucketname(String bucketnameConf){
        bucketname = bucketnameConf;
    }
    /**
     * 域
     */
    public static String domain;

    @Value("${qiniu.domain}")
    public void setDomain(String domainConf){
        domain = domainConf;
    }
}
