package com.onnoa.shop.common.distributed.lock.zookeeper.config;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description: Zk连接池
 * @Author: onnoA
 * @Date: 2020/6/14 16:51
 */
public abstract class ZkConnectionPool {
    /** zookeeper 连接缓冲池 */
    private static final HashMap<String, SingleConnectionPool> ZK_POOL = new HashMap<>();
    /** 连接池大小 */
    private static final int POOL_SIZE = 3;

    /***
     * 创建zk连接.如果存在，则从连接池中获取获取。
     * @param address
     * @param sessionTimeout
     * @param watcher
     * @return
     * @throws IOException
     */
    public synchronized static ZooKeeper createZookeeper(String address, int sessionTimeout, Watcher watcher) throws IOException {
        String poolKey = address + sessionTimeout;
        SingleConnectionPool pool = ZK_POOL.get(poolKey);
        if(pool == null ) {
            pool = new SingleConnectionPool(address, sessionTimeout, watcher, POOL_SIZE);
            ZK_POOL.put(poolKey, pool);
        }
        return pool.obtain();
    }



    public static void clearPool() {

    }

    private static class SingleConnectionPool {
        private static final Logger LOGGER = LoggerFactory.getLogger(SingleConnectionPool.class);

        private String address; //zk地址
        private int sessionTimeout; //回话超时
        private Watcher watcher; //监控器
        private int poolSize; //池大小
        private List<ZooKeeper> zkList; //zk连接池
        private int nextGetIndex = 0; //下一个索引

        public SingleConnectionPool(String address, int sessionTimeout, Watcher watcher, int poolSize) {
            this.address = address;
            this.sessionTimeout = sessionTimeout;
            this.watcher = watcher;
            this.poolSize = poolSize;
            this.zkList = new ArrayList<>(poolSize);
        }

        /**
         * 获得连接
         * @return
         * @throws IOException
         */
        public ZooKeeper obtain() throws IOException  {
            ZooKeeper zooKeeper;
            if(zkList.size() < nextGetIndex + 1) {
                zooKeeper = newZkCli(address, sessionTimeout, watcher);
                zkList.add(zooKeeper);
            } else {
                zooKeeper = zkList.get(nextGetIndex);
                assert zooKeeper != null;
                if(!zooKeeper.getState().isAlive()) {
                    try {
                        zooKeeper.close();
                    } catch (InterruptedException e) {
                        LOGGER.warn(e.toString(), e);
                    }
                    zooKeeper = newZkCli(address, sessionTimeout, watcher);
                    zkList.set(nextGetIndex, zooKeeper);
                }
            }
            nextGetIndex = (nextGetIndex + 1) % poolSize;
            return zooKeeper;
        }

        private static ZooKeeper newZkCli(String address, int sessionTimeout, Watcher watcher) throws IOException  {
            ZooKeeper _zk = new ZooKeeper(address, sessionTimeout, watcher);
            return _zk;
        }
    }
}

