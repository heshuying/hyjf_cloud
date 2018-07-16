package com.hyjf.zuul.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @author xiasq
 * @version RefreshRouteService, v0.1 2018/4/13 10:08
 */
@Service
public class RefreshRouteService {
	public final static Logger logger = LoggerFactory.getLogger(RefreshRouteService.class);
	@Autowired
    ApplicationEventPublisher publisher;

	@Autowired
    RouteLocator routeLocator;

	public void refreshRoute() {
		logger.info("refreshRoute...");
		RoutesRefreshedEvent routesRefreshedEvent = new RoutesRefreshedEvent(routeLocator);
		publisher.publishEvent(routesRefreshedEvent);
	}
}
