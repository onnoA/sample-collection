package com.onnoa.shop.common.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 功能描述: springContext 工具类
 * @date 2020/8/8 15:09
 */
@Component
@Slf4j
public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	/**
	 * Sets application context.
	 *
	 * @param applicationContext the application context
	 *
	 * @throws BeansException the beans exception
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		if (SpringContextHolder.applicationContext == null) {
			synchronized (SpringContextHolder.class) {
				if (SpringContextHolder.applicationContext == null) {
					SpringContextHolder.applicationContext = applicationContext;
				}
			}
		}
	}

	/**
	 * Gets application context.
	 *
	 * @return the application context
	 */
	public static ApplicationContext getApplicationContext() {
		assertApplicationContext();
		return applicationContext;
	}

	public static Object getBean(String beanId) {
		if (beanId == null || beanId.length() == 0) {
			return null;
		}
		try {
			Object bean = applicationContext.getBean(beanId);
			if (bean == null) {
				log.warn("获取不到bean:{}", beanId);
			}
			return bean;
		} catch (Exception e) {
			log.warn("获取bean异常:{}", beanId);
			return null;
		}
	}

	/**
	 * 获取Bean ，获取不到返回null
	 *
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(Class<T> clazz) {
		if (clazz == null) {
			return null;
		}
		try {
			return applicationContext.getBean(clazz);
		} catch (Exception e) {
			log.warn("获取不到bean:{}", clazz);
			return null;
		}

	}

	/**
	 * 获取Bean ，获取不到返回null
	 *
	 * @param name
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(String name, Class<T> clazz) {
		if (clazz == null) {
			return null;
		}
		try {
			return applicationContext.getBean(name, clazz);
		} catch (Exception e) {
			log.warn("获取不到bean:{}，{}", name, clazz);
			return null;
		}
	}

	/// 获取当前环境
	public static String getActiveProfile() {
		return applicationContext.getEnvironment().getActiveProfiles()[0];
	}

	public static DefaultListableBeanFactory getDefaultListableBeanFactory() {
		assertApplicationContext();
		return (DefaultListableBeanFactory) ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
	}

	private static void assertApplicationContext() {
		if (SpringContextHolder.applicationContext == null) {
			throw new IllegalArgumentException("applicationContext属性为null,请检查是否注入了SpringContextHolder!");
		}
	}

}
