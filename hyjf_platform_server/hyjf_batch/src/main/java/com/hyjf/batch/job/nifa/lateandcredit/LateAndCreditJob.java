/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.nifa.lateandcredit;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author PC-LIUSHOUYI
 * @version LateAndCreditJob, v0.1 2018/9/5 11:42
 * 获取逾期标和完全债转标
 */
/** 禁止并发执行 */
@DisallowConcurrentExecution
public class LateAndCreditJob extends BaseJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("LateAndCreditJob: {} execute...", context.getJobDetail().getKey().getName());

        restTemplate.getForEntity("http://AM-TRADE/am-trade/late_and_credit/update_repay_info", boolean.class);

        logger.info("LateAndCreditJob execute end...");
    }
}
