package com.hyjf.batch.job.exception.invest;

import com.hyjf.batch.job.BaseJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 债转投资异常处理定时
 * @author jun
 * @since 20180623
 */
public class BatchBankInvestExceptionJob extends BaseJob implements Job{
	
	Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("BatchBankInvestExceptionJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/cs-trade/bankException/investExceptionHandle", String.class);
        logger.info("BatchBankInvestExceptionJob execute end...");
    }
}

