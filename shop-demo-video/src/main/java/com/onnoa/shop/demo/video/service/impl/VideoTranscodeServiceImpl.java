package com.onnoa.shop.demo.video.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.onnoa.shop.demo.video.domain.VideoTranscode;
import com.onnoa.shop.demo.video.dto.VideoTranscodeDto;
import com.onnoa.shop.demo.video.mapper.VideoTranscodeMapper;
import com.onnoa.shop.demo.video.service.VideoTranscodeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Description: 视频转码信息表
 * @Author: jeecg-boot
 * @Date:   2020-03-25
 * @Version: V1.0
 */
@Service
public class VideoTranscodeServiceImpl extends ServiceImpl<VideoTranscodeMapper, VideoTranscode> implements VideoTranscodeService {

    @Override
    @Transactional
    public VideoTranscode add(VideoTranscodeDto videoTranscodeDto) {
        VideoTranscode videoTranscode = new VideoTranscode();
        BeanUtils.copyProperties(videoTranscodeDto, videoTranscode);
        this.baseMapper.insert(videoTranscode);
        return videoTranscode;
    }

    @Override
    public VideoTranscode queryByVideoTranscodeId(String id) {
        VideoTranscode videoTranscode = this.baseMapper.queryByVideoTranscodeId(id);
        if (videoTranscode == null) {
//            result.success("未找到对应实体");
        }
        return videoTranscode;
    }
}
