/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.hjh.hjhplanswitch;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 定时关闭计划任务
 * @author liubin
 * @version HjhPlanJoinOffJob, v0.1 2018/8/14 16:36
 */
@DisallowConcurrentExecution  // 禁止并发执行
public class HjhPlanJoinOffJob extends BaseJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("HjhPlanJoinOffJob: {} execute...", context.getJobDetail().getKey().getName());

        Boolean result = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/hjhPlanSwitchController/batch/hjhPlanJoinOff", Boolean.class).getBody();

        logger.info("HjhPlanJoinOffJob execute end...");
    }
}