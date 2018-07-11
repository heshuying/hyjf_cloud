/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.borrow.tender;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自动投资
 * @author liubin
 * @version AutoTenderJob, v0.1 2018/6/28 13:56
 */
@DisallowConcurrentExecution  // 禁止并发执行
public class AutoTenderJob extends BaseJob implements Job {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("AutoTenderJob: {} execute...", context.getJobDetail().getKey().getName());

        Boolean result = restTemplate.getForEntity(
                "http://CS-TRADE/batch/tender/autotender", Boolean.class).getBody();

        logger.info("AutoTenderJob execute end...");
    }
}
