/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.hjh.autocredit;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 汇计划自动清算Job
 *
 * @author liuyang
 * @version HjhAutoCreditJob, v0.1 2018/6/28 14:24
 */
@DisallowConcurrentExecution
public class HjhAutoCreditJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("HjhAutoCreditJob: {} execute...", jobExecutionContext.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://AM-TRADE/am-trade/hjhAutoCredit/hjhAutoCredit", String.class);
        logger.info("HjhAutoCreditJob execute end...");
    }
}
