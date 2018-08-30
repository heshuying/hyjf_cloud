/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.finance.poundage;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wangjun
 * @version PoundageJob, v0.1 2018/6/21 15:40
 * 手续费分账明细插入定时
 */
@DisallowConcurrentExecution
public class PoundageJob extends BaseJob implements Job {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("PoundageJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://AM-TRADE/am-trade/batch/poundage", String.class);
        logger.info("PoundageJob execute end...");
    }
}
