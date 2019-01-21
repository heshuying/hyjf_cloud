/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.sell.daily;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 销售日报定时发送邮件
 *
 * @author yaoyong
 * @version DailyAutoSendJob, v0.1 2018/11/15 16:45
 */
@DisallowConcurrentExecution
public class DailyAutoSendJob extends BaseJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("DailyAutoSendJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-MARKET/cs-market/dailySend/send", Object.class);
        logger.info("DailyAutoSendJob execute end...");
    }
}
