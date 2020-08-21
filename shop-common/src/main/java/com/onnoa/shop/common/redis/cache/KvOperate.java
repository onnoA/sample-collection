package com.onnoa.shop.common.redis.cache;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @Description: redis操作类接口
 * @Author: onnoA
 * @Date: 2020/5/20 15:56
 */
public interface KvOperate {

    void set(String key, Object value);

    void set(String key, Object value, int timeoutSecond);

    void expire(String key, int timeoutSecond);

    Object get(String key);

    void delete(Set<String> keys);

    void del(String key);

    boolean exists(String key);

    long inc(String key);

    long incBy(String key, long step);

    Set<String> keys(String pattern);

    void delKeysByPrefix(String keyPrefix);

    List<Object> multiGet(Collection<String> keys);

    long lpush(String key, Object value);

    Object lpop(String key);

    List<Object> range(String key, long start, long end);
}
