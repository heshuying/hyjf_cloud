/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.statistic.totalinterest;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 定时更新运营数据
 * @author fq
 * @version TotalInvestAndInterestJob, v0.1 2018/7/31 10:51
 */
@DisallowConcurrentExecution
public class TotalInvestAndInterestJob extends BaseJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("TotalInvestAndInterestJob: {} execute...",
                jobExecutionContext.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-MESSAGE/cs-message/totalinvestandinterest/execute",
                Object.class);
        logger.info("TotalInvestAndInterestJob execute end...");
    }
}
