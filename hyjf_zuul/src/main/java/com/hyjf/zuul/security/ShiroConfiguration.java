/*
 * @Copyright: 2005-2017 www.2345.com. All rights reserved.
 */
package com.hyjf.zuul.security;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * @author xiasq
 * @version ShiroConfiguration, v0.1 2018/4/13 9:54
 */
@Configuration
public class ShiroConfiguration {

	private static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

	@Bean(name = "cacheShiroManager")
	public CacheManager getCacheManage() {
		return new EhCacheManager();
	}

	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean(name = "shiroRealm")
	public AuthorizingRealm getShiroRealm() {
		AuthorizingRealm realm = new ShiroRealm(getCacheManage());
		realm.setName("shiro_auth_cache");
		realm.setAuthenticationCache(getCacheManage().getCache(realm.getName()));
		realm.setAuthenticationTokenClass(UsernamePasswordToken.class);
		return realm;
	}

	@Bean(name = "securityManager")
	public DefaultWebSecurityManager getSecurityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setCacheManager(getCacheManage());
		securityManager.setRealm(getShiroRealm());
		return securityManager;
	}

	@Bean
	public MethodInvokingFactoryBean getMethodInvokingFactoryBean() {
		MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
		factoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
		factoryBean.setArguments(new Object[] { getSecurityManager() });
		return factoryBean;
	}

	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator getAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
		creator.setProxyTargetClass(true);
		return creator;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(getSecurityManager());
		return advisor;
	}

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
		ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
		factoryBean.setSecurityManager(getSecurityManager());
		filterChainDefinitionMap.put("/api/**", "authcBasic");
		factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return factoryBean;
	}
}
