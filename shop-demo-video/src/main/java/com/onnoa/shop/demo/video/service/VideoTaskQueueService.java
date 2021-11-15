package com.onnoa.shop.demo.video.service;

import com.baomidou.mybatisplus.service.IService;
import com.onnoa.shop.demo.video.domain.VideoTaskQueue;

public interface VideoTaskQueueService extends IService<VideoTaskQueue> {

    void transcodeFailureJoinQueue();
}
