package com.onnoa.distributed.primary.key.util;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 多线程执行器。
 *
 */
public class ThreadExecutor {

	private static ThreadExecutor instance = new ThreadExecutor();

	// 默认的现成执行池
	private ThreadPoolExecutor defaultThreadExecutor = null;

	private ThreadExecutor() {
		int size = SystemInfo.getProcessCount() <= 1 ? 4 : SystemInfo.getProcessCount() * 2;
		defaultThreadExecutor = createThreadPoolExecutor("_common", size, true);
	}

	/**
	 * 创建多线程执行池子。请自行管理创建的线程池对象。当前类不管理用户创建的线程池对象。
	 *
	 * @param name 池子名称
	 * @param size 池子大小
	 * @param isDaemon 是否守护线程。如果是守护线程，用户进程关闭时，守护线程自动消亡 。
	 * @return
	 */
	public static ThreadPoolExecutor createThreadPoolExecutor(String name, int size, boolean isDaemon) {
		return new NameableThreadPoolExecutor(name, size, isDaemon);
	}

	/**
	 * <pre>
	 * 在线程池中执行任务。不获得返回结果。
	 * 使用提示：
	 * 如果存在以下两种任务，请调用 createThreadPoolExecutor 创建单独线程池执行。
	 * 1、执行的任务中存在长时间的任务。如：线程中存在sleep，或者超长时间等待远程调用返回结果的请求。（超长时间处理是可以使用线程池的）
	 * 2、永久占用线程的任务。如：监控线程。
	 * </pre>
	 *
	 * @param runnable
	 */
	public static void execute(Runnable runnable) {
		ThreadExecutor.getInstance().defaultThreadExecutor.execute(runnable);
	}


	private static ThreadExecutor getInstance() {
		return instance;
	}
}
