/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.electricitysalesdata;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 电销数据推送记录生成Job
 *
 * @author liuyang
 * @version ElectricitySalesDataJob, v0.1 2019/5/28 15:17
 */
@DisallowConcurrentExecution
public class ElectricitySalesDataJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("ElectricitySalesDataJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-USER/cs-user/batch/electricitysalesdata/generateElectricitySalesData", String.class);
        logger.info("ElectricitySalesDataJob execute end...");
    }

}
