package com.onnoa.shop.common.distributed.lock.redis.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtils.class);

    private RedisTemplate<String, String> redisTemplate;

    private final static String INCREMENTERROR = "递增因子必须大于0";

    private ThreadLocal<String> lockFlag = new ThreadLocal<>();

    private static final String UNLOCK_LUA;

    private static final String GET_EXPIRE_LUA;

    static {
        UNLOCK_LUA = "if redis.call(\"get\",KEYS[1]) == ARGV[1] " +
                "then " +
                "    return redis.call(\"del\",KEYS[1]) " +
                "else " +
                "    return 0 " +
                "end ";
        GET_EXPIRE_LUA = "local val =  redis.call(\"get\",KEYS[1]);" +
                " if val ~=nil " +
                " then " +
                "   redis.call(\"PEXPIRE\",KEYS[1],ARGV[1]) " +
                "   return val;" +
                " else return val;" +
                "end ";
    }

    /**
     * 指定缓存失效时间
     *
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(KeyPrefix keyPrefix, String key, long time) {
        String realKey = keyPrefix.prefix() + key;
        try {
            if (time > 0) {
                redisTemplate.expire(realKey, keyPrefix.expiredTime(), TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("【RedisService】指定缓存失效时间异常,key={},msg={}", realKey, e.getMessage());
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(KeyPrefix keyPrefix, String key) {
        String realKey = keyPrefix.prefix() + key;
        return redisTemplate.getExpire(realKey, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @return true：存在 ，false：不存在
     */
    public boolean hasKey(KeyPrefix keyPrefix, String key) {
        String realKey = keyPrefix.prefix() + key;
        try {
            return redisTemplate.hasKey(realKey);
        } catch (Exception e) {
            LOGGER.error("【RedisService】判断key是否存在异常,key={},msg={}", realKey, e.getMessage());
            return false;
        }
    }

    /**
     * 删除缓存
     */
    public void del(KeyPrefix keyPrefix, String key) {
        String realKey = keyPrefix.prefix() + key;
        try {
            redisTemplate.delete(realKey);
        } catch (Exception e) {
            LOGGER.error("【RedisService】删除缓存存在异常,key={},msg={}", realKey, e.getMessage());
        }
    }

    /**
     * 批量模糊删除缓存
     */
    public void delBatch(KeyPrefix keyPrefix, String key) {
        String realKey = keyPrefix.prefix() + key;
        Set<String> keys = redisTemplate.keys(realKey + "*");
        try {
            redisTemplate.delete(keys);
        } catch (Exception e) {
            LOGGER.error("【RedisService】删除缓存存在异常,key={},msg={}", realKey, e.getMessage());
        }
    }

    /**
     * 缓存放入并设置时间
     *
     * @return true成功 false 失败
     */
    public boolean set(KeyPrefix keyPrefix, String key, String value) {
        String realKey = keyPrefix.prefix() + key;
        int time = keyPrefix.expiredTime();
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(realKey, value, time, TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(realKey, value);
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("【RedisService】设置缓存存在异常,key={},value={},msg={}", realKey, value, e.getMessage());
            return false;
        }
    }


    /**
     * 模糊查询redis
     *
     * @param keyPrefix
     * @param key
     * @return
     */
    public HashMap<String, String> getMap(KeyPrefix keyPrefix, String key) {
        String realKey = keyPrefix.prefix() + key;
        Set<String> keys = redisTemplate.keys(realKey + "*");
        HashMap<String, String> map = new HashMap<>();
        for (String key1 : keys) {
            Object value = redisTemplate.opsForValue().get(key1);
            map.put(key1, String.valueOf(value));
        }
        return map;
    }

    /**
     * 缓存获取
     *
     * @return 值
     */

    public Object get(KeyPrefix keyPrefix, String key) {
        String realKey = keyPrefix.prefix() + key;
        return realKey == null ? null : redisTemplate.opsForValue().get(realKey);
    }

    /**
     * 递增
     */
    public long incr(KeyPrefix keyPrefix, String key, long delta) {
        String realKey = keyPrefix.prefix() + key;
        if (delta < 0) {
            LOGGER.error("【RedisService】缓存递增存在异常,key={},msg={}", realKey, INCREMENTERROR);
        }
        return redisTemplate.opsForValue().increment(realKey, delta);
    }

    /**
     * 递减
     */
    public long decr(KeyPrefix keyPrefix, String key, long delta) {
        String realKey = keyPrefix.prefix() + key;
        if (delta < 0) {
            LOGGER.error("【RedisService】缓存递减存在异常,key={},msg={}", realKey, INCREMENTERROR);
        }
        return redisTemplate.opsForValue().increment(realKey, -delta);
    }

    /**
     * 加锁
     *
     * @param key
     * @param expire
     * @param retryTimes
     * @param sleepMillis
     * @return
     */
    public boolean lock(String key, long expire, int retryTimes, long sleepMillis) {
        boolean result = setRedis(key, expire);
        // 如果获取锁失败，按照传入的重试次数进行重试
        while ((!result) && retryTimes-- > 0) {
            try {
                LOGGER.info("lock failed, retrying..." + retryTimes);
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                return false;
            }
            result = setRedis(key, expire);
        }
        return result;
    }


    /**
     * 释放锁（只会释放当前线程对应锁）
     *
     * @param key
     * @return
     */
    public boolean releaseLock(String key) {
        // 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
        try {
            List<String> keys = new ArrayList<>();
            keys.add(key);
            List<String> args = new ArrayList<>();
            args.add(lockFlag.get());

            // 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
            // spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本

            Long result = redisTemplate.execute((RedisCallback<Long>) connection -> {
                Object nativeConnection = connection.getNativeConnection();
                // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
                // 集群模式
                if (nativeConnection instanceof JedisCluster) {
                    return (Long) ((JedisCluster) nativeConnection).eval(UNLOCK_LUA, keys, args);
                }

                // 单机模式
                else if (nativeConnection instanceof Jedis) {
                    return (Long) ((Jedis) nativeConnection).eval(UNLOCK_LUA, keys, args);
                }
                return 0L;
            });

            return result != null && result > 0;
        } catch (Exception e) {
            LOGGER.error("release lock occured an exception", e);
        }
        return false;
    }

    private boolean setRedis(String key, long expire) {
        try {
            String result = redisTemplate.execute((RedisCallback<String>) connection -> {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                String uuid = UUID.randomUUID().toString();
                lockFlag.set(uuid);
                return commands.set(key, uuid, "NX", "PX", expire);
            });
            return !StringUtils.isEmpty(result);
        } catch (Exception e) {
            LOGGER.error("set redis occured an exception", e);
        }
        return false;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        redisTemplate = (RedisTemplate<String, String>) applicationContext.getBean("redisTemplate");
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
    }
}
