package com.onnoa.shop.common.distributed.lock.zookeeper.config;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Description: 提供zookeeper更高层级操作方法。
 * @Author: onnoA
 * @Date: 2020/6/14 16:49
 */
final class ZkSupport {
    private static final Logger LOG = LoggerFactory.getLogger(ZkSupport.class);

    private final ZooKeeper zkClient;

    /** 重试延时 */
    private long retryDelay = 500L;
    /** 重试次数 */
    private int retryCount = 10;

    private List<ACL> acl = ZooDefs.Ids.OPEN_ACL_UNSAFE;

    public ZkSupport(ZooKeeper zookeeper) {
        if(zookeeper == null) {
            throw new IllegalArgumentException("zookeeper 参数不能为空");
        }
        this.zkClient = zookeeper;
    }

    /**
     * 执行给定的操作。如果执行不成功，将重试执行（次数可以配置）
     *
     * @param operation
     * @return 如果执行失败，则抛出异常
     */
    public <T> T retryOperation(ZooKeeperOperation<T> operation, int retryCount) throws Exception{
        for (int i = 0; i < retryCount; i++) {
            try {
                return operation.execute();
            } catch (Exception e) {
                LOG.warn(e.toString());
                retryDelay(i);
            }
        }
        throw new Exception("zk操作执行失败。重试次数:" + retryCount);
    }

    /**
     * 确保路径存在。如果不存在，则创建一个永久的路径
     *
     * @param path
     */
    public void ensurePathExists(String path) {
        ensureExists(path, null, acl, CreateMode.PERSISTENT);
    }

    public ZooKeeper getZkClient() {
        return zkClient;
    }

    private void ensureExists(final String path, final byte[] data, final List<ACL> acl, final CreateMode flags) {
        try {
            retryOperation(new ZooKeeperOperation<Boolean>() {
                public Boolean execute() throws KeeperException, InterruptedException {
                    Stat stat = zkClient.exists(path, false);
                    if (stat != null) {
                        return true;
                    }
                    ensureExists(PathUtil.getParent(path), null, acl, flags);
                    zkClient.create(path, data, acl, flags);
                    return true;
                }
            }, retryCount);
        } catch (Exception e){
            throw new RuntimeException("无法保证路径Zookeeper路径存在。", e);
        }
    }

    /**
     * 重试等待
     *
     * @param attemptCount
     *            等待时长：attemptCount * retryDelay;
     */
    private void retryDelay(int attemptCount) {
        if (attemptCount > 0) {
            try {
                Thread.sleep(attemptCount * retryDelay);
            } catch (InterruptedException e) {
                LOG.debug("zookeeper重试等待异常: " + e, e);
            }
        }
    }

    static class PathUtil {
        private static boolean verifyPath(String path) {
            if (path == null) {
                return false;
            }
            return path.charAt(0)=='/' && path.charAt(path.length() - 1) != '/';

        }

        public static String getParent(String path) {
            if (!verifyPath(path)) {
                return "/";
            }
            String[] pathUnit = path.split("/");
            path = "";
            for (int i = 1; i < pathUnit.length - 1; i++) {
                path += "/" + pathUnit[i];
            }
            return path.equals("") ? "/" : path;
        }
    }

}
