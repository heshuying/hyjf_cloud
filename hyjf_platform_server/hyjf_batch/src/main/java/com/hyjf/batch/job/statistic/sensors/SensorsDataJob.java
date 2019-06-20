package com.hyjf.batch.job.statistic.sensors;

import com.hyjf.am.response.StringResponse;
import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Auther:yangchangwei
 * @Date:2019/6/20
 * @Description: 神策数据统计
 */
@DisallowConcurrentExecution
public class SensorsDataJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("SensorsDataJob: {} execute...", jobExecutionContext.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://DATA-STATISTICS/jinchuang/sensors/updatedata", StringResponse.class);
        logger.info("SensorsDataJob execute end...");
    }


}
