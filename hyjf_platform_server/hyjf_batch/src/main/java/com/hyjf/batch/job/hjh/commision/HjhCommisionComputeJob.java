package com.hyjf.batch.job.hjh.commision;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 汇计划提成计算定时
 * @author hesy
 * @version HjhCommisionComputeJob, v0.1 2018/8/17 11:35
 */
@DisallowConcurrentExecution  // 禁止并发执行
public class HjhCommisionComputeJob extends BaseJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("HjhCommisionComputeJob: {} execute...", context.getJobDetail().getKey().getName());

        restTemplate.getForEntity(
                "http://hyjf-web/hjh/commision/compute", Boolean.class).getBody();

        logger.info("HjhCommisionComputeJob execute end...");
    }
}
