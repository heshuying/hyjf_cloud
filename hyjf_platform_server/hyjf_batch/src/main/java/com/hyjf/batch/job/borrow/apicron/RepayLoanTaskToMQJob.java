package com.hyjf.batch.job.borrow.apicron;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 批次还款，实时放款批处理任务
 * @author dxj
 *
 */
@DisallowConcurrentExecution  // 禁止并发执行
public class RepayLoanTaskToMQJob extends BaseJob implements Job {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("RepayLoadToMQJob: {} execute...", context.getJobDetail().getKey().getName());
        
        restTemplate.getForEntity("http://AM-TRADE/am-trade/batch/taskAssign", String.class);
        
        logger.info("RepayLoadToMQJob execute end...");
    }
}
