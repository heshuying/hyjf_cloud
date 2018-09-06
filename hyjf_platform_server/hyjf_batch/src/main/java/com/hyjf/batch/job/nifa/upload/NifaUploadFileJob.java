/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.nifa.upload;

import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author PC-LIUSHOUYI
 * @version NifaUploadFileJob, v0.1 2018/9/4 15:37
 * 互金自动上报数据上传文件
 */
/** 禁止并发执行 */
@DisallowConcurrentExecution
public class NifaUploadFileJob extends BaseJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("NifaUploadFileJob: {} execute...", context.getJobDetail().getKey().getName());

        restTemplate.getForEntity("http://AM-TRADE/am-trade/nifa_file_deal/upload_file", boolean.class);

        logger.info("NifaUploadFileJob execute end...");
    }
}
