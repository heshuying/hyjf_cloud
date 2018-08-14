/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.hjh.plancapital;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 汇计划资本预估统计(每日)定时任务 "0 30 1 * * ?"
 * @author liubin
 * @version HjhPlanCapitalJob, v0.1 2018/7/28 13:56
 */
@DisallowConcurrentExecution  // 禁止并发执行
public class HjhPlanCapitalJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("HjhPlanCapitalJob: {} execute...", context.getJobDetail().getKey().getName());

        Boolean result = restTemplate.getForEntity(
                "http://CS-MESSAGE/cs-message/hjh_plan_capital/hjhplancapital", Boolean.class).getBody();

        logger.info("HjhPlanCapitalJob execute end...");
    }
}
