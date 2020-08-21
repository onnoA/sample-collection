package com.onnoa.shop.common.distributed.lock.zookeeper.lock;

import com.google.common.collect.ImmutableMap;
import com.onnoa.shop.common.distributed.lock.zookeeper.config.ZkLockSupport;
import com.onnoa.shop.common.distributed.lock.zookeeper.exception.ZkLockException;
import com.onnoa.shop.common.properties.base.ShopProperties;
import com.onnoa.shop.common.spring.SpringContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: 基于ZooKeeper的高性能分布式锁 实现原理，底层采用zookeeper做分布式锁的支撑。
 * 一个资源锁在获取到之后，在处理完业务逻辑之后并不立即释放，
 * 首先检查本地是否还有请求需要使用该资源。如果有，该资源锁
 * 继续被本地占用。在以下情况释放锁，
 * 1、本地并没有请求该资源的请求。
 * 2、超过了本地所占用的最大时间。
 * @Author: onnoA
 * @Date: 2020/6/14 16:03
 */
public abstract class ZkDistributedLock implements IDistributedLock, Cloneable {

    private static final Logger logger = LoggerFactory.getLogger(ZkDistributedLock.class);
    private static ShopProperties sp;

    /*本地锁逻辑相关变量*/
    /**
     * 锁被占用
     */
    private AtomicBoolean lockUsed = new AtomicBoolean(false);
    /**
     * 当前对象的锁定对象
     */
    private ReentrantLock localLockObj = new ReentrantLock();
    /**
     * 等待计数,包括当前正在执行的线程
     */
    private AtomicInteger waitingCount = new AtomicInteger(0);
    /**
     * 获得锁的线程ID
     */
    private long lockedThreadId = 0L;


    /*zookeeper锁逻辑相关变量*/
    private static final String ZK_LOCK_PATH = "/shop/lock/";
    /**
     * 释放锁的最小时间
     */
    private static final int releaseLockTime = 5 * 1000; // 毫秒
    /**
     * 锁名称
     */
    private String lockName;
    /**
     * zk地址
     */
    private String zkAddress;
    /**
     * zk锁支持
     */
    private ZkLockSupport zkLock = null;
    /**
     * 锁存放路径,相对路径
     */
    private String lockRelativePath;
    /**
     * 获取锁的时间
     */
    private long lastGetZkLockTime = 0L;
    /**
     * 是否获得zk锁
     */
    private boolean isGotZkLock = false;

    private static volatile boolean inited = false;


    /*微粒锁相关变量*/
    /**
     * 微粒锁存储集合
     */
    private HashMap<String, ParticleLock> particleLocks = new HashMap<>();


    /***
     * 构造分布式锁对象。ZK地址从配置文件中读取
     * @param lockName 锁名称。一个类的实例锁必须保持唯一，相同名称的锁，是同一个锁。
     */
    public ZkDistributedLock(String lockName) {
        if (StringUtils.isBlank(lockName)) {
            throw new IllegalArgumentException("lockName");
        }
        this.lockName = lockName;
        this.lockRelativePath = this.getClass().getName();
        ShopProperties properties = getSp();
        if (properties != null && StringUtils.isNoneBlank(properties.getZk().getZkAddressList())) {
            this.zkAddress = properties.getZk().getZkAddressList();
            ensureZkSupport();
            this.inited = true;
        }
    }

    /***
     * 构造分布式锁对象
     * @param lockName 锁名称。一个类的实例锁必须保持唯一，相同名称的锁，是同一个锁。
     * @param zkAddress ZK地址。如："127.0.0.1:2181"
     */
    public ZkDistributedLock(String lockName, String zkAddress) {
        if (StringUtils.isBlank(lockName)) {
            throw new IllegalArgumentException("lockName");
        }
        this.lockName = lockName;
        this.zkAddress = zkAddress;
        this.lockRelativePath = this.getClass().getName();
        ensureZkSupport();
    }

