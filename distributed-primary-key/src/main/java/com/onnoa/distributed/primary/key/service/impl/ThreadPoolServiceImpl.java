package com.onnoa.distributed.primary.key.service.impl;

import com.onnoa.distributed.primary.key.service.ThreadPoolService;
import com.onnoa.distributed.primary.key.util.ThreadExecutor;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/8/10 15:43
 */
@Service
public class ThreadPoolServiceImpl implements ThreadPoolService {

    @Override
    public void create() {
        ThreadExecutor.execute(()->{
            System.out.println("线程执行");
        });

    }
}
