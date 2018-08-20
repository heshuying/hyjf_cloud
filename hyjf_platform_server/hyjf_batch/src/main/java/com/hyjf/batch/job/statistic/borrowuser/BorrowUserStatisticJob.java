/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.statistic.borrowuser;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 借款人数据定时任务
 * @author fuqiang
 * @version BorrowUserStatisticJob, v0.1 2018/7/18 15:40
 */
@DisallowConcurrentExecution
public class BorrowUserStatisticJob extends BaseJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("BorrowUserStatisticJob: {} execute...",
                jobExecutionContext.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-MARKET/cs-market/statisticsoperationreport/insertstatistic",
                Object.class);
        logger.info("BorrowUserStatisticJob execute end...");
    }
}
