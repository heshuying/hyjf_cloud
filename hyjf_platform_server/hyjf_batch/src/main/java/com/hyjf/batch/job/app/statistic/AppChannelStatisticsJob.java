/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.app.statistic;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * app推广统计定时任务
 * 
 * @author fuqiang
 * @version AppChannelStatisticsJob, v0.1 2018/7/16 14:14
 */
@DisallowConcurrentExecution
public class AppChannelStatisticsJob extends BaseJob implements Job {
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		logger.info("AppChannelStatisticsJob: {} execute...", jobExecutionContext.getJobDetail().getKey().getName());
		restTemplate.getForEntity("http://CS-MESSAGE/cs-message/appchannelstatistics/insertStatistics", String.class);
		logger.info("AppChannelStatisticsJob execute end...");
	}
}
