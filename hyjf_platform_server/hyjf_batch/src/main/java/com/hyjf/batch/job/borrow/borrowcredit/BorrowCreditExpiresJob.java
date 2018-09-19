/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.borrow.borrowcredit;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowCreditJob, v0.1 2018/6/23 17:00
 */
@DisallowConcurrentExecution  // 禁止并发执行
public class BorrowCreditExpiresJob  extends BaseJob implements Job {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("BorrowCreditExpiresJob: {} execute...", context.getJobDetail().getKey().getName());

        restTemplate.getForEntity("http://CS-TRADE/batch/borrowCredit/expires", String.class);

        logger.info("BorrowCreditExpiresJob execute end...");
    }
}
