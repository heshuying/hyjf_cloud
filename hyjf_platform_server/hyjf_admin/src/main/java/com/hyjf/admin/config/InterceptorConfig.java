/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author dongzeshan
 * @version InterceptorConfig, v0.1 2018/6/22 10:56
 */

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
/*
		InterceptorRegistration addIn= registry.addInterceptor(new AdminInterceptor());
		//所有都拦截
		addIn.addPathPatterns("/**");
		//不拦截的请求
		addIn.excludePathPatterns(
				"/hyjf-admin/login/login",
				"/hyjf-admin/login/getPicture"
		).excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
*/
	}
}