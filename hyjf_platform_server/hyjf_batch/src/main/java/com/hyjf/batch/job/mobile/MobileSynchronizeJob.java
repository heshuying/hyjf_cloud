package com.hyjf.batch.job.mobile;

import com.hyjf.batch.job.BaseJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lisheng
 * @version MobileSynchronizeJob, v0.1 2018/8/30 10:43
 * 同步手机号每日定时任务
 */

public class MobileSynchronizeJob extends BaseJob implements Job {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("MobileSynchronizeJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-USER/cs-user/batch/mobileSynchronize", String.class);
        logger.info("MobileSynchronizeJob execute end...");
    }
}
