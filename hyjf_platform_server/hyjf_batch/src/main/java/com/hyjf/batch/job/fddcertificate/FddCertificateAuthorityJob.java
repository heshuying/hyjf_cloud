/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.fddcertificate;

import com.hyjf.batch.job.BaseJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangqingqing
 * @version FddCertificateAuthorityJob, v0.1 2018/6/26 13:51
 * 法大大电子签章批量做CA认证
 */
public class FddCertificateAuthorityJob  extends BaseJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(FddCertificateAuthorityJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("CouponExpiredJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("", String.class);
        logger.info("CouponExpiredJob execute end...");
    }
}
