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
 * 定时发短信定时任务
 * @author fuqiang
 * @version OntimeMessageJob, v0.1 2018/6/22 10:27
 */
@DisallowConcurrentExecution
public class OntimeMessageJob extends BaseJob implements Job {
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		logger.info("OntimeMessageJob: {} execute...", jobExecutionContext.getJobDetail().getKey().getName());
		restTemplate.getForEntity("http://CS-MESSAGE/cs-message/smsOntime/send", Object.class);
		logger.info("OntimeMessageJob execute end...");
	}
}
