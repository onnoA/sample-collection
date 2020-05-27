package com.onnoa.shop.common.redis.cache;

import com.onnoa.shop.common.redis.cache.impl.RedisKvOperate;
import com.onnoa.shop.common.spring.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description: redis操作类。用于提供针对缓存简单调用的方法。
 * 所有的操作方法，参数中的Key都会拼接上初始对象时的前缀，用于防止Key重名。
 * @Author: onnoA
 * @Date: 2020/5/20 15:56
 */
public abstract class CacheFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheFacade.class);

    // 默认超时时间为一天
    private static int DefaultTimeoutSeconds = 1 * 24 * 60 * 60;


    private final String keyPrefix;

    protected CacheFacade(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    /**
     * 获取设置缓存的Key前缀
     *
     * @return
     */
    public String getKeyPrefix() {
        return keyPrefix;
    }

    /**
     * 获取值.如果Redis报错，返回NULL。
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        try {
            key = getKeyPrefix() + key;
            return getRedis().get(key);
        } catch (Exception e) {
            LOGGER.error("Redis出错！！！", e);
            return null;
        }
    }

    /**
     * 根据Key获取对象
     *
     * @param key
     * @param clazz 返回的对象类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public <TValue> TValue get(String key, Class<TValue> clazz) {
        return (TValue) get(key);
    }

    /**
     * 设置缓存。默认缓存时间1天
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        return this.set(key, value, DefaultTimeoutSeconds);
    }

    /**
     * 判断是否缓存了key.
     *
     * @param key
     * @return
     */
    public boolean exists(String key) {
        try {
            key = getKeyPrefix() + key;
            return getRedis().exists(key);
        } catch (Exception e) {
            LOGGER.error("Redis出错！！！", e);
            return false;
        }
    }

    /**
     * 设置key过期
     *
     * @param key
     * @param timeoutSeconds
     */
    public void expire(String key, int timeoutSeconds) {
        try {
            key = getKeyPrefix() + key;
            getRedis().expire(key, timeoutSeconds);
        } catch (Exception e) {
            LOGGER.error("Redis出错！！！", e);
        }
    }

    /**
     * 设置缓存。并且指定超时时间。
     *
     * @param key
     * @param value
     * @param timeoutSeconds 过期时间:秒; -1 表示永不过期
     */
    public boolean set(String key, Object value, int timeoutSeconds) {
        try {
            key = getKeyPrefix() + key;
            if (timeoutSeconds <= -1) {
                getRedis().set(key, value);
            } else {
                getRedis().set(key, value, timeoutSeconds);
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("Redis出错！！！", e);
            return false;
        }
    }

    /**
     * 删除keys
     *
     * @param keys
     */
    public void delete(Set<String> keys) {
        try {
            if (CollectionUtils.isEmpty(keys)) {
                return;
            }
            getRedis().delete(keys);
        } catch (Exception e) {
            LOGGER.error("Redis出错！！！", e);
        }
    }

    /**
     * 删除key
     *
     * @param key
     */
    public void del(String key) {
        try {
            key = getKeyPrefix() + key;
            getRedis().del(key);
        } catch (Exception e) {
            LOGGER.error("Redis出错！！！", e);
        }
    }

    /**
     * 自增加1
     *
     * @param key
     * @return
     */
    public long inc(String key) {
        return this.incBy(key, 1);
    }

    /**
     * 自增加 step
     *
     * @param key
     * @param step
     * @return
     */
    public long incBy(String key, long step) {
        try {
            key = getKeyPrefix() + key;
            return getRedis().incBy(key, step);
        } catch (Exception e) {
            LOGGER.error("Redis incBy 出错！！！", e);
            return 0;
        }
    }

    /**
     * 根据Key前缀获取键集合.注意，这里的前缀包含了构造对象时的前缀。例如：common:demo:
     *
     * @param prefix 键前缀，可为null。这个前缀会和拼接到内置前缀后面。如:keyPrefix=abc，那么实际的键前缀是：common:demo:abc。
     * @return
     */
    public Set<String> keys(String prefix) {
        prefix = prefix == null ? "" : prefix;
        try {
            prefix = getKeyPrefix() + prefix;
            return getRedis().keys(prefix);
        } catch (Exception e) {
            LOGGER.error("Redis出错！！！", e);
            return new HashSet<>();
        }
    }

    /**
     * 根据前缀删除匹配的所有键。注意，这里的前缀包含了构造对象时的前缀。例如：common:demo:
     *
     * @param prefix 键前缀，可为null。这个前缀会和拼接到内置前缀后面。如:keyPrefix=abc，那么实际的键前缀是：common:demo:abc。
     */
    public void delKeysByPrefix(String prefix) {
        prefix = prefix == null ? "" : prefix;
        try {
            prefix = getKeyPrefix() + prefix;
            getRedis().delKeysByPrefix(prefix);
        } catch (Exception e) {
            LOGGER.error("Redis出错！！！", e);
        }
    }

    /**
     * 根据所有的keys，获取所有的值
     *
     * @param keys
     * @return
     */
    public List<Object> multiGet(Collection<String> keys) {
        try {
            return getRedis().multiGet(keys);
        } catch (Exception e) {
            LOGGER.error("Redis出错！！！", e);
            return Collections.emptyList();
        }
    }

    /**
     * 获取列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。
     * 如果负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推
     */
    public List<Object> range(String key, long start, long end) {
        try {
            key = getKeyPrefix() + key;
            return getRedis().range(key, start, end);
        } catch (Exception e) {
            LOGGER.error("Redis出错！！！", e);
            return Collections.emptyList();
        }
    }

    /**
     * 获取列表头部的一个值
     */
    public Object lpop(String key) {
        try {
            key = getKeyPrefix() + key;
            return getRedis().lpop(key);
        } catch (Exception e) {
            LOGGER.error("Redis出错！！！", e);
            return null;
        }
    }

    /**
     * 将一个值插入列表头部
     */
    public long lpush(String key, Object value) {
        return lpush(key, value, DefaultTimeoutSeconds);
    }

    /**
     * 将一个值插入列表头部，并设置超时时间
     *
     * @param timeoutSeconds 超时秒数,-1表示永不超时
     */
    public long lpush(String key, Object value, int timeoutSeconds) {
        try {
            key = getKeyPrefix() + key;
            long size = getRedis().lpush(key, value);
            if (timeoutSeconds > -1) {
                expire(key, DefaultTimeoutSeconds);
            }
            return size;
        } catch (Exception e) {
            LOGGER.error("Redis出错！！！", e);
            return 0;
        }
    }

    /**
     * set 操作end
     */

    private static KvOperate redisClient = SpringContextHolder.getBean(RedisKvOperate.class);

    protected KvOperate getRedis() {
        if (redisClient == null) {
            redisClient = SpringContextHolder.getBean(RedisKvOperate.class);
        }
        return redisClient;
    }

}
