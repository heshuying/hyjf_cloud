/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.hjh.autocalculatefairvalue;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 汇计划自动计算计划订单公允价值Job
 *
 * @author liuyang
 * @version HjhAutoCalculateFairValueJob, v0.1 2018/8/6 14:46
 */
@DisallowConcurrentExecution
public class HjhAutoCalculateFairValueJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("HjhAutoCalculateFairValueJob: {} execute...", jobExecutionContext.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://AM-TRADE/am-trade/hjhAutoCalculateFairValue/hjhCalculateFairValue", String.class);
        logger.info("HjhAutoCalculateFairValueJob execute end...");
    }
}
