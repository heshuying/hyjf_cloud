/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.userportrait;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author: sunpeikai
 * @version: UserPortraitJob, v0.1 2018/6/27 16:53
 * 员工画像定时任务
 */
@DisallowConcurrentExecution
public class UserPortraitJob extends BaseJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 0 0 1 * * ? 每天凌晨1点执行一次
        logger.info("UserPortraitJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-USER/cs-user/batch/user_portrait_batch", String.class);
        logger.info("UserPortraitJob execute end...");
    }
}
