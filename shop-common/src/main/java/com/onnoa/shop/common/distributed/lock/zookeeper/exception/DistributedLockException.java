package com.onnoa.shop.common.distributed.lock.zookeeper.exception;

/**
 * @Description: 分布式锁异常
 * @Author: onnoA
 * @Date: 2020/6/14 16:03
 */
public class DistributedLockException extends RuntimeException {

    public DistributedLockException(){
        super();
    }

    public DistributedLockException(String message){
        super(message);
    }

    public DistributedLockException(String message, Throwable cause) {
        super(message, cause);
    }
}
