package com.hyjf.batch.job.repair.bankwithdraw;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 江西银行提现掉单异常处理定时任务
 * @author jijun
 * @date 2018年6月14日
 */
@DisallowConcurrentExecution
public class BankWithdrawRepairJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info(".................BankWithdrawRepairJob: {} execute.................", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/cs-trade/bankException/bankWithdrawExceptionHandle", null);
        logger.info(".................BankWithdrawRepairJob execute end.................");
    }
}
