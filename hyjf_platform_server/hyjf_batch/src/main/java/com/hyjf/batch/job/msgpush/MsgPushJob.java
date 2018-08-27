/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.msgpush;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hyjf.batch.job.BaseJob;

/**
 * 消息推送定时任务
 * @author fuqiang
 * @version MsgPushJob, v0.1 2018/6/21 14:30
 */
@DisallowConcurrentExecution
public class MsgPushJob extends BaseJob implements Job {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		logger.info("MsgPushJob: {} execute...", jobExecutionContext.getJobDetail().getKey().getName());
		restTemplate.getForEntity("http://CS-MESSAGE/cs-message/app_message/push_all", Object.class);
		logger.info("MsgPushJob execute end...");
	}
}
