package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.mq.base.CommonProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.BankAleveService;
import com.hyjf.am.resquest.admin.BankAleveRequest;
import com.hyjf.am.vo.admin.BankAleveVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.file.FileUtil;
import com.hyjf.common.file.SFTPParameter;
import com.hyjf.common.util.FtpUtil;
import com.hyjf.common.util.calculate.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * Created by zdj on 2018/7/20.
 */
@Service
public class BankAleveServiceImpl extends BaseServiceImpl implements BankAleveService{

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    private CommonProducer commonProducer;

    @Autowired
    private AmAdminClient amAdminClient;

    /**
     * 根据筛选条件查询银行账务明细list
     * @param
     * @return
     */
    @Override
    public List<BankAleveVO> queryBankAleveList(BankAleveRequest request) {
        return amTradeClient.queryBankAleveList(request);
    }

    @Override
    public Integer queryBankAleveCount(BankAleveRequest request) {
        return amTradeClient.queryBankAleveCount(request);
    }

    /**
     * 根据日期下载对账文件并发送mq导入数据
     *
     * @param dualDate
     * @return
     */
    @Override
    public String dualHistoryData(String dualDate) {
        SFTPParameter para = new SFTPParameter();

        para.hostName = systemConfig.getFtpHostName();//ftp服务器地址
        para.userName = systemConfig.getFtpUserName();//ftp服务器用户名
        para.passWord = systemConfig.getFtpPassWord();//ftp服务器密码
        para.port = StringUtils.isBlank(systemConfig.getEvePort())? 0 : Integer.valueOf(systemConfig.getEvePort());//ftp服务器端口

        String beforeYear = DateUtils.getBeforeYear();//当前时间前一天的年份
        String beforeMonth = DateUtils.getBeforeMonth();//当前时间前一天的月份
        String beforeDay = DateUtils.getBeforeDay();//当前前时间前一天的日期
//        String date = DateUtils.getNowDateOfDay();//当天日期返回时间类型 yyyyMMdd
        String localDir = systemConfig.getLocalDir();
        FileUtil.createDir(localDir+"/"+dualDate);
        String filePath = beforeYear+"/"+beforeMonth+"/"+beforeDay;
        para.downloadPath =systemConfig.getFtpDownloadPath()+ filePath;//ftp服务器文件目录
        para.savePath =localDir+"/"+dualDate;
//        String beforeDate = DateUtils.getBeforeDate();//当前前时间前一天的日期yyyyMMdd
        Integer countsAleve = this.amAdminClient.countAleveByDualDate(dualDate);
        if (countsAleve < 0) {
            logger.info("【手动导入对账文件】查询Aleve导入件数出错!");
            return "查询Aleve导入件数出错！";
        }

        Integer countsEve = this.amAdminClient.countEveByDualDate(dualDate);
        if (countsEve < 0) {
            logger.info("【手动导入对账文件】查询eve导入件数出错!");
            return "查询eve导入件数出错！";
        }

        try {
            if(countsAleve==0 && countsEve==0){
                //删除前一天的文件目录
                FileUtil.deltree(localDir+"/"+dualDate);
                if(!FtpUtil.downloadFiles(para)){
                    logger.error("【手动导入对账文件】下载ftp文件失败");
                    return "下载ftp文件失败!";
                }else{
                    File dir = new File(para.savePath);
                    // 只有aleve跟eve两个文件都下载成功才发MQ
                    if(dir.listFiles().length == 2){
                        logger.info("【手动导入对账文件】aleve与eve文件下载成功，开始发送MQ");
                        JSONObject params = new JSONObject();
                        params.put("status", "1");
                        params.put("savePath", para.savePath);
                        params.put("filePathEve", systemConfig.getEveFileName());
                        params.put("filePathAleve", systemConfig.getAleveFileName());
                        params.put("dualDate",dualDate);
                        // 修复数据不冲正
                        params.put("isBOA","0");
                        try {
                            commonProducer.messageSend(new MessageContent(MQConstant.ALEVE_FILE_TOPIC, UUID.randomUUID().toString(), params));
                        } catch (MQException e) {
                            logger.error("【手动导入对账文件】发送【导入手续费流水明细(aleve)】MQ失败...",e);
                            return "发送【导入手续费流水明细(aleve)】MQ失败...";
                        }
                        try {
                            commonProducer.messageSend(new MessageContent(MQConstant.EVE_FILE_TOPIC, UUID.randomUUID().toString(),params));
                        } catch (MQException e) {
                            logger.error("【手动导入对账文件】发送【导入手续费流水明细(eve)】MQ失败...",e);
                            return "发送【导入红包账户流水明细(eve)】MQ失败...";
                        }
                    } else {
                        logger.error("【手动导入对账文件】下载文件失败或者不全。下载成功文件数：{}",dir.listFiles().length);
                        return "下载文件失败或者不全。下载成功文件数：" + dir.listFiles().length;
                    }
                    para.release();
                }
                logger.info("【手动导入对账文件】处理成功！");
                return "处理成功";
            } else {
                logger.error("【手动导入对账文件】文件已导入数据库，请重新核对！");
                return "文件已导入数据库，请重新核对！";
            }

        } catch (Exception e) {
            logger.error("【手动导入对账文件】文件处理失败！");
            return "文件处理失败！";
        }
    }
}
