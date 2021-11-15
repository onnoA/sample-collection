package com.onnoa.shop.demo.video.service;

import com.baomidou.mybatisplus.service.IService;
import com.onnoa.shop.demo.video.domain.VideoTranscode;
import com.onnoa.shop.demo.video.dto.VideoTranscodeDto;


/**
 * @Description: 视频转码信息表
 * @Author: jeecg-boot
 * @Date:   2020-03-25
 * @Version: V1.0
 */
public interface VideoTranscodeService extends IService<VideoTranscode> {

    VideoTranscode add(VideoTranscodeDto videoTranscodeDto);

    VideoTranscode queryByVideoTranscodeId(String id);

}
