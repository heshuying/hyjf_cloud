package com.hyjf.batch.job.statistic;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 上海大屏幕运营数据
 * @Author : huanghui
 */
@DisallowConcurrentExecution            //禁止并发
public class OperationalDataJob extends BaseJob implements Job {

    //  0 0 */2 * * ?  --> 每两个小时运行一次

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("OperationalDataJob: {} execute...", jobExecutionContext.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://AM-TRADE/am-trade/batch/countRechargeMoney", String.class);
        logger.info("OperationalDataJob: {} execute end...");
    }
}