    protected ZkDistributedLock(String lockRelativePath, String lockName, String zkAddress) {
        if (StringUtils.isBlank(lockName)) {
            throw new IllegalArgumentException("lockName");
        }
        this.lockName = lockName;
        this.zkAddress = zkAddress;
        this.lockRelativePath = lockRelativePath;
        ensureZkSupport();
    }

    /**
     * 释放Zk资源
     */
    @SuppressWarnings("unused")
    private void releaseZkSupport() {
        if (zkLock != null) {
            zkLock.close();
            zkLock = null;
        }
    }

    /**
     * 获取锁。如果锁被占用将一直等待，至到获取到锁。
     * 如果获取锁失败，将抛出异常。
     * 注意：在同一个线程中，同一个分布式锁对象，重复lock会产生永久等待。
     *
     * @throws ZkLockException 获取锁失败时抛出异常。
     */
    public void lock() throws ZkLockException {
        localLock();
        try {
            if (!zkLock()) {
                localUnlock();
                throw new ZkLockException("获取Zk锁失败");
            }
        } catch (Exception e) {
            logger.warn(e.toString());
            localUnlock();
            throw new ZkLockException();
        }
    }

    private synchronized void checkInited() {
        if (!this.inited) {
            this.zkAddress = getSp().getZk().getZkAddressList();
            ensureZkSupport();
            this.inited = true;
        }
    }

    /***
     * 释放锁
     */
    public void unlock() throws IllegalMonitorStateException, RuntimeException {
        if (lockUsed.get() == false) {
            throw new IllegalMonitorStateException("没有使用lock，不允许unlock");
        }
        if (Thread.currentThread().getId() != lockedThreadId) {
            throw new IllegalMonitorStateException("当前线程未获得锁，不允许释放锁");
        }
        if (noOtherThreadWait() || holdZkLockTimeGreaterReleaseTime()) {
            releaseZkLock();
        }
        localUnlock();
    }

