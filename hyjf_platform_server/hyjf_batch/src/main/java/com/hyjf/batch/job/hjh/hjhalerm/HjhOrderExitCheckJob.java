/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.hjh.hjhalerm;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 计划订单退出超过两天邮件预警
 * @author zhangyk
 * @date 2018/8/15 15:42
 */
@DisallowConcurrentExecution
public class HjhOrderExitCheckJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("HjhOrderExitCheckJob: {} execute...", context.getJobDetail().getKey().getName());

        restTemplate.getForEntity(
                "http://CS-TRADE/cs-trade/hjhAlarmController/batch/hjhOrderExitCheck", null);

        logger.info("HjhOrderExitCheckJob execute end...");
    }
}
