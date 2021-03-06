/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.pc.statistic;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * PC渠道统计数据定时
 * @author fuqiang
 * @version PcChannelStatisticsJob, v0.1 2018/7/16 10:12
 */
@DisallowConcurrentExecution
public class PcChannelStatisticsJob extends BaseJob implements Job {
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		logger.info("PcChannelStatisticsJob: {} execute...", jobExecutionContext.getJobDetail().getKey().getName());
		restTemplate
				.getForObject("http://CS-MARKET/cs-market/pcChannelStatistics/insertStatistics", String.class);
		logger.info("PcChannelStatisticsJob execute end...result is {}");
	}
}
