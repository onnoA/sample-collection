package com.onnoa.shop.demo.video.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.onnoa.shop.demo.video.domain.VideoTaskQueue;
import com.onnoa.shop.demo.video.mapper.VideoTaskQueueMapper;
import com.onnoa.shop.demo.video.service.VideoTaskQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class VideoTaskQueueServiceImpl extends ServiceImpl<VideoTaskQueueMapper, VideoTaskQueue> implements VideoTaskQueueService {

    @Autowired
    private VideoTaskQueueMapper videoTaskQueueMapper;

    @Override
    public void transcodeFailureJoinQueue() {
        /**
         * 如果视频过大，转码时间太长，可能也会判定消息异常丢失，会重新加入redis队列的任务
         */
        //查询超过时间未处理完成的任务
        long start = System.currentTimeMillis();
        log.info(Thread.currentThread().getName() + "开始执行TranscodeFailureJoinQueueScheduledJob逻辑，当前时间{}",new Date());
        List<String> videoIds = videoTaskQueueMapper.queryIdsByData(120);
        log.info(Thread.currentThread().getName() + "超过时间未处理完成的任务:{}",videoIds);
        //查询目前redis队列里存在的任务
//        RQueue<String> queue = redisson.getQueue(VideoInfoConstant.VS_VIDEO_PROCESSING_VIDEO_PROC_UNFINISHED_QUEUE);
//        List<String> rvideoIds = queue.readAll();
//        videoIds.removeAll(rvideoIds);
        log.info(Thread.currentThread().getName() + "目前redis队列里存在的任务:, job执行重新加入redis队列的任务:{}",videoIds);
//        queue.addAll(videoIds);
        if(!videoIds.isEmpty()){
            List<VideoTaskQueue> videoTaskQueues = this.selectList(new EntityWrapper<VideoTaskQueue>()
                    .in("task_id", videoIds)
                    .in("status", new Integer[]{0, 10}));
            //修改更新时间
            this.updateBatchById(videoTaskQueues);
        }
        log.info(Thread.currentThread().getName() + "执行TranscodeFailureJoinQueueScheduledJob完成{}s", (System.currentTimeMillis() - start)/1000);
    }

}
