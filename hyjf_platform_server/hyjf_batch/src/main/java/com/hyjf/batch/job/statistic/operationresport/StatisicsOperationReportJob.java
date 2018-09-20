/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.statistic.operationresport;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hyjf.batch.job.BaseJob;

/**
 * 运营数据定时任务
 * @author yinhui
 * @version StatisicsOperationReportJob, v0.1 2018/7/18 9:40
 */
@DisallowConcurrentExecution
public class StatisicsOperationReportJob extends BaseJob implements Job {
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		logger.info("StatisicsOperationReportJob: {} execute...",
				jobExecutionContext.getJobDetail().getKey().getName());
//		restTemplate.getForEntity("http://CS-MARKET/cs-market/statisticsoperationreport/insertoperationgroupdata",
//				Object.class);
		logger.info("StatisicsOperationReportJob execute end...");
	}
}
