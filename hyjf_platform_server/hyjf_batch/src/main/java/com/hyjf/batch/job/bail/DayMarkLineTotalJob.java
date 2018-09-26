/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.bail;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 日、月推标额度定时任务
 *
 * @author PC-LIUSHOUYI
 * @version DayMarkLineTotalTask, v0.1 2018/9/25 17:06
 */
@DisallowConcurrentExecution
public class DayMarkLineTotalJob extends BaseJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("DayMarkLineTotalJob: {} execute...", jobExecutionContext.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://AM-TRADE/am-trade/day_mark_line_total/update_day_mark_line", String.class);
        logger.info("DayMarkLineTotalJob execute end...");
    }
}
