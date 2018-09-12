package com.hyjf.batch.job.borrow.increaseinterest;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import com.hyjf.batch.job.BaseJob;

/**
 * 产品加息放款批处理任务
 * @author dxj
 *
 */
@DisallowConcurrentExecution  // 禁止并发执行
public class IncreaseinterestLoansJob extends BaseJob implements Job {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("IncreaseinterestLoansJob: {} execute...", context.getJobDetail().getKey().getName());
        
        ResponseEntity<String> result = restTemplate.getForEntity("http://AM-TRADE/am-trade/batch/increaseinterestLoans", String.class);
        
        logger.info("IncreaseinterestLoansJob execute end..."+result.getBody());
    }
}
