package com.onnoa.shop.common.utils;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 根据名称隔离的多线程执行器。
 */
class NameableThreadPoolExecutor extends ThreadPoolExecutor {

	public NameableThreadPoolExecutor(String name, int size, boolean isDaemon) {
		super(size, size, Long.MAX_VALUE, TimeUnit.MILLISECONDS, new LinkedTransferQueue<Runnable>(),
				new NameableThreadFactory(name, isDaemon));
	}
}
