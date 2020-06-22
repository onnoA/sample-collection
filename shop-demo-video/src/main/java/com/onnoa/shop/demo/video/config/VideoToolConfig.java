package com.onnoa.shop.demo.video.config;

import com.onnoa.shop.common.exception.ServiceException;
import com.onnoa.shop.common.properties.base.ShopProperties;
import com.onnoa.shop.common.properties.video.VideoFFMpegProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class VideoToolConfig {

    /*@Bean
    public VideoTool qiniuUtil(ShopProperties shopProperties) {
        VideoFFMpegProperties videoProperties = Optional
                .of(shopProperties)
                .map(ShopProperties::getVideo)
                .orElseThrow(() -> ServiceException.COMMON_PARAMS_NOT_NULL.format("视频流ffmpeg配置: shop.video.ffmpeg"));

        return new VideoTool();
    }*/

}
