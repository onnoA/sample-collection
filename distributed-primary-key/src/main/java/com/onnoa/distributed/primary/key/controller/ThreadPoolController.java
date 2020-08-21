package com.onnoa.distributed.primary.key.controller;

import com.onnoa.distributed.primary.key.service.ThreadPoolService;
import com.onnoa.distributed.primary.key.util.FixedThreadExecutor;
import com.onnoa.shop.common.result.ResultBean;
import com.onnoa.shop.common.utils.MapParamUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/8/10 15:42
 */
@RestController
@RequestMapping(value = "/thread")
@Slf4j
public class ThreadPoolController {
    @Autowired
    private ThreadPoolService threadPoolService;

    @PostMapping(value = "/create")
    public ResultBean createThreadPool() {
        threadPoolService.create();
        FixedThreadExecutor.execute(() -> {
            for (int i = 0; i < 100; i++) {
                log.info("执行另一个线程:{}", i);
            }
        });
        return ResultBean.success(null);
    }

    @PostMapping(value = "/json")
    public ResultBean json( Map params) {
        String test = MapParamUtil.getStringValue(params, "test");
        return ResultBean.success(test);

    }
}
