/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.borrow.creditend;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

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

        BooleanResponse result = restTemplate.getForEntity(
                "http://CS-TRADE/batch/creditend/batchcreditend", BooleanResponse.class).getBody();

        logger.info("BatchCreditEndJob execute end...");
    }
}
