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

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("BankWithdrawExceptionJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/cs-trade/bankWithdrawException/withdraw", String.class);
        logger.info("BankWithdrawExceptionJob execute end...");
    }
}
