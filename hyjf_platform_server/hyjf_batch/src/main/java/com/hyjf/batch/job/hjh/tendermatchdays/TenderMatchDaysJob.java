/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.hjh.tendermatchdays;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 计算自动投资的匹配期(每日)
 * @author liubin
 * @version TenderMatchDaysJob, v0.1 2018/8/23 10:35
 */
@DisallowConcurrentExecution  // 禁止并发执行
public class TenderMatchDaysJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("TenderMatchDaysJob: {} execute...", context.getJobDetail().getKey().getName());

        Boolean result = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/tenderMatchDaysController/batch/tenderMatchDays", Boolean.class).getBody();

        logger.info("TenderMatchDaysJob execute end...");
    }
}
