package com.hyjf.batch.job.cert.transact;

import com.hyjf.batch.job.BaseJob;
import com.hyjf.common.util.CustomConstants;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description 合规数据上报 CERT 国家互联网应急中心交易流水上报
 * @Author pcc
 * @Date 2019/1/31 10:10
 */
public class CertTransactMessageJob extends BaseJob implements Job {
    Logger _log = LoggerFactory.getLogger(CertTransactMessageJob.class);

    private String thisMessName = "国家互联网应急中心交易流水上报";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_CERT + " " + thisMessName + "】";
    Logger logger = LoggerFactory.getLogger(CertTransactMessageJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info(logHeader + "CertTransactMessageJob: {} execute...", jobExecutionContext.getJobDetail().getKey().getName());
        restTemplate.getForEntity("http://CS-TRADE/cs-trade/batch/certTransactMessage/certTransact", Object.class);
        logger.info(logHeader + "CertTransactMessageJob execute end...");
    }
}
