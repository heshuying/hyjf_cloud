package com.hyjf.batch.job.sell.daily;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hyjf.batch.job.BaseJob;

/**
 * @author fuqiang
 * @version DailyGeneratorDataJob, v0.1 2018/11/19 15:43
 */
@DisallowConcurrentExecution
public class DailyGeneratorDataJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("DailyGeneratorDataJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-MARKET/cs-market/daily_generator_data/data", Object.class);
        logger.info("DailyGeneratorDataJob execute end...");
    }
}
