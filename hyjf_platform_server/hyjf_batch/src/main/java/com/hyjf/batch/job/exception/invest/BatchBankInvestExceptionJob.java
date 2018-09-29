package com.hyjf.batch.job.exception.invest;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 债转投资异常处理定时
 * @author jun
 * @since 20180623
 */
@DisallowConcurrentExecution
public class BatchBankInvestExceptionJob extends BaseJob implements Job{

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("BatchBankInvestExceptionJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/cs-trade/bankException/investExceptionHandle", String.class).getBody();
        logger.info("BatchBankInvestExceptionJob execute end...");
    }
}

