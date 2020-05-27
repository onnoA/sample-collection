package com.onnoa.shop.common.distributed.lock.zookeeper.generator;


/**
 * @Description: id生成接口
 * @Author: onnoA
 * @Date: 2020/6/6 12:35
 */
public interface IdGenerator {

	/**
	 * 生成下一个ID
	 *
	 * @return the string
	 */
	Long nextId();
}
