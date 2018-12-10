package com.hyjf.batch.job.exception.investall;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 债转出借异常处理定时
 * @author jun
 * @since 20180619	
 */
@DisallowConcurrentExecution
public class BatchBankInvestAllExceptionJob extends BaseJob implements Job{

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("BatchBankInvestAllExceptionJob: {} execute start...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/cs-trade/bankException/investAllExceptionHandle", String.class);
        logger.info("BatchBankInvestAllExceptionJob execute end...");
    }
}

