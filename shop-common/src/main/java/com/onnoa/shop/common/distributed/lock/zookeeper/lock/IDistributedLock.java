package com.onnoa.shop.common.distributed.lock.zookeeper.lock;

import com.onnoa.shop.common.distributed.lock.zookeeper.exception.DistributedLockException;

/**
 * @Description: 分布式锁接口
 * @Author: onnoA
 * @Date: 2020/6/14 16:03
 */
public interface IDistributedLock {

    /**
     * 功能描述: 获取锁
     *
     * @date 2020/6/14 22:02
     */
    public void lock() throws DistributedLockException;

    /**
     * 功能描述: 释放锁
     *
     * @param
     * @return
     * @date 2020/6/14 21:44
     */
    public void unlock() throws IllegalMonitorStateException, RuntimeException;


    /**
     * 功能描述: 细粒度锁
     *
     * @param particle 微粒。如：订单号、用户id，产品id。注：请不要包含特殊字符：/
     * @return 所对象
     * @date 2020/6/14 21:43
     */
    public IDistributedLock getParticleLock(String particle);

    /**
     * 功能描述: 尝试获取锁。true 为获取锁成功
     *
     * @return 是否成功获取锁
     * @date 2020/6/14 21:22
     */
    public boolean tryLock();

}
