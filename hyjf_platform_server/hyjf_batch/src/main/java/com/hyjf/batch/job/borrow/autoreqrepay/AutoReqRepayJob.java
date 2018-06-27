package com.hyjf.batch.job.borrow.autoreqrepay;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hyjf.batch.job.BaseJob;

/**
 * 自动还款请求
 * @author dxj
 *
 */
@DisallowConcurrentExecution  // 禁止并发执行
public class AutoReqRepayJob extends BaseJob implements Job {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("AutoReqRepayJob: {} execute...", context.getJobDetail().getKey().getName());
        
        restTemplate.getForEntity("http://AM-TRADE/batch/autoReqRepay", String.class);
        
        logger.info("AutoReqRepayJob execute end...");
    }
}
