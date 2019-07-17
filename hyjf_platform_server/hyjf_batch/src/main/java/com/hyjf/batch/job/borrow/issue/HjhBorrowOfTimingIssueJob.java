package com.hyjf.batch.job.borrow.issue;

/**
 * @author xiasq
 * @version HjhBorrowOfTimingIssueJob, v0.1 2018/7/11 9:41
 * 计划自动发标任务
 */

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/** 禁止并发执行 */
@DisallowConcurrentExecution
public class HjhBorrowOfTimingIssueJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
//		logger.info("HjhBorrowOfTimingIssueJob: {} execute...", context.getJobDetail().getKey().getName());
//
//		restTemplate.getForEntity("http://CS-TRADE/cs-trade/batch/timing/hjhBorrow", String.class).getBody();
//
//		logger.info("HjhBorrowOfTimingIssueJob execute end...");
    }
}
