package com.hyjf.batch.job.exception.bankrecharge;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 充值掉单定时任务 create by jijun 20180612
 */
@DisallowConcurrentExecution
public class BankRechargeExceptionJob extends BaseJob implements Job {

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 执行定时任务
     * @param context
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("BankRechargeExceptionJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://AM-TRADE/am-trade/bankException/recharge", String.class);
        logger.info("BankRechargeExceptionJob execute end...");
    }
}