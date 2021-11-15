package com.onnoa.shop.common.utils;

/**
 * 系统信息
 */
abstract class SystemInfo {
	private static final int DEFAULT_PROCESSORS = Runtime.getRuntime().availableProcessors();

	/***
	 * @description: 获取cpu总线程数
	 * @return: int 总线程数
	 * @author: onnoA
	 * @date: 2021/11/12 11:38
	 */
	public static int getProcessCount() {
//		return DEFAULT_PROCESSORS <= 1 ? 1 : DEFAULT_PROCESSORS;
		return Math.max(DEFAULT_PROCESSORS, 1);
	}

	public static void main(String[] args) {
		int DEFAULT_PROCESSORS = Runtime.getRuntime().availableProcessors();
		System.out.println(DEFAULT_PROCESSORS);
	}

}
