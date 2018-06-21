package com.hyjf.batch.job.exception.credittenderexception;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hyjf.batch.job.BaseJob;
/**
 * 债转投资异常处理定时
 * @author jun
 * @since 20180619	
 */
public class BankCreditTenderExceptionJob extends BaseJob implements Job{
	
	Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("BankCreditTenderExceptionJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/credittenderexception/handle", String.class);
        logger.info("BankCreditTenderExceptionJob execute end...");
    }
}

