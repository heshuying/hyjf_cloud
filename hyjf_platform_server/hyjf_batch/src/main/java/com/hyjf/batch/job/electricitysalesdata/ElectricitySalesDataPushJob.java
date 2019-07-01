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
 * 电销数据推送
 *
 * @author liuyang
 * @version ElectricitySalesDataPushJob, v0.1 2019/6/3 13:48
 */
@DisallowConcurrentExecution
public class ElectricitySalesDataPushJob extends BaseJob implements Job {


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("ElectricitySalesDataPushJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-USER/cs-user/batch/electricitysalesdatapush/electricitySalesDataPush", String.class);
        logger.info("ElectricitySalesDataPushJob execute end...");
    }
}
