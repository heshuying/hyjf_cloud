/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.rtb;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yaoy
 * @version IncreaseInterestRepayJob, v0.1 2018/6/12 15:15
 *融通宝加息(放款)定时任务
 */
@DisallowConcurrentExecution
public class IncreaseInterestRepayJob extends BaseJob implements Job {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("IncreaseInterestRepayJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/batch/rtb/increaseInterestRepay", String.class);
        logger.info("IncreaseInterestRepayJob execute end...");
    }
}
