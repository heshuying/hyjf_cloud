/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.cert.exception;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description 应急中心上报异常处理   五分钟跑一次
 * @Author sunss
 * @Date 2019/1/30 14:57
 */
@DisallowConcurrentExecution
public class CertSendExceptionJob extends BaseJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(CertSendExceptionJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("CertSendExceptionJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/cs-trade/batch/certException/doException", String.class).getBody();
        logger.info("CertSendExceptionJob execute end...");
    }
}
