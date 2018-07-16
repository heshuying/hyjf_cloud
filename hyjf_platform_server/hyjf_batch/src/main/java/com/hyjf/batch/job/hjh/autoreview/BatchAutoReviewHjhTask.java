package com.hyjf.batch.job.hjh.autoreview;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/13 11:48
 * @Description: BatchAutoReviewHjhTask
 */
@DisallowConcurrentExecution
public class BatchAutoReviewHjhTask extends BaseJob implements Job {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("BatchAutoReviewHjhTask: {} execute...", context.getJobDetail().getKey().getName());

        restTemplate.getForEntity("http://AM-TRADE/batch/hjhautoreview/hjhautoreview", String.class);

        logger.info("BatchAutoReviewHjhTask execute end...");
    }
}
