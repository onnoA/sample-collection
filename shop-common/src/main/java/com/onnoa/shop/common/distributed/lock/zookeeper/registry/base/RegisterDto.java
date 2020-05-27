package com.onnoa.shop.common.distributed.lock.zookeeper.registry.base;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/6/6 12:35
 */
@Data
@AllArgsConstructor
public class RegisterDto {

	private String app;

	private String host;

	private CoordinatorRegistryCenter coordinatorRegistryCenter;

}
