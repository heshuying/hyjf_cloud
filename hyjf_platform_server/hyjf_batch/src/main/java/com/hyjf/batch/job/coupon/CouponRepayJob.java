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
 * @version CouponRepayJob, v0.1 2018/6/12 15:23
 *体验金按收益期限还款
 */
public class CouponRepayJob extends BaseJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(CouponRepayJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("CouponRepayJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/batch/coupon/periodRepay", String.class);
        logger.info("CouponRepayJob execute end...");
    }
}
