/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.message;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 生日祝福发短信定时任务
 */
@DisallowConcurrentExecution
public class BirthdayBlessMessageJob extends BaseJob implements Job {
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		logger.info("BirthdayBlessMessageJob: {} execute...", jobExecutionContext.getJobDetail().getKey().getName());
		restTemplate.getForEntity("http://CS-MESSAGE/cs-message/birthdayBless/send", Object.class);
		logger.info("BirthdayBlessMessageJob execute end...");
	}
}
