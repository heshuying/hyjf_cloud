package com.hyjf.batch.job.statistic;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 上海大屏幕运营数据 公司动态
 * @Author : huanghui
 * 禁止并发
 */
@DisallowConcurrentExecution
public class OperationalArticleDataJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("OperationalDataJob: {} execute...", jobExecutionContext.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://AM-CONFIG/am-config/contentArticle/getContentList", String.class);
        logger.info("OperationalDataJob: {} execute end...");
    }

}
