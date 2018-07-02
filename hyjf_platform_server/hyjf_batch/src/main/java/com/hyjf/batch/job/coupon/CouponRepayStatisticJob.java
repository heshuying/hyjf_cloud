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
 * @version CouponRepayStatisticJob, v0.1 2018/6/19 11:50
 * 加息券还款统计定时任务
 */
@DisallowConcurrentExecution
public class CouponRepayStatisticJob extends BaseJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(CouponRepayJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("CouponRepayStatisticJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/batch/coupon/couponRepayStatistic", String.class);
        logger.info("CouponRepayStatisticJob execute end...");
    }
}
