package com.hyjf.batch.job.exception.banktendercancel;

import com.hyjf.batch.job.BaseJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 投资撤销异常处理定时任务
 * @author jijun
 * @since 20180625
 */
public class BankTenderCancelExceptionJob extends BaseJob implements Job{
	
	Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("BankTenderCancelExceptionJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/cs-trade/bankException/bankTenderCancelExceptionHandle", String.class);
        logger.info("BankTenderCancelExceptionJob execute end...");
    }
}

