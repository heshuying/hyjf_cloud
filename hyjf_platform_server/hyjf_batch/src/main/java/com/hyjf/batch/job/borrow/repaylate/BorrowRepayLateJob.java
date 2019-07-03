package com.hyjf.batch.job.borrow.repaylate;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author wangjun
 * @version BorrowRepayLateJob, v0.1 2019/3/20 11:58
 * 计算逾期还款标的逾期利息
 */
@DisallowConcurrentExecution  // 禁止并发执行
public class BorrowRepayLateJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("BorrowRepayLateJob: {} execute...", context.getJobDetail().getKey().getName());
        
        restTemplate.getForEntity("http://CS-TRADE/cs-trade/batch/repaylate", String.class);
        
        logger.info("BorrowRepayLateJob execute end...");
    }
}
