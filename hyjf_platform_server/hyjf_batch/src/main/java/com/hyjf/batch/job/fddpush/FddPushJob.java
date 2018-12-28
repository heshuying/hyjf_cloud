/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.fddpush;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yangchangwei
 * @version FddPushJob, v0.1 2018-12-25
 * 放款后推送法大大协议
 */
@DisallowConcurrentExecution
public class FddPushJob extends BaseJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(FddPushJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("放款后推送法大大协议任务开始执行: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/cs-trade/batch/fddPush", String.class);
        logger.info("放款后推送法大大协议任务执行结束，execute end...");
    }
}
