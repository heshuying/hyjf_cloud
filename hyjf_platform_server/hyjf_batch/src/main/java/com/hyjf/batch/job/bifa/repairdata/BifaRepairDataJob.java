/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.bifa.repairdata;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/** 禁止并发执行 */
@DisallowConcurrentExecution
public class BifaRepairDataJob extends BaseJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("BifaRepairDataJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/cs-trade/bifaExceptionData/reportAgain", null);
        logger.info("BifaRepairDataJob execute end...");
    }
}
