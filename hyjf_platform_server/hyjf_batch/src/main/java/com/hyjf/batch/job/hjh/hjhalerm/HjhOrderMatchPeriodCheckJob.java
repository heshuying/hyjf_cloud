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
 * hjh订单匹配期超过两天短信预警
 * @author zhangyk
 * @date 2018/8/15 14:02
 */
@DisallowConcurrentExecution
public class HjhOrderMatchPeriodCheckJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("HjhOrderMatchPeriodCheckJob: {} execute...", context.getJobDetail().getKey().getName());

        restTemplate.getForEntity(
                "http://CS-TRADE/cs-trade/hjhAlarmController/batch/hjhOrderMatchPeriodCheck",null);

        logger.info("HjhOrderMatchPeriodCheckJob execute end...");
    }
}
