package com.onnoa.shop.demo.video.config;


import com.onnoa.shop.demo.video.constant.VideoInfoConstant;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Data
@Configuration
public class TranscodeParameterConfig implements InitializingBean {
    @Value("${videostream.transcode-parameter.type.640x360}")
    private String type640x360;

    @Value("${videostream.transcode-parameter.type.854x480}")
    private String type854x480;

    @Value("${videostream.transcode-parameter.type.1280x720}")
    private String type1280x720;

    @Value("${videostream.transcode-parameter.type.1920x1080}")
    private String type1920x1080;

    @Value("${videostream.transcode-parameter.type.360x640}")
    private String type360x640;

    @Value("${videostream.transcode-parameter.type.480x854}")
    private String type480x854;

    @Value("${videostream.transcode-parameter.type.720x1280}")
    private String type720x1280;

    @Value("${videostream.transcode-parameter.type.1080x1920}")
    private String type1080x1920;

    private Map<String,Map<String,String>> paramMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        this.combinationParameter(VideoInfoConstant.LANDSCAPE_STANDARD_DEFINITION, type640x360);
        this.combinationParameter(VideoInfoConstant.PORTRAIT_STANDARD_DEFINITION, type360x640);

        this.combinationParameter(VideoInfoConstant.LANDSCAPE_HIGH_DEFINITION, type854x480);
        this.combinationParameter(VideoInfoConstant.PORTRAIT_HIGH_DEFINITION, type480x854);

        this.combinationParameter(VideoInfoConstant.LANDSCAPE_ULTRA_DEFINITION, type1280x720);
        this.combinationParameter(VideoInfoConstant.PORTRAIT_ULTRA_DEFINITION, type720x1280);

        this.combinationParameter(VideoInfoConstant.LANDSCAPE_BLURAY_DEFINITION, type1920x1080);
        this.combinationParameter(VideoInfoConstant.PORTRAIT_BLURAY_DEFINITION, type1080x1920);
    }

    private void combinationParameter(String px, String param) {
        Map<String,String> map = new HashMap<>();
        String[] split = param.split("/");
        for (String arr : split) {
            String[] types = arr.split("\\|");
            map.put(types[0],types[1]);
        }
        paramMap.put(px, map);
    }
}
