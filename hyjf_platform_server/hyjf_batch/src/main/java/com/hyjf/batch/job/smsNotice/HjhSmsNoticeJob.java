/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.smsnotice;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 标的还款逾期短信提醒
 * @author jijun
 * @version HjhSmsNoticeTask, v0.1 2018/6/26 9:21
 */
@DisallowConcurrentExecution  // 禁止并发执行
public class HjhSmsNoticeJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("HjhSmsNoticeJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/cs-trade/hjhSmsNotice/overdueSmsNotice", String.class);
        logger.info("HjhSmsNoticeJob: {} execute...", context.getJobDetail().getKey().getName());
    }
}
