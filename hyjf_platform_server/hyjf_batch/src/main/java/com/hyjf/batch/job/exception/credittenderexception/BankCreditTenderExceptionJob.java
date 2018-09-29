package com.hyjf.batch.job.exception.credittenderexception;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 * 债转投资异常处理定时任务
 * @author jun
 * @since 20180619	
 */
@DisallowConcurrentExecution
public class BankCreditTenderExceptionJob extends BaseJob implements Job{
	
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("BankCreditTenderExceptionJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/cs-trade/bankException/creditTenderExceptionHandle", String.class).getBody();
        logger.info("BankCreditTenderExceptionJob execute end...");
    }

}

