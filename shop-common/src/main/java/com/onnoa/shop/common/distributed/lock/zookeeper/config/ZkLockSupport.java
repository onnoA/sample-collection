package com.onnoa.shop.common.distributed.lock.zookeeper.config;

import org.apache.zookeeper.*;
import org.apache.zookeeper.KeeperException.NoNodeException;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @Description: 分布式锁实现
 * @Author: onnoA
 * @Date: 2020/6/14 16:03
 */
public class ZkLockSupport {
	private static final Logger logger = LoggerFactory.getLogger(ZkLockSupport.class);

	/** Zookeeper 会话失效时间 */
	private static final int ZK_SESSION_TIMEOUT = 10 * 1000; //10 秒钟
	private static final int retryCount = 10; //zk重试次数

	private final Object wakeUpWatingLockObj = new Object();
	private ZkSupport zkSupport;

	private String path;
	private final String prefix;
	private String waitingPath;
	private ZkNodeName nodeName = null;
	private String zkAddress;
	private boolean isClosed = false;


	public ZkLockSupport(String path, String zkAddress) {
		this.path = path;
		this.prefix = "lock" + ZkNodeName.splitChar;
		this.zkAddress = zkAddress;
		try {
			ensureZookeeper();
		} catch (IOException e) {
		}
	}

	private void ensureZookeeper() throws IOException {
		if(this.zkSupport == null || !this.zkSupport.getZkClient().getState().isAlive()) {
			this.zkSupport = new ZkSupport(createZookeeper(zkAddress));
		}
	}

	private static ZooKeeper createZookeeper(String address) throws IOException {
		try {
			return ZkConnectionPool.createZookeeper(address, ZK_SESSION_TIMEOUT, new Watcher() {
				@Override
				public void process(WatchedEvent event) {
					logger.debug("zk watch: event=" + event.getType() + ", path=" + event.getPath());
				}
			});
		} catch (IOException e) {
			logger.error("连接zk失败：" + e.toString(), e);
			throw e;
		}
	}

	/**
	 * 获取锁<br>
	 * 使用完后一定要使用unlock释放锁。否则会导致资源永远不被释放
	 * @return
	 */
	public boolean lock(){
		if(isClosed) {
			throw new RuntimeException("已经关闭");
		}
		try {
			ensureZookeeper();
			if(!createLockNode()) {
				return false;
			}
			//获取所有子节点
			SortedSet<ZkNodeName> allChildrenLockNodes = getAllChildrenLockNodes();
			if(allChildrenLockNodes == null || allChildrenLockNodes.size() == 0) {
				deleteCurrentNode();
				deleteParentNodeWhenNoChildren();
				nodeName = null;
				return false;
			}

			if(currentNodeIsMinNode(allChildrenLockNodes)) {
				return true;
			}

			return waitLessThanMeNodeRelease(allChildrenLockNodes);
		} catch(Exception e) {
			logger.error("zk锁失败" + e.toString(), e);
			deleteCurrentNode();
			deleteParentNodeWhenNoChildren();
			nodeName = null;
			return false;
		}
	}

	public boolean tryLock() {
		if(isClosed) {
			return false;
		}
		try {
			ensureZookeeper();
			zkSupport.ensurePathExists(path);
			if(currentNodeHasChildren()) {
				return false;
			}
			if(!createLockNode()) {
				return false;
			}
			//获取所有子节点
			SortedSet<ZkNodeName> allChildrenLockNodes = getAllChildrenLockNodes();
			if (currentNodeIsMinNode(allChildrenLockNodes)) {
				return true;
			} else {
				deleteCurrentNode();
				deleteParentNodeWhenNoChildren();
				nodeName = null;
				return false;
			}
		} catch(Exception e) {
			logger.error("zk锁失败" + e.toString(), e);
			deleteCurrentNode();
			deleteParentNodeWhenNoChildren();
			nodeName = null;
			return false;
		}
	}

	private boolean currentNodeHasChildren() {
		try {
			return zkSupport.retryOperation(new ZooKeeperOperation<Boolean>() {
				@Override
				public Boolean execute() throws Exception {
					Stat nodeStat = zkSupport.getZkClient().exists(path, false);
					return nodeStat != null && nodeStat.getNumChildren() > 0;
				}
			}, retryCount);
		} catch (Exception e) {
			return false;
		}
	}

