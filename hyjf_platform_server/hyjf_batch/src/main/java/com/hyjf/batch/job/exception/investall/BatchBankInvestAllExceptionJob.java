package com.hyjf.batch.job.exception.investall;

import com.hyjf.batch.job.BaseJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 债转投资异常处理定时
 * @author jun
 * @since 20180619	
 */
public class BatchBankInvestAllExceptionJob extends BaseJob implements Job{
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("BatchBankInvestAllExceptionJob: {} execute start...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/investallexception/handle", String.class);
        logger.info("BatchBankInvestAllExceptionJob execute end...");
    }
}

