package com.onnoa.shop.common.properties.async;

import lombok.Data;

@Data
public class AsyncTaskProperties {

    // 配置核心线程数
    private int corePoolSize = 5;

    // 配置最大线程数
    private int maxPoolSize = 10;

    // 队列最大长度，一般需要设置值
    private int queueCapacity = 200;

    // 线程池维护线程所允许的空闲时间，默认为60s
    private int keepAliveSeconds = 3000;

    // 线程前缀名
    private String threadNamePrefix = "shop-task-executor-";

    // 拒绝策略 abortPolicy callerRunsPolicy discardOldestPolicy discardPolicy
    private String rejectedExecutionHandler;
}
