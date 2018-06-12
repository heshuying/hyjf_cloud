/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wangjun
 * @version UpdateBankCardJob, v0.1 2018/5/29 17:08
 */
public class UpdateBankCardJob extends BaseJob implements Job {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("UpdateBankCardJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://AM-USER/updateBankCard/batch/update", String.class);
        logger.info("UpdateBankCardJob execute end...");
    }
}
