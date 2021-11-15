package com.onnoa.shop.demo.authority.system.config;

import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.Resource;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.onnoa.shop.common.properties.async.AsyncTaskProperties;
import com.onnoa.shop.common.properties.base.ShopProperties;

@Configuration
@EnableAsync
public class ShopAsyncExecutorConfig implements AsyncConfigurer {

    @Resource
    private ShopProperties shopProperties;

    @Bean(value = "authorityServiceAsyncExecutor")
    public ThreadPoolTaskExecutor asyncServiceExecutor() {
        AsyncTaskProperties task = shopProperties.getTask();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 配置核心线程数 允许线程池中允许同时运行的最大线程数。
        executor.setCorePoolSize(task.getCorePoolSize());
        // 配置最大线程数
        executor.setMaxPoolSize(task.getMaxPoolSize());
        // 线程池维护线程所允许的空闲时间，默认为60s
        executor.setKeepAliveSeconds(task.getKeepAliveSeconds());
        // 队列最大长度，一般需要设置值
        executor.setQueueCapacity(task.getQueueCapacity());
        // 配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix(task.getThreadNamePrefix());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 默认的拒绝策略是： AbortPolicy
        switch (task.getRejectedExecutionHandler()) {
            case "abortPolicy":
                executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
                break;
            case "callerRunsPolicy":
                executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
                break;
            case "discardOldestPolicy":
                executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
                break;
            case "discardPolicy":
                executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
                break;
            default:
                executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
                break;
        }
        // 执行初始化
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
