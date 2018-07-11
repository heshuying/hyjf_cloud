package com.hyjf.batch.job.borrow.issue;

/**
 * @author xiasq
 * @version BorrowOfTimingIssueJob, v0.1 2018/7/11 9:41
 * 不拆分散标自动发标任务
 */

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/** 禁止并发执行 */
@DisallowConcurrentExecution
public class BorrowOfTimingIssueJob extends BaseJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("BorrowOfTimingIssueJob: {} execute...", context.getJobDetail().getKey().getName());

		String result = restTemplate
				.getForEntity("http://AM-TRADE/am-trade/timing_borrow/issue/none_split_borrow", String.class).getBody();

		logger.info("BorrowOfTimingIssueJob execute end...result is :{}", result);
	}
}
