package com.hyjf.batch.job.userportrait.screen;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Auther:dangzw
 * @Date:2019/4/25
 * @Description:用户画像-运营部投屏二数据获取
 */
@DisallowConcurrentExecution
public class OperationScreenTJob extends BaseJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 0 17 4 * * ? 每天凌晨4点17分执行一次
        logger.info("OperationScreenTJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://AM-ADMIN/am-admin/batch/operationScreenBatch", String.class);
        logger.info("OperationScreenTJob execute end...");
    }
}
