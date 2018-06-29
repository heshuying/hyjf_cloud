package com.hyjf.batch.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xiasq
 * @version DemoJob, v0.1 2018/6/14 16:33
 */
@DisallowConcurrentExecution  // 禁止并发执行
public class DemoJob extends BaseJob implements Job {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("demoJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-MARKET/batch/transactiondemo", String.class);
        logger.info("demoJob execute end...");
    }
}
