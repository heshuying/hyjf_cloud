package com.hyjf.batch.job.borrow.autoreview;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/13 09:20
 * @Description: BatchAutoReviewBorrowTask
 */
@DisallowConcurrentExecution
public class BatchAutoReviewBorrowTask extends BaseJob implements Job {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("BatchAutoReviewBorrowTask: {} execute...", context.getJobDetail().getKey().getName());

        restTemplate.getForEntity("http://CS-TRADE/cs-trade/batch/autoReviewBorrow/taskReviewBorrowAssign", String.class);

        logger.info("BatchAutoReviewBorrowTask execute end...");
    }
}
