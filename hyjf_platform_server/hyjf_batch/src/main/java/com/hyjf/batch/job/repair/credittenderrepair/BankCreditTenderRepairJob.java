package com.hyjf.batch.job.repair.credittenderrepair;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 * 债转出借异常处理定时任务
 * @author jun
 * @since 20180619	
 */
@DisallowConcurrentExecution
public class BankCreditTenderRepairJob extends BaseJob implements Job{
	
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("BankCreditTenderRepairJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/cs-trade/bankException/creditTenderExceptionHandle", null);
        logger.info("BankCreditTenderRepairJob execute end...");
    }

}

