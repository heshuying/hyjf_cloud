package com.hyjf.zuul.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author xiasq
 * @version RefreshRouteScheduler, v0.1 2018/7/13 15:41
 */
@Component
@EnableScheduling
public class RefreshRouteScheduler {
    public final static Logger logger = LoggerFactory.getLogger(RefreshRouteScheduler.class);
	@Autowired
	private RefreshRouteService service;
	@Autowired
	private Flag flag;

	/**
	 * 每5分钟执行一次
	 */
	@Scheduled(fixedRate = 5 * 60 * 1000)
	public void checkTransactionMessage() {
	    logger.info("自动刷新路由配置....");
		flag.setFlag(false);
		service.refreshRoute();
	}

}