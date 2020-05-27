package com.onnoa.shop.common.distributed.lock.zookeeper.registry;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.onnoa.shop.common.distributed.lock.zookeeper.generator.IncrementIdGenerator;
import com.onnoa.shop.common.distributed.lock.zookeeper.properties.ZookeeperProperties;
import com.onnoa.shop.common.distributed.lock.zookeeper.registry.base.CoordinatorRegistryCenter;
import com.onnoa.shop.common.distributed.lock.zookeeper.registry.base.RegisterDto;
import com.onnoa.shop.common.distributed.lock.zookeeper.registry.zookeeper.ZookeeperRegistryCenter;
import com.onnoa.shop.common.properties.base.ShopProperties;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author gfxiang
 * @date 2019/8/9 16:34
 * @description	注册中心工厂.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public final class RegistryCenterFactory {

	private static final ConcurrentHashMap<HashCode, CoordinatorRegistryCenter> REG_CENTER_REGISTRY = new ConcurrentHashMap<>();

	/**
	 * 创建注册中心.
	 *
	 * @param zookeeperProperties the zookeeper properties
	 *
	 * @return 注册中心对象 coordinator registry center
	 */
	public static CoordinatorRegistryCenter createCoordinatorRegistryCenter(ZookeeperProperties zookeeperProperties) {
		Hasher hasher = Hashing.md5().newHasher().putString(zookeeperProperties.getZkAddressList(), Charsets.UTF_8);
		HashCode hashCode = hasher.hash();
		CoordinatorRegistryCenter result = REG_CENTER_REGISTRY.get(hashCode);
		if (null != result) {
			return result;
		}
		result = new ZookeeperRegistryCenter(zookeeperProperties);
		result.init();
		REG_CENTER_REGISTRY.put(hashCode, result);
		return result;
	}

	/**
	 * Startup.
	 *
	 * @param shopProperties 自定义项目配置类
	 * @param host                the host
	 * @param app                 the app
	 */
	public static void startup(ShopProperties shopProperties, String host, String app) {
		CoordinatorRegistryCenter coordinatorRegistryCenter = createCoordinatorRegistryCenter(shopProperties.getZk());
		RegisterDto dto = new RegisterDto(app, host, coordinatorRegistryCenter);
		Long serviceId = new IncrementIdGenerator(dto).nextId();
		IncrementIdGenerator.setServiceId(serviceId);
		log.info("zk启动注册服务：app:{},host:{},serviceId:{}",app,host,serviceId);
	}

}
