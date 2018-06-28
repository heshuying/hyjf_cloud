/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.eve.downloadFile;

import com.hyjf.batch.job.BaseJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wangjun
 * @version DownloadFileJob, v0.1 2018/6/25 9:57
 */
public class DownloadFileJob extends BaseJob implements Job {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("DownloadFileJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://AM-TRADE/am-trade/batch/downloadFiles", String.class);
        logger.info("DownloadFileJob execute end...");
    }
}
