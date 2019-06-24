package com.hyjf.batch.job.caijing;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 零壹财经推送昨天的数据
 * @Author: yinhui
 * @Date: 2019/6/12 13:40
 * @Version 1.0
 */
@DisallowConcurrentExecution
public class CaijingYesterDaySendJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("CaijingYesterDaySendJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-MESSAGE/cs-message/zeroOneCaiJingController/investRecordSub", null);
        logger.info("CaijingYesterDaySendJob execute end...");
    }
}
