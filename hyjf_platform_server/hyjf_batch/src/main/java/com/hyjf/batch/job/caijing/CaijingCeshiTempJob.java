package com.hyjf.batch.job.caijing;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Author: yinhui
 * @Date: 2019/6/12 13:44
 * @Version 1.0
 */
@DisallowConcurrentExecution
public class CaijingCeshiTempJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("CaijingCeshiTempJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-MESSAGE/cs-message/zeroOneCaiJingController/ceshiInvestRecordSub", null);
        logger.info("CaijingCeshiTempJob execute end...");
    }
}
