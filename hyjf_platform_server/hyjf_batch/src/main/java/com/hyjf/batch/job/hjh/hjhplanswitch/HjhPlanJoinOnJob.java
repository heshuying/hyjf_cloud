/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.hjh.hjhplanswitch;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 定时开启计划任务
 * @author liubin
 * @version HjhPlanJoinOnJob, v0.1 2018/8/14 16:36
 */
@DisallowConcurrentExecution  // 禁止并发执行
public class HjhPlanJoinOnJob extends BaseJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("HjhPlanJoinOnJob: {} execute...", context.getJobDetail().getKey().getName());

        BooleanResponse result = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/hjhPlanSwitchController/batch/hjhPlanJoinOn", BooleanResponse.class).getBody();

        logger.info("HjhPlanJoinOnJob execute end...");
    }
}