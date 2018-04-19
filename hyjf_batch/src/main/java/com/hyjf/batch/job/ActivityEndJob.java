package com.hyjf.batch.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author xiasq
 * @version ActivityEndJob, v0.1 2018/4/19 15:35
 * 进行中的活动列表状态变更定时
 */
public class ActivityEndJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("ActivityEndJob: {} execute...", context.getJobDetail().getKey().getName());
		String url = "http://AM-MARKET/activity/batch/update";
		restTemplate.getForEntity(url, String.class);
    }
}
