package com.hyjf.batch.job.activity;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xiasq
 * @version ActivityEndJob, v0.1 2018/4/19 15:35
 * 进行中的活动列表状态变更定时
 */
@DisallowConcurrentExecution
public class ActivityEndJob extends BaseJob implements Job {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("ActivityEndJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://AM-MARKET/activity/batch/update", String.class);
        logger.info("ActivityEndJob execute end...");
    }
}
