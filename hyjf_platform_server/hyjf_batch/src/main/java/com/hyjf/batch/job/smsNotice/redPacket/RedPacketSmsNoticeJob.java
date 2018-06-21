/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.smsNotice.redPacket;

import com.hyjf.batch.job.BaseJob;
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
public class RedPacketSmsNoticeJob  extends BaseJob implements Job {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("RedPacketSmsNoticeJob: {} execute...", context.getJobDetail().getKey().getName());
        // TODO 调用原子层地址需要修改
        restTemplate.getForEntity("http://CS-TRADE/tradeBatch/redPacket/smsNotice", String.class);
        logger.info("RedPacketSmsNoticeJob execute end...");
    }
}
