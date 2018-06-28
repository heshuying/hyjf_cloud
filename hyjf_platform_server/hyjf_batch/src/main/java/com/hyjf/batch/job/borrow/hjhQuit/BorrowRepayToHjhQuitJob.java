/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.borrow.hjhQuit;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowRepayToHjhQuitJob, v0.1 2018/6/25 9:31
 */
@DisallowConcurrentExecution  // 禁止并发执行
public class BorrowRepayToHjhQuitJob extends BaseJob implements Job {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("BorrowRepayToHjhQuitJob: {} execute...", context.getJobDetail().getKey().getName());

        restTemplate.getForEntity("http://CS-TRADE/batch/borrowRepay/hjhQuit", String.class);

        logger.info("BorrowRepayToHjhQuitJob execute end...");
    }
}
