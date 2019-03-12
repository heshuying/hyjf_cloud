package com.hyjf.batch.job.repair.banktendercancel;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 出借撤销异常处理定时任务
 * @author jijun
 * @since 20180625
 */
@DisallowConcurrentExecution
public class BankTenderCancelRepairJob extends BaseJob implements Job{
	
	Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("BankTenderCancelRepairJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/cs-trade/bankException/bankTenderCancelExceptionHandle", null);
        logger.info("BankTenderCancelRepairJob execute end...");
    }
}

