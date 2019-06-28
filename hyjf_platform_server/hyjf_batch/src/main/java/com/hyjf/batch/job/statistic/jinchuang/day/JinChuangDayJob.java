package com.hyjf.batch.job.statistic.jinchuang.day;

import com.hyjf.am.response.StringResponse;
import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Auther:yangchangwei
 * @Date:2019/6/28
 * @Description: 金创每日更新数据
 */
@DisallowConcurrentExecution
public class JinChuangDayJob  extends BaseJob implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("JinChuangDayJob: {} execute...", jobExecutionContext.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://DATA-STATISTICS/jinchuang/day/updatedata", StringResponse.class);
        logger.info("JinChuangDayJob execute end...");
    }
}
