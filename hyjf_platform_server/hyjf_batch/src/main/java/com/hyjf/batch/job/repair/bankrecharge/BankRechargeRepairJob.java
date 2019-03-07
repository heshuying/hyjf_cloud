package com.hyjf.batch.job.repair.bankrecharge;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 充值掉单定时任务 create by jijun 20180612
 */
@DisallowConcurrentExecution
public class BankRechargeRepairJob extends BaseJob implements Job {

    /**
     * 执行定时任务
     * @param context
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("BankRechargeRepairJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/cs-trade/bankException/recharge", null);
        logger.info("BankRechargeRepairJob execute end...");
    }
}