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
 * @version CouponExpiredJob, v0.1 2018/6/19 9:09
 * 优惠劵过期提醒
 */
public class CouponExpiredJob extends BaseJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(CouponRepayJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("CouponExpiredJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://AM-TRADE/batch/coupon/expired", String.class);
        logger.info("CouponExpiredJob execute end...");
    }
}
