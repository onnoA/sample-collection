package com.onnoa.shop.demo.video.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.onnoa.shop.demo.video.domain.VideoTranscode;
import org.apache.ibatis.annotations.Param;


/**
 * @Description: 视频转码信息表
 * @Author: jeecg-boot
 * @Date:   2020-03-25
 * @Version: V1.0
 */
public interface VideoTranscodeMapper extends BaseMapper<VideoTranscode> {

    VideoTranscode queryByVideoTranscodeId(@Param("id") String id);

}
