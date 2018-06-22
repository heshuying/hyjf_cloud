/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.msgpush;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * @author fuqiang
 * @version MsgPushStaticsJob, v0.1 2018/6/21 18:14
 */
public class MsgPushStaticsJob implements Job {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		logger.info("MsgPushStaticsJob: {} execute...", jobExecutionContext.getJobDetail().getKey().getName());
		restTemplate.getForEntity("http://CS-MESSAGE/cs-message/message/msgPushStatics", Object.class);
		logger.info("MsgPushStaticsJob execute end...");
	}
}
