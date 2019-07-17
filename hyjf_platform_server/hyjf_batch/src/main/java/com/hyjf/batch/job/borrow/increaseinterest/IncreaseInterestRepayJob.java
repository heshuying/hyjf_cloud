package com.hyjf.batch.job.borrow.increaseinterest;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

/**
 * 产品加息还款批处理任务
 * @author dxj
 *
 */
@DisallowConcurrentExecution  // 禁止并发执行
public class IncreaseInterestRepayJob extends BaseJob implements Job {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
//        logger.info("IncreaseInterestRepayJob: {} execute...", context.getJobDetail().getKey().getName());
//
//        restTemplate.getForEntity("http://CS-TRADE/cs-trade/batch/increaseinterest/taskAssignRepay", String.class);
//
//        logger.info("IncreaseInterestRepayJob execute end...");
    }
}
