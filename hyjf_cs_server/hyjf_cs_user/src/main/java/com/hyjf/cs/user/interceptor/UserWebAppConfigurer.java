/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author zhangqingqing
 * @version UserWebAppConfigurer, v0.1 2018/5/31 16:40
 */
@Configuration
public class UserWebAppConfigurer extends WebMvcConfigurationSupport {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //这里可以添加多个拦截器
      //  registry.addInterceptor(new BankOpenAccountInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
