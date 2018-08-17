package com.hyjf.batch.job.exception.bankwithdraw;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 江西银行提现掉单异常处理定时任务
 * @author jijun
 * @date 2018年6月14日
 */
@DisallowConcurrentExecution
public class BankWithdrawExceptionJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info(".................BankWithdrawExceptionJob: {} execute.................", context.getJobDetail().getKey().getName());
        boolean ret=restTemplate.getForEntity("http://CS-TRADE/cs-trade/bankException/bankWithdrawExceptionHandle", Boolean.class).getBody();
        if (!ret){
            logger.info(".................江西银行提现掉单异常处理定时任务运行异常.................");
        }
        logger.info(".................BankWithdrawExceptionJob execute end.................");

    }
}
