package com.hyjf.batch;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author xiasq
 * @version SpringLocator, v0.1 2018/1/20 22:09
 */

@Component
public class SpringLocator implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringLocator.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

    /**
     * 得到bean实例 - 根据类名
     * @param name
     * @param <T>
     * @return
     */
	public static <T> T getBean(String name) {
		return (T) applicationContext.getBean(name);
	}

    /**
     * 得到bean实例 - 根据类类型
     * @param
     * @param <T>
     * @return
     */
	public static <T> T getBean(Class<T> clazz) {
		return BeanFactoryUtils.beanOfType(applicationContext, clazz);
	}

}
