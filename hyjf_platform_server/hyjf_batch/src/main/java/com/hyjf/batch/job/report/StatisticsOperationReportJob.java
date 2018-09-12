/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.batch.job.report;

import com.hyjf.am.response.StringResponse;
import com.hyjf.batch.job.BaseJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 运营报告每月统计
 * @author tanyy
 * @version StatisticsOperationReportJob, v0.1 2018/7/26 9:21
 */
public class StatisticsOperationReportJob extends BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("StatisticsOperationReportJob: {} execute...", context.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-MESSAGE/cs-message/operation_report_job/countOperationReport", StringResponse.class);
        logger.info("StatisticsOperationReportJob: {} execute end...", context.getJobDetail().getKey().getName());
    }
}
