package com.hyjf.batch.job.screendata;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lisheng
 * @version ActivityEndJob, v0.1 2019/3/20 15:35
 * 大屏数据金额统计batch
 */
@DisallowConcurrentExecution
public class ScreenDataTransferJob extends BaseJob implements Job {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("ScreenDataJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE//cs-trade/screen/transfer", String.class);
        logger.info("ScreenDataJob execute end...");
    }
}
