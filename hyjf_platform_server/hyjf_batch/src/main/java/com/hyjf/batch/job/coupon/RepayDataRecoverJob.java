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
 * @author yaoy
 * @version RepayDataRecoverJob, v0.1 2018/6/25 10:44
 * 优惠券还款掉单修复定时任务
 */
public class RepayDataRecoverJob extends BaseJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(CouponRepayJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("RepayDataRecoverJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/batch/coupon/dataRecover", String.class);
        logger.info("RepayDataRecoverJob execute end...");
    }
}
