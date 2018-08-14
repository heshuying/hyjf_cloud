/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.hjh.hjhcheck;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 汇计划各计划开放额度校验预警
 * @author liubin
 * @version HjhOpenAccountCheckJob, v0.1 2018/8/14 14:36
 */
@DisallowConcurrentExecution  // 禁止并发执行
public class HjhOpenAccountCheckJob extends BaseJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("HjhOpenAccountCheckJob: {} execute...", context.getJobDetail().getKey().getName());

        Boolean result = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/hjhAlarmController/batch/hjhOpenAccountCheck", Boolean.class).getBody();

        logger.info("HjhOpenAccountCheckJob execute end...");
    }
}
