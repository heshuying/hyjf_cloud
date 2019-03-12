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
 * 订单出借异常短信预警
 * @author zhangyk
 * @date 2018/8/15 16:22
 */
@DisallowConcurrentExecution
public class HjhOrderInvestRepairCheckJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("HjhOrderExitCheckJob: {} execute...", context.getJobDetail().getKey().getName());

        restTemplate.getForEntity(
                "http://CS-TRADE/cs-trade/hjhAlarmController/batch/hjhOrderInvestExceptionCheck", null);

        logger.info("HjhOrderExitCheckJob execute end...");
    }
}
