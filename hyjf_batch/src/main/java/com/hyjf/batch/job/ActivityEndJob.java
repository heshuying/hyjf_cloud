package com.hyjf.batch.job;

import com.hyjf.batch.job.service.ActivityEndService;
import com.hyjf.batch.job.service.BaseService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xiasq
 * @version ActivityEndJob, v0.1 2018/4/19 15:35
 * 进行中的活动列表状态变更定时
 */
public class ActivityEndJob implements Job {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ActivityEndService activityEndService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("ActivityEndJob: {} execute...", context.getJobDetail().getKey().getName());
        activityEndService.execute();
        logger.info("ActivityEndJob execute end...");

    }
}
