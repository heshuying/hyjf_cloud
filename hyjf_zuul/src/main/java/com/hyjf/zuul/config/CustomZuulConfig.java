package com.hyjf.zuul.config;

import com.hyjf.zuul.client.AmConfigClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiasq
 * @version CustomZuulConfig, v0.1 2018/4/13 10:07
 */
@Configuration
public class CustomZuulConfig {
	@Autowired
    ZuulProperties zuulProperties;
	@Autowired
    ServerProperties server;
	@Autowired
	private AmConfigClient amConfigClient;
	@Autowired
	private Flag flag;

	@Bean
	public CustomRouteLocator routeLocator() {
		CustomRouteLocator routeLocator = new CustomRouteLocator(this.server.getServlet().getServletPrefix(), this.zuulProperties);
		routeLocator.setAmConfigClient(amConfigClient);
		routeLocator.setFlag(flag);
		return routeLocator;
	}
}
