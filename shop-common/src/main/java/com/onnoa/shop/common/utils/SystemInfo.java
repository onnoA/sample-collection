package com.onnoa.shop.common.utils;

/**
 * 系统信息
 */
abstract class SystemInfo {
	private static final int DEFAULT_PROCESSORS = Runtime.getRuntime().availableProcessors();

	public static int getProcessCount() {
		return DEFAULT_PROCESSORS <= 1 ? 1 : DEFAULT_PROCESSORS;
	}

}
