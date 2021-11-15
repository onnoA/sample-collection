package com.onnoa.shop.demo.video.config;

import com.onnoa.shop.demo.video.constant.GlobalConstant;
import com.onnoa.shop.demo.video.properties.VideoFFMpegProperties;
import com.onnoa.shop.demo.video.properties.qiniu.QiniuProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description: 自定义全局变量
 * @Author: onnoA
 * @Date: 2020/6/6 11:46
 */
@Data
@ConfigurationProperties(prefix = GlobalConstant.ROOT_PREFIX)
@Component
public class ShopProperties {

    public VideoFFMpegProperties video = new VideoFFMpegProperties();

    // 七牛配置
    public QiniuProperties qiniu = new QiniuProperties();


}