	public void unlock() {
		if(isClosed) {
			throw new RuntimeException("已经关闭");
		}
		try {
			ensureZookeeper();
			deleteCurrentNode();
			deleteParentNodeWhenNoChildren();
		} catch (Exception e) {
			logger.error("zk分布式锁解锁失败：" + e.toString(), e);
		} finally {
			nodeName = null;
		}
	}
	private void deleteParentNodeWhenNoChildren() {
		try {
			zkSupport.retryOperation(new ZooKeeperOperation<Boolean>() {

				@Override
				public Boolean execute() throws Exception {
					if(nodeName == null) {
						return false;
					}
					Stat nodeStat = zkSupport.getZkClient().exists(ZkSupport.PathUtil.getParent(nodeName.getNodePath()), null);
					if(nodeStat == null) {
						logger.debug(ZkSupport.PathUtil.getParent(nodeName.getNodePath()) + " 节点不存在");
						return true;
					}
					logger.debug(ZkSupport.PathUtil.getParent(nodeName.getNodePath()) + " 子节点个数：" + nodeStat.getNumChildren());
					if(nodeStat.getNumChildren() > 0) {
						return false;
					}
					try {
						zkSupport.getZkClient().delete(ZkSupport.PathUtil.getParent(nodeName.getNodePath()), nodeStat.getVersion());
					} catch (NoNodeException e) {
						logger.warn("分布式锁->删除的父节点不存在：" + nodeName.getNodePath());
						return true;
					}
					return true;
				}
			}, retryCount);
		} catch (Exception e) {
		}
	}

	private void deleteCurrentNode() {
		try {
			zkSupport.retryOperation(new ZooKeeperOperation<Boolean>() {

				@Override
				public Boolean execute() throws Exception {
					if(nodeName == null) {
						return true;
					}
					try {
						zkSupport.getZkClient().delete(nodeName.getNodePath(), -1);
					} catch (NoNodeException e) {
						logger.warn("分布式锁->删除的节点不存在：" + nodeName.getNodePath());
						return true;
					}
					return true;
				}
			}, retryCount);
		} catch (Exception e) {
			logger.error("删除当前节点失败：" + e.toString(), e);
		} finally {
		}
	}

	/***
	 * 等待比我小的节点锁定释放。
	 * @throws InterruptedException
	 * @throws KeeperException
	 */
	private boolean waitLessThanMeNodeRelease(SortedSet<ZkNodeName> allChildrenLockNodes) throws KeeperException, InterruptedException {
		ZooKeeper zkClient = zkSupport.getZkClient();
		SortedSet<ZkNodeName> headNodes = allChildrenLockNodes.headSet(nodeName);
		if(headNodes.size() == 0) {
			return true;
		}
		ZkNodeName[] arr_headNodes = headNodes.toArray(new ZkNodeName[0]);
		for (int i = arr_headNodes.length - 1; i >= 0 ; i--) {
			synchronized (wakeUpWatingLockObj) {
				waitingPath = headNodes.last().getNodePath();
				Stat stat = zkClient.exists(waitingPath, this.new WakeUpWatingOnNodeDeletedWatch());
				if(stat != null) {
					wakeUpWatingLockObj.wait();
					try {
						return waitLessThanMeNodeRelease(getAllChildrenLockNodes());
					} catch (Exception e) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private boolean currentNodeIsMinNode(SortedSet<ZkNodeName> allChildrenLockNodes) {
		if (this.nodeName == null) {
			return false;
		}
		if (allChildrenLockNodes == null || allChildrenLockNodes.size() == 0) {
			return false;
		}
		return allChildrenLockNodes.first().equals(this.nodeName);
	}

	private SortedSet<ZkNodeName> getAllChildrenLockNodes() throws Exception{
		return zkSupport.retryOperation(new ZooKeeperOperation<SortedSet<ZkNodeName>>() {

			@Override
			public SortedSet<ZkNodeName> execute() throws Exception {
				List<String> childPaths = zkSupport.getZkClient().getChildren(path, false);
				SortedSet<ZkNodeName> result = new TreeSet<>();
				if(childPaths.isEmpty()) {
					return result;
				}

				for(String childPath : childPaths) {
					result.add(new ZkNodeName(path + "/" + childPath));
				}
				return result;
			}
		}, retryCount);

	}

	private String getLockPath() {
		return path + "/" + prefix;
	}

	private boolean createLockNode()  throws Exception {
		return zkSupport.retryOperation(new ZooKeeperOperation<Boolean>() {
			@Override
			public Boolean execute() throws Exception {
				zkSupport.ensurePathExists(path);
				String lockNodePath = zkSupport.getZkClient().create(getLockPath(), null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
				nodeName = new ZkNodeName(lockNodePath);
				return true;
			}

		}, retryCount);
	}

	private class WakeUpWatingOnNodeDeletedWatch implements Watcher {

		@Override
		public void process(WatchedEvent event) {
			if (event.getType() == EventType.NodeDeleted) {
				if(event.getPath().equals(waitingPath)) {
					synchronized (wakeUpWatingLockObj) {
						wakeUpWatingLockObj.notifyAll();
					}
				} else {
					logger.warn("等待唤醒的节点不正确:监控节点{0},通知节点{1}", waitingPath, event.getPath());
				}
			}
		}
	}

	public void close() {
		try {
			this.zkSupport.getZkClient().close();
		} catch (InterruptedException e) {
			logger.warn(e.toString(), e);
		}
		isClosed = true;
	}

	public boolean isClosed() {
		return isClosed;
	}
}
