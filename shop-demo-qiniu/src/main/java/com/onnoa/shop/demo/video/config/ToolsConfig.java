package com.onnoa.shop.demo.video.config;

import com.onnoa.shop.common.exception.ServiceException;
import com.onnoa.shop.common.properties.base.ShopProperties;
import com.onnoa.shop.common.properties.qiniu.QiniuProperties;
import com.onnoa.shop.demo.video.service.QiniuTool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-02 11:05
 */
@Configuration
public class ToolsConfig {

    @Bean
    public QiniuTool qiniuUtil(ShopProperties shopProperties) {
        QiniuProperties qiniuProperties = Optional
                .of(shopProperties)
                .map(ShopProperties::getQiniu)
                .orElseThrow(() -> ServiceException.COMMON_PARAMS_NOT_NULL.format("七牛配置: shop.qiniu"));

        QiniuProperties.QiniuKeyProperties key = qiniuProperties.getKey();
        QiniuProperties.QiniuOssProperties oss = qiniuProperties.getOss();

        return new QiniuTool(oss.getBucketName(), oss.getPublicHost(), key.getAccessKey(), key.getSecretKey());
    }

}
