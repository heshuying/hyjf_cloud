/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.bifa.indexdata;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author jun
 * @version BifaIndexHistoryDataJob, v0.1 2019/1/21 15:33
 */
@DisallowConcurrentExecution
public class BifaIndexHistoryDataJob extends BaseJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("BifaIndexHistoryDataJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/cs-trade/bifaIndexData/historyDataReport", null);
        logger.info("BifaIndexHistoryDataJob execute end...");
    }
}
