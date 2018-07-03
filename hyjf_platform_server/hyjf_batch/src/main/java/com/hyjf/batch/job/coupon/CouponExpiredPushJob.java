/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.coupon;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yaoy
 * @version CouponExpiredPushJob, v0.1 2018/6/19 15:18
 * 优惠券过期发送push消息定时任务
 */
@DisallowConcurrentExecution
public class CouponExpiredPushJob extends BaseJob implements Job{
    private static final Logger logger = LoggerFactory.getLogger(CouponRepayJob.class);
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("CouponExpiredPushJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/batch/couponExpiredPush/expiredPush", String.class);
        logger.info("CouponExpiredPushJob execute end...");
    }
}
