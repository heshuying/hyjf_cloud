/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.nifa.download;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author PC-LIUSHOUYI
 * @version NifaDownloadFileJob, v0.1 2018/9/4 15:38
 * 互金下载反馈文件
 */
/** 禁止并发执行 */
@DisallowConcurrentExecution
public class NifaDownloadFileJob extends BaseJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("NifaDownloadFileJob: {} execute...", context.getJobDetail().getKey().getName());

        restTemplate.getForEntity("http://AM-TRADE/am-trade/nifa_file_deal/download_file", boolean.class);

        logger.info("NifaDownloadFileJob execute end...");
    }
}
