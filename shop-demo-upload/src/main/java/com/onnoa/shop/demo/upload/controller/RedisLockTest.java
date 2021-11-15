package com.onnoa.shop.demo.upload.controller;

import com.onnoa.shop.common.distributed.lock.redis.lock.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class RedisLockTest {

    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping("/redis_lock")
    public String index() {
        try {
            boolean result = redisUtils.lock("test", 3000L, 4, 1000L);
            if (!result) {
//                log.info(String.format("获取锁失败,锁对象为:%s", redisUtils.get(null, "test")));
                return "抢购失败，请重试.....";
            }
//            UserKey userKey = new UserKey(PrefixConstant.USER);
            Integer stock = 100;
            Integer realStock = 0;
            if (stock > 0) {
                realStock = stock - 1;
                System.out.println("抢购成功......剩余库存" + realStock);
//                redisUtils.set(userKey, "stock", String.valueOf(realStock));
            }
        } finally {
            // 释放锁
            redisUtils.releaseLock("test");
        }
        return "redis lock success ... ";
    }
}
