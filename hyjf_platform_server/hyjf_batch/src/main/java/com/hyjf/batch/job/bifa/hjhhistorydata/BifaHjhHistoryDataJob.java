/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.bifa.hjhhistorydata;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author jun
 * @version BifaHjhHistoryDataJob, v0.1 2019/1/18 17:04
 */
@DisallowConcurrentExecution
public class BifaHjhHistoryDataJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("BifaHjhHistoryDataJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/cs-trade/bifaHjhHistoryData/report", null);
        logger.info("BifaHjhHistoryDataJob execute end...");
    }
}
