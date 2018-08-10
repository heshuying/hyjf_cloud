/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.borrow.hjhquit;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowRepayToHjhQuitJob, v0.1 2018/6/25 9:31
 */
/** 禁止并发执行 */
@DisallowConcurrentExecution
public class BorrowRepayToHjhQuitJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("BorrowRepayToHjhQuitJob: {} execute...", context.getJobDetail().getKey().getName());

        restTemplate.getForEntity("http://CS-TRADE/borrowRepay/hjhQuit", String.class);

        logger.info("BorrowRepayToHjhQuitJob execute end...");
    }
}
