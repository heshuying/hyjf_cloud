package com.hyjf.batch.job.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.hyjf.batch.SpringLocator;

/**
 * @author xiasq
 * @version BaseJob, v0.1 2018/1/20 22:22
 */
public abstract class BaseService {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 不能直接注入，对象改变了。
	 */
	protected RestTemplate restTemplate = SpringLocator.getBean(RestTemplate.class);
}
