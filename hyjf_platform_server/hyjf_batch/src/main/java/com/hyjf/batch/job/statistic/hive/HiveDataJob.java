package com.hyjf.batch.job.statistic.hive;

import com.hyjf.am.response.StringResponse;
import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Auther:yangchangwei
 * @Date:2019/6/20
 * @Description: hive数据统计
 */
@DisallowConcurrentExecution
public class HiveDataJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("HiveDataJob: {} execute...", jobExecutionContext.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://DATA-STATISTICS/jinchuang/hive/updatedata", StringResponse.class);
        logger.info("HiveDataJob execute end...");
    }


}
