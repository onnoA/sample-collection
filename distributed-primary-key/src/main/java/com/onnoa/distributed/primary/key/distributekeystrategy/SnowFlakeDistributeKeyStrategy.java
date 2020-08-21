package com.onnoa.distributed.primary.key.distributekeystrategy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import com.onnoa.shop.common.distributed.primarykey.snowflake.IdWorker;
import com.onnoa.shop.common.distributed.primarykey.snowflake.SnowflakeIdWorker;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/8/8 15:12
 */
@Service
@Slf4j
public class SnowFlakeDistributeKeyStrategy implements IDistributedKeyStrategy {

    @Override
    public String generate() {
        IdWorker idWorker = new IdWorker();
        List<String> list = Lists.newArrayList();
        for (int i = 0; i < 10000; i++) {
            String generateId = "101" + idWorker.nextId();
            String id = SnowflakeIdWorker.generateId();
            list.add(generateId);
            list.add(id);

        }
        list.add("111111");
        list.add("111111");
        Map<String, Long> map = list.stream().collect(Collectors.groupingBy(a -> a, Collectors.counting()));
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            if (entry.getValue() > 1) {
                log.info("重复的单号：{},重复的次数:{}", entry.getKey(), entry.getValue());
            }
        }
        return null;
    }
}
