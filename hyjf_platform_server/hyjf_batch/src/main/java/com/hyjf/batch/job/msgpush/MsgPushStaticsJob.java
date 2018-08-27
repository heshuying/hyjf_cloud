/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.msgpush;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hyjf.batch.job.BaseJob;

/**
 * 消息推送统计定时任务
 * @author fuqiang
 * @version MsgPushStaticsJob, v0.1 2018/6/21 18:14
 */
@DisallowConcurrentExecution
public class MsgPushStaticsJob extends BaseJob implements Job {

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		logger.info("MsgPushStaticsJob: {} execute...", jobExecutionContext.getJobDetail().getKey().getName());
		restTemplate.getForEntity("http://CS-MESSAGE/cs-message/app_message/push_statics", Object.class);
		logger.info("MsgPushStaticsJob execute end...");
	}
}
