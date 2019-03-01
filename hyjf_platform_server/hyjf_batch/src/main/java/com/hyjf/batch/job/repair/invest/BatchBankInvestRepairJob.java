package com.hyjf.batch.job.repair.invest;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 债转出借异常处理定时
 * @author jun
 * @since 20180623
 */
@DisallowConcurrentExecution
public class BatchBankInvestRepairJob extends BaseJob implements Job{

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("BatchBankInvestRepairJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/cs-trade/bankException/investExceptionHandle", null);
        logger.info("BatchBankInvestRepairJob execute end...");
    }
}

