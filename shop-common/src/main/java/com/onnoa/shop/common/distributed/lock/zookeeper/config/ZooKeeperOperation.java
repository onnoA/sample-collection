package com.onnoa.shop.common.distributed.lock.zookeeper.config;

/**
 * @Description: ZooKeeper回调操作
 * @Author: onnoA
 * @Date: 2020/6/14 16:50
 */
public interface ZooKeeperOperation<T> {
    T execute() throws Exception;
}
