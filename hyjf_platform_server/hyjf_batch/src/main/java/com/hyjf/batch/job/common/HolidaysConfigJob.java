package com.hyjf.batch.job.common;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author xiasq
 * @version HolidaysConfigJob, v0.1 2018/7/18 16:08
 */
@DisallowConcurrentExecution
public class HolidaysConfigJob extends BaseJob implements Job {
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		logger.info("HolidaysConfigJob: {} execute...", jobExecutionContext.getJobDetail().getKey().getName());
		restTemplate.getForEntity("http://AM-CONFIG/am-config/holidays/save", String.class);
		logger.info("HolidaysConfigJob execute end...");
	}
}