package com.onnoa.shop.demo.video.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.onnoa.shop.demo.video.domain.VideoTaskQueue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/6/8 10:50
 */
public interface VideoTaskQueueMapper extends BaseMapper<VideoTaskQueue> {

    /**
     * 查询最近超过某个时间的数据
     * @param data 分钟格式
     * @return
     */
    List<String> queryIdsByData(@Param("data") Integer data);
}
