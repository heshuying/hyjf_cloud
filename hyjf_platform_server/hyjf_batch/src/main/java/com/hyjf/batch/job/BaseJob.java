package com.hyjf.batch.job;

import com.hyjf.batch.SpringLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author xiasq
 * @version BaseJob, v0.1 2018/1/20 22:22
 */
public abstract class BaseJob {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 不能直接注入，对象改变了。
	 */
	protected RestTemplate restTemplate = SpringLocator.getBean(RestTemplate.class);
}
