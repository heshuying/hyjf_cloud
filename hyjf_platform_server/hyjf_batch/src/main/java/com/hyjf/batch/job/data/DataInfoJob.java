/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.data;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yaoyong
 * @version DataInfoJob, v0.1 2018/9/20 9:27
 * 平台数据统计定时任务
 */
@DisallowConcurrentExecution
public class DataInfoJob extends BaseJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(DataInfoJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("DataInfoJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://AM-TRADE/am-trade/batch/calculate_invest_interest", String.class);
        logger.info("DataInfoJob execute end...");
    }
}