    /***
     * 如果当前存在事务  则在事务结束后    释放锁
     * 如果当前不存在事务  则立即释放锁
     */
    public void unlockAfterCompletion() {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCompletion(int status) {
                    logger.debug("事务结束状态为: " + ImmutableMap.of(0, "COMMITTED", 1, "ROLLED_BACK", 2, "UNKNOWN").get(status));
                    unlock();
                }
            });
        } else {
            unlock();
        }
    }

    private boolean holdZkLockTimeGreaterReleaseTime() {
        return System.currentTimeMillis() - lastGetZkLockTime > releaseLockTime;
    }

    private boolean noOtherThreadWait() {
        return waitingCount.get() <= 1;
    }

    /**
     * 获取微粒锁 。<br>
     * 常见的：按单锁，按用户锁，按产品锁
     *
     * @param particle 微粒。如：订单号、用户id，产品id。注：请不要包含特殊字符：/
     */
    public ZkDistributedLock getParticleLock(String particle) {
        checkInited();
        ParticleLock pl = particleLocks.get(particle);
        if (pl == null) {
            synchronized (particleLocks) {
                pl = particleLocks.get(particle);
                if (pl == null) {
                    pl = new ParticleLock(this.lockRelativePath + "/" + lockName + "_ParticleLock", particle, zkAddress, this);
                    particleLocks.put(particle, pl);
                }
            }
        }
        return pl;
    }


    /**
     * 移除微粒锁
     *
     * @param particle 微粒
     */
    protected void removeParticleLock(String particle) {
        synchronized (particleLocks) {
            particleLocks.remove(particle);
        }
    }

    /***
     * 尝试获取锁。如果不能立即获取锁，立刻放回false。
     *
     * @return 是否成功获取锁
     */
    public boolean tryLock() {
        if (!localTryLock()) {
            return false;
        }
        try {
            if (zkTryLock()) {
                return true;
            } else {
                localUnlock();
                return false;
            }
        } catch (Exception e) {
            localUnlock();
            return false;
        }
    }

    private boolean localTryLock() {
        if (!localLockObj.tryLock()) {
            return false;
        }
        if (!lockUsed.compareAndSet(false, true)) {
            localLockObj.unlock();
            return false;
        }
        waitingCount.incrementAndGet();
        lockedThreadId = Thread.currentThread().getId();
        return true;
    }


    private void localLock() {
        waitingCount.incrementAndGet();
        localLockObj.lock();
        lockedThreadId = Thread.currentThread().getId();
        if (!lockUsed.compareAndSet(false, true)) {
            throw new ZkLockException("锁正在被使用，不能重复锁定");
        }
    }

    private void localUnlock() {
        if (!lockUsed.compareAndSet(true, false)) {
            throw new IllegalMonitorStateException("没有使用lock，不允许unlock");
        }
        waitingCount.decrementAndGet();
        lockedThreadId = 0L;
        localLockObj.unlock();
    }

    private boolean zkLock() {
        if (isGotZkLock == false) {
            ensureZkSupport();
            if (zkLock.lock()) {
                logger.debug("线程id:" + Thread.currentThread().getId() + "，调用ZK锁。当前进程的线程等待数量：" + waitingCount);
                lastGetZkLockTime = System.currentTimeMillis();
                isGotZkLock = true;
                return true;
            } else {
                lastGetZkLockTime = 0L;
                isGotZkLock = false;
                // releaseZkSupport();  //这里不释放ZK，为了保证访问ZK的速度。
                return false;
            }
        }
        return true;
    }

    private boolean zkTryLock() {
        if (isGotZkLock == false) {
            ensureZkSupport();
            if (zkLock.tryLock()) {
                logger.debug("线程id:" + Thread.currentThread().getId() + "，调用ZK try锁。当前进程的线程等待数量：" + waitingCount);
                lastGetZkLockTime = System.currentTimeMillis();
                isGotZkLock = true;
                return true;
            } else {
                lastGetZkLockTime = 0L;
                isGotZkLock = false;
                return false;
            }
        }
        return true;
    }

    private void releaseZkLock() {
        assert isGotZkLock == true;
        zkLock.unlock();
        logger.debug("线程id:" + Thread.currentThread().getId() + "，调用了zk unlock, 当前进程的线程等待数量:" + waitingCount + "，当前进程持有ZK锁时间(ms)：" + (System.currentTimeMillis() - lastGetZkLockTime));
        isGotZkLock = false;
        //		releaseZkSupport(); //这里不释放ZK，为了保证访问ZK的速度。
    }

    private void ensureZkSupport() {
        if (this.zkAddress == null) {
            this.zkAddress = getSp().getZk().getZkAddressList();
        }
        if (zkLock != null && zkLock.isClosed() == false) {
            return;
        }
        zkLock = new ZkLockSupport(ZK_LOCK_PATH + this.lockRelativePath + "/" + lockName, zkAddress);
    }


    /***
     * 微粒锁
     * @author 丁伟
     * @date 2017年8月15日
     * @version 1.0
     */
    private static class ParticleLock extends ZkDistributedLock {
        private ZkDistributedLock parent;
        private AtomicInteger lockedCount = new AtomicInteger(0);
        private String particle;

        public ParticleLock(String lockRelativePath, String particle, String zkAddress, ZkDistributedLock parent) {
            super(lockRelativePath, particle, zkAddress);
            if (parent == null) {
                throw new IllegalArgumentException("参数 parent不能为空");
            }
            this.parent = parent;
            this.particle = particle;
        }


        @Override
        public void lock() {
            lockedCount.incrementAndGet();
            super.lock();
        }

        @Override
        public void unlock() {
            super.unlock();
            int rc = lockedCount.decrementAndGet();
            if (rc <= 0) {
                parent.removeParticleLock(particle);
            }
        }
    }

    protected static synchronized ShopProperties getSp() {
        if (sp == null) {
            sp = SpringContextHolder.getBean(ShopProperties.class);
        }
        return sp;
    }
}
