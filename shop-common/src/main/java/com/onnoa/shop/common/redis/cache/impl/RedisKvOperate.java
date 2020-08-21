package com.onnoa.shop.common.redis.cache.impl;

import com.onnoa.shop.common.redis.cache.KvOperate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Description: redis操作类实现类
 * @Author: onnoA
 * @Date: 2020/5/20 15:56
 */
@Component
public class RedisKvOperate implements KvOperate {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, Object value, int timeoutSecond) {
        redisTemplate.opsForValue().set(key, value, timeoutSecond, TimeUnit.SECONDS);
    }

    @Override
    public void expire(String key, int timeoutSecond) {
        redisTemplate.expire(key, timeoutSecond, TimeUnit.SECONDS);
    }

    @Override
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    @Override
    public void delete(Set<String> keys) {
        redisTemplate.delete(keys);
    }

    @Override
    public void del(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public long inc(String key) {
        return incBy(key, 1);
    }

    @Override
    public long incBy(String key, long step) {
        return redisTemplate.opsForValue().increment(key, step);
    }

    @Override
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 根据前缀删除所有key
     */
    @Override
    public void delKeysByPrefix(String keyPrefix) {
        Set<String> keys = keys(keyPrefix);
        for (String key : keys) {
            del(key);
        }
    }

    /**
     * 根据keys获取所有的value,返回的顺序和keys一致
     * Damon
     */
    @Override
    public List<Object> multiGet(Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    @Override
    public long lpush(String key, Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    @Override
    public Object lpop(String key) {
        return key == null ? null : redisTemplate.opsForList().leftPop(key);
    }

    @Override
    public List<Object> range(String key, long start, long end) {
        return key == null ? null : redisTemplate.opsForList().range(key, start, end);
    }

}
