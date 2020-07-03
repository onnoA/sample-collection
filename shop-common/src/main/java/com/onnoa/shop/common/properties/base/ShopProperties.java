package com.onnoa.shop.common.properties.base;

import com.onnoa.shop.common.constant.GlobalConstant;
import com.onnoa.shop.common.distributed.lock.zookeeper.properties.ZookeeperProperties;
import com.onnoa.shop.common.properties.qiniu.QiniuProperties;
import com.onnoa.shop.common.properties.video.VideoFFMpegProperties;
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
public class ShopProperties {

    public VideoFFMpegProperties video = new VideoFFMpegProperties();

    // 七牛配置
    public QiniuProperties qiniu = new QiniuProperties();

    // zookeeper配置
    private ZookeeperProperties zk = new ZookeeperProperties();

}
