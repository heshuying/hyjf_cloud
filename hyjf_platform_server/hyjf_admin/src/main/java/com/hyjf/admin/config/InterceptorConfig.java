/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.hyjf.admin.interceptor.AdminInterceptor;

/**
 * @author zhangqingqing
 * @version WebMvcConfig, v0.1 2018/6/6 10:56
 */
@SuppressWarnings("deprecation")
@EnableWebMvc
@Configuration
 public class InterceptorConfig extends WebMvcConfigurerAdapter {
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminInterceptor());
    }
}