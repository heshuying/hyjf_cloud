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
 * @version CouponExpiredSmsJob, v0.1 2018/6/22 11:20
 * 优惠券过期短信提醒
 */
public class CouponExpiredSmsJob extends BaseJob implements Job{

    private static final Logger logger = LoggerFactory.getLogger(CouponExpiredSmsJob.class);
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("CouponExpiredSmsJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/batch/coupon/expiredSms", String.class);
        logger.info("CouponExpiredSmsJob execute end...");
    }
}
