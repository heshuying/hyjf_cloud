package com.hyjf.batch.job.cert.getyibumessage;

import com.hyjf.batch.job.BaseJob;
import com.hyjf.common.util.CustomConstants;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description 合规数据上报 CERT 查询批次数据入库消息
 * @Author nxl
 * @Date 2018/12/25 14:10
 */
public class CertGetYiBuMessageJob extends BaseJob implements Job {
    Logger _log = LoggerFactory.getLogger(CertGetYiBuMessageJob.class);

    private String thisMessName = "查询批次数据入库消息";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_CERT + " " + thisMessName + "】";
    Logger logger = LoggerFactory.getLogger(CertGetYiBuMessageJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info(logHeader + "CertGetYiBuMessageJob: {} execute...", jobExecutionContext.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/cs-trade/batch/getCertYiBuMessage/certYiBuMessage", Object.class);
        logger.info(logHeader + "CertGetYiBuMessageJob execute end...");
    }
}
