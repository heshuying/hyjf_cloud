package com.hyjf.batch.job.hjh.hjhalerm;


import com.hyjf.batch.job.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 清算日前一天扫描处于出借中或者复审中的原始标的进行预警
 * @author zhangyk
 * @date 2018/8/20 15:49
 */
@DisallowConcurrentExecution  // 禁止并发执行
public class HjhAlermBeforeLiquidateCheckJob extends BaseJob implements Job {


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("HjhAlermBeforeLiquidateCheckJob: {} execute...", context.getJobDetail().getKey().getName());

        restTemplate.getForEntity(
                "http://CS-TRADE/cs-trade/hjhAlarmController/batch/alermBeforeLiquidateCheck", null);

        logger.info("HjhAlermBeforeLiquidateCheckJob execute end...");
    }
}
