package com.onnoa.shop.common.distributed.lock.redis.lock;

public interface KeyPrefix {
    public int expiredTime();

    public String prefix();
}
