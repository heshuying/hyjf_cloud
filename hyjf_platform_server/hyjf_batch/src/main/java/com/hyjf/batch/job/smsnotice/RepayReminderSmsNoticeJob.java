/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.smsnotice;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author PC-LIUSHOUYI
 * @version RepayReminderSmsNoticeJob, v0.1 2018/6/22 10:20
 * 还款前三天提醒借款人还款短信定时
 */
@DisallowConcurrentExecution  // 禁止并发执行
public class RepayReminderSmsNoticeJob extends BaseJob implements Job {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("RepayReminderSmsNoticeJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/repayReminder/smsNotice", String.class);
        logger.info("RepayReminderSmsNoticeJob execute end...");
    }
}
