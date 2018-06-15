/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.coupon;

import com.hyjf.batch.job.BaseJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ${yaoy}
 * @version RtbLoansJob, v0.1 2018/6/12 15:15
 *融通宝加息(放款)定时任务
 */
public class RtbLoansJob extends BaseJob implements Job {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("RtbLoansJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/batch/rtb/rtbloans", String.class);
        logger.info("RtbLoansJob execute end...");
    }
}
