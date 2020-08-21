package com.onnoa.distributed.primary.key.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 根据名称隔离的现成构造工厂。
 *
 */
class NameableThreadFactory implements ThreadFactory {
	private final ThreadGroup group;
	private final String namePrefix;
	private final AtomicInteger threadId;
	private final boolean isDaemon;

	/**
	 *
	 * @param name 线程名名称前缀
	 * @param isDaemon 是否守护线程。如果是守护线程，用户进程关闭时，守护线程自动消亡 。
	 */
	public NameableThreadFactory(String name, boolean isDaemon) {
		SecurityManager s = System.getSecurityManager();
		this.group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
		this.namePrefix = name;
		this.threadId = new AtomicInteger(0);
		this.isDaemon = isDaemon;
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r, namePrefix + threadId.getAndIncrement());
		t.setDaemon(isDaemon);
		return t;
	}
}
