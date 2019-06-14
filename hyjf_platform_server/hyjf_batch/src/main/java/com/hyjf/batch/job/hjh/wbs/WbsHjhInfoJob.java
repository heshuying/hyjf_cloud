/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.hjh.wbs;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * WBS系统计划类产品发送MQ定时
 *
 * @author liuyang
 * @version WbsHjhInfoJob, v0.1 2019/4/15 17:46
 */
@DisallowConcurrentExecution
public class WbsHjhInfoJob extends BaseJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("WbsHjhInfoJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/cs-trade/wbsHjhPlanInfo/wbsSendHjhPlanInfo", String.class);
        logger.info("WbsHjhInfoJob execute end...");
    }
}
