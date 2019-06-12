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
 * 批次结束债权未收到回调批次状态查询并更新
 * @author hesy
 */
@DisallowConcurrentExecution  // 禁止并发执行
public class BatchCreditEndCallBackFailProcessJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("BatchCreditEndCallBackFailProcessJob: {} execute...", context.getJobDetail().getKey().getName());

        BooleanResponse result = restTemplate.getForEntity(
                "http://CS-TRADE/batch/creditend/batchQuery", BooleanResponse.class).getBody();

        logger.info("BatchCreditEndCallBackFailProcessJob execute end...");
    }
}
