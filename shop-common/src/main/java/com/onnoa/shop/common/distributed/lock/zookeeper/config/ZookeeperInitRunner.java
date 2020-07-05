package com.onnoa.shop.common.distributed.lock.zookeeper.config;

import com.onnoa.shop.common.distributed.lock.zookeeper.registry.RegistryCenterFactory;
import com.onnoa.shop.common.properties.base.ShopProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetAddress;

/**
 * @Author gfxiang
 * @date 2019/8/9 16:34
 * @description
 */
//@Component
@Order
@Slf4j
public class ZookeeperInitRunner implements CommandLineRunner {

    @Resource
	private ShopProperties shopProperties;
	@Value("${spring.application.name}")
	private String applicationName;

	@Override
	public void run(String... args) throws Exception {
		String hostAddress = InetAddress.getLocalHost().getHostAddress();
		log.info("###ZookeeperInitRunner，init. HostAddress={}, applicationName={}", hostAddress, applicationName);
		RegistryCenterFactory.startup(shopProperties, hostAddress, applicationName);
		log.info("###ZookeeperInitRunner，finish<<<<<<<<<<<<<");
	}

}
