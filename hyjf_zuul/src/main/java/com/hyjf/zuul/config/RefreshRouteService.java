package com.hyjf.zuul.config;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

	//@PostConstruct
	public void openTimeTaskThread() {
		ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
		// 延时 100 秒后，按 300 秒的周期执行任务
		timer.scheduleAtFixedRate(() -> refreshRoute(), 100 * 1000, 300 * 1000, TimeUnit.MILLISECONDS);
	}

	public void refreshRoute() {
		logger.info("refreshRoute...");
		RoutesRefreshedEvent routesRefreshedEvent = new RoutesRefreshedEvent(routeLocator);
		publisher.publishEvent(routesRefreshedEvent);
	}
}
