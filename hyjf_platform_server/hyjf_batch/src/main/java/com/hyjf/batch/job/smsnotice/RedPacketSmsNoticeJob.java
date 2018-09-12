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
 * @version RedPacketSmsNoticeJob, v0.1 2018/6/20 12:02
 * 红包账户余额短信提醒
 */
/** 禁止并发执行 */
@DisallowConcurrentExecution
public class RedPacketSmsNoticeJob extends BaseJob implements Job {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("RedPacketSmsNoticeJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/redPacket/smsNotice", String.class);
        logger.info("RedPacketSmsNoticeJob execute end...");
    }
}
