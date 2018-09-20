/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.msgpush;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hyjf.batch.job.BaseJob;

/**
 * 平台消息统计报表
 * @author fq
 * @version MsgPushPlatStaticsJob, v0.1 2018/9/19 18:48
 */
@DisallowConcurrentExecution
public class MsgPushPlatStaticsJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("MsgPushPlatStaticsJob: {} execute...", jobExecutionContext.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-MESSAGE/cs-message/app_message/push_plat_statics", Object.class);
        logger.info("MsgPushPlatStaticsJob execute end...");
    }
}
