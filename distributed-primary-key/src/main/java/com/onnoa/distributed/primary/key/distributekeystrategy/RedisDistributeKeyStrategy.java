package com.onnoa.distributed.primary.key.distributekeystrategy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onnoa.distributed.primary.key.constants.SystemConstants;
import com.onnoa.distributed.primary.key.service.RedisService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/8/8 15:10
 */
@Service
@Slf4j
public class RedisDistributeKeyStrategy implements IDistributedKeyStrategy {

    @Autowired
    private RedisService redisService;

    @Override
    public String generate() {

        List<String> list = Lists.newArrayList();
        StringBuilder sb = null;
        for (int i = 0; i < 10000; i++) {
            sb = new StringBuilder();
            String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String key = SystemConstants.REDIS_KEY_PREFIX + date;
            Long increment = redisService.increment(key, 1);
            sb.append(date);
            /*
             * sb.append(String.format("%02d", order.getSourceType())); sb.append(String.format("%02d",
             * order.getPayType()));
             */
            String incrementStr = increment.toString();
            if (incrementStr.length() <= 6) {
                sb.append(String.format("%06d", increment));
            }
            else {
                sb.append(incrementStr);
            }

        }
        list.add("111111");
        list.add("111111");
        Map<String, Long> map = list.stream().collect(Collectors.groupingBy(a -> a, Collectors.counting()));
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            if (entry.getValue() > 1) {
                log.info("重复的单号：{},重复的次数:{}", entry.getKey(), entry.getValue());
            }
        }
        return sb.toString();
    }
}
