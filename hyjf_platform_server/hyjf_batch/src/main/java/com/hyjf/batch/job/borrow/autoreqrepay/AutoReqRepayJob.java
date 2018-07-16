package com.hyjf.batch.job.borrow.autoreqrepay;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 自动还款请求
 * @author dxj
 *
 */
@DisallowConcurrentExecution  // 禁止并发执行
public class AutoReqRepayJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("AutoReqRepayJob: {} execute...", context.getJobDetail().getKey().getName());
        
        restTemplate.getForEntity("http://AM-TRADE/am-trade/autoReqRepayController/autoReqRepay", boolean.class);
        
        logger.info("AutoReqRepayJob execute end...");
    }
}
