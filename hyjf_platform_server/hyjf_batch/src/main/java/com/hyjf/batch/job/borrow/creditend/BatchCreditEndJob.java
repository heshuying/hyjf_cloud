/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.borrow.creditend;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 批次结束债权
 * @author liubin
 * @version BatchCreditEndJob, v0.1 2018/7/10 13:56
 */
@DisallowConcurrentExecution  // 禁止并发执行
public class BatchCreditEndJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("BatchCreditEndJob: {} execute...", context.getJobDetail().getKey().getName());

        Boolean result = restTemplate.getForEntity(
                "http://CS-TRADE/batch/creditend/batchcreditend", Boolean.class).getBody();

        logger.info("BatchCreditEndJob execute end...");
    }
}
