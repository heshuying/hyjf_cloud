/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.task;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.config.SystemConfig;
import com.hyjf.am.trade.dao.mapper.auto.AccountListMapper;
import com.hyjf.am.trade.dao.mapper.auto.AleveErrorLogMapper;
import com.hyjf.am.trade.dao.mapper.auto.AleveLogMapper;
import com.hyjf.am.trade.dao.mapper.auto.EveLogMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.AdminAccountCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.trade.AleveCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.trade.ManualReverseCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.trade.AleveLogCustomize;
import com.hyjf.am.trade.mq.DownloadFileProducer;
import com.hyjf.am.trade.mq.Producer;
import com.hyjf.am.trade.service.AccountService;
import com.hyjf.am.trade.service.task.AleveLogFileService;
import com.hyjf.am.trade.utils.FileUtil;
import com.hyjf.am.trade.utils.FtpUtil;
import com.hyjf.am.trade.utils.SFTPParameter;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version AleveLogFileServiceImpl, v0.1 2018/6/25 10:09
 */
@Service
public class AleveLogFileServiceImpl implements AleveLogFileService {
    private static final Logger logger = LoggerFactory.getLogger(AleveLogFileServiceImpl.class);

    @Autowired
    protected AleveLogMapper aleveLogMapper;

    @Autowired
    protected EveLogMapper eveLogMapper;

    @Autowired
    protected AleveErrorLogMapper aleveErrorLogMapper;

    @Autowired
    private AleveCustomizeMapper aleveCustomizeMapper;

    @Autowired
    private ManualReverseCustomizeMapper manualReverseCustomizeMapper;

    @Autowired
    private AccountListMapper accountListMapper;

    @Autowired
    private AdminAccountCustomizeMapper adminAccountCustomizeMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    private DownloadFileProducer downloadFileProducer;

    /**
     * 存款业务红包流水全明细数据文件下载
     */
    @Override
    public void downloadFiles(){
        SFTPParameter para = new SFTPParameter();

        para.hostName = systemConfig.getFtpHostName();//ftp服务器地址
        para.userName = systemConfig.getFtpUserName();//ftp服务器用户名
        para.passWord = systemConfig.getFtpPassWord();//ftp服务器密码
        para.port = StringUtils.isBlank(systemConfig.getFtpPort())? 0 : Integer.valueOf(systemConfig.getFtpPort());//ftp服务器端口

        String beforeYear = DateUtils.getBeforeYear();//当前时间前一天的年份
        String beforeMonth = DateUtils.getBeforeMonth();//当前时间前一天的月份
        String beforeDay = DateUtils.getBeforeDay();//当前前时间前一天的日期
        String date = DateUtils.getNowDateOfDay();//当天日期返回时间类型 yyyyMMdd
        String localDir = systemConfig.getLocalDir();
        Boolean dir = FileUtil.createDir(localDir+"/"+date);
        String filePath = beforeYear+"/"+beforeMonth+"/"+beforeDay;
        para.downloadPath =systemConfig.getFtpDownloadPath()+ filePath;//ftp服务器文件目录
        para.savePath =localDir+"/"+date;
        String beforeDate = DateUtils.getBeforeDate();//当前前时间前一天的日期yyyyMMdd
        int countsAleve = this.countAleveByExample(beforeDate);
        int countsEve = this.countEveByExample(beforeDate);

        try {
            if(countsAleve==0 && countsEve==0){
                //删除前一天的文件目录
                FileUtil.deltree(localDir+"/"+beforeDate);
                if(!FtpUtil.downloadFiles(para)){
                    logger.error("下载ftp文件失败");
                }else{
                    JSONObject params = new JSONObject();
                    params.put("status", "1");
                    params.put("savePath", para.savePath);
                    params.put("filePathEve", systemConfig.getEveFileName());
                    params.put("filePathAleve", systemConfig.getAleveFileName());
                    try {
                        downloadFileProducer.messageSend(new Producer.MassageContent(MQConstant.ALEVE_FILE_TOPIC, params));
                    } catch (MQException e) {
                        logger.error("发送【导入手续费流水明细(aleve)】MQ失败...");
                    }
                    try {
                        downloadFileProducer.messageSend(new Producer.MassageContent(MQConstant.EVE_FILE_TOPIC, params));
                    } catch (MQException e) {
                        logger.error("发送【导入红包账户流水明细(eve)】MQ失败...");
                    }
                    para.release();
                }
            }

        } catch (Exception e) {
            logger.error("下载ftp文件失败");
        }
    }

    /**
     * aleveLog表数据插入
     * @param list
     */
    @Override
    public void insertAleveLog(List<AleveLog> list){
        for (AleveLog aleve : list) {
//            AleveLogExample example = new AleveLogExample();
//            AleveLogExample.Criteria eveCrt = example.createCriteria();
            aleve.setCreateTime(GetDate.getNowTime());
            aleve.setCreateUserId(1);
            aleve.setDelFlag(0);
            aleve.setUpdFlag(0);
//            eveCrt.andTrannoEqualTo(aleve.getTranno());
            aleveLogMapper.insert(aleve);
        }
    }

    /**
     * eveLog表数据插入
     * @param list
     */
    @Override
    public void insertEveLog(List<EveLog> list){
        for (EveLog eve : list) {
//            EveLogExample example = new EveLogExample();
//            EveLogExample.Criteria eveCrt = example.createCriteria();
            eve.setCreateTime(GetDate.getNowTime());
            eve.setCreateUserId(1);
            eve.setDelFlag(0);
//            eveCrt.andTrannoEqualTo(eve.getTranno());
//            eveCrt.andOrdernoEqualTo(eve.getOrderno());
            eveLogMapper.insert(eve);
        }
    }

    /**
     * aleveErrorLog表数据插入
     * @param aleveErrorLogs
     */
    @Override
    public void insertAleveErrorLog(List<AleveErrorLog> aleveErrorLogs){
        for(AleveErrorLog aleveErrorLog : aleveErrorLogs){
            aleveErrorLogMapper.insert(aleveErrorLog);
        }
    }

    /**
     * 检查导入的ALEVE数据中是否含有利息相关记录
     * @param tranStype
     * @return
     */
    @Override
    public List<AleveLogCustomize> selectAleveReverseList(List<String> tranStype) {
        return this.aleveCustomizeMapper.queryAleveLogListByTranstype(tranStype);
    }

    /**
     * 检索手动冲正数量
     * @param aleveLogCustomize
     * @return
     */
    @Override
    public int countManualReverse(AleveLogCustomize aleveLogCustomize) {

        //通过账号ID获取用户信息
        List<String> userIds = this.selectUserIdsByAccount(aleveLogCustomize.getCardnbr());
        if (userIds.isEmpty()) {
            logger.error("【自动冲正异常】获取用户信息失败、电子账号：" + aleveLogCustomize.getCardnbr());
            return 1;
        } else if (userIds.size() > 1) {
            logger.error("【自动冲正异常】获取用户信息不唯一、电子账号：" + aleveLogCustomize.getCardnbr());
            return 1;
        }

        String userIdStr = userIds.get(0);
        if (StringUtils.isBlank(userIdStr)) {
            logger.error("【自动冲正异常】获取用户信息异常、电子账号：" + aleveLogCustomize.getCardnbr());
            return 1;
        }
        Map<String, Object> param = new HashMap<String, Object>();

        // 用户名
        param.put("userIdSrch", userIdStr);
        // 原交易流水号
        if (aleveLogCustomize.getSeqno() > 0) {
            param.put("seqNoSrch", aleveLogCustomize.getSeqno());
        }
        // 电子账号
        if (StringUtils.isNotEmpty(aleveLogCustomize.getCardnbr())) {
            param.put("accountIdSrch", aleveLogCustomize.getCardnbr());
        }

        int count = manualReverseCustomizeMapper.countManualReverse(param);
        return count;
    }

    /**
     * 冲正处理
     * @param aleveLogCustomize
     * @return
     */
    @Override
    public boolean updateAutoCorretion(AleveLogCustomize aleveLogCustomize) {

        //冲正金额
        BigDecimal total = aleveLogCustomize.getAmount();

        //当前系统时间（数字类型、入库用）
        int nowTime = GetDate.getNowTime10();

        //通过账号ID获取用户信息
        List<String> userIds = this.selectUserIdsByAccount(aleveLogCustomize.getCardnbr());
        if (userIds.isEmpty()) {
            logger.error("【自动冲正异常】获取用户信息失败、电子账号：" + aleveLogCustomize.getCardnbr());
            return false;
        } else if (userIds.size() > 1) {
            logger.error("【自动冲正异常】获取用户信息不唯一、电子账号：" + aleveLogCustomize.getCardnbr());
            return false;
        }

        String userIdStr = userIds.get(0);
        if (StringUtils.isBlank(userIdStr)) {
            logger.error("【自动冲正异常】获取用户信息异常、电子账号：" + aleveLogCustomize.getCardnbr());
            return false;
        }
        Integer userId = Integer.parseInt(userIdStr);

        // 重新获取用户信息
        Account account = accountService.getAccount(userId);
        // 写入收支明细
        AccountList accountList = new AccountList();
        // 账户信息
        // 订单号：空
        //accountList.setNid(GetOrderIdUtils.getOrderId2(user.getUserId()));
        accountList.setUserId(userId);
        accountList.setAmount(total);
        //list表->1收入2支出
        accountList.setType(1);
        accountList.setTrade("auto_reverse");
        //操作识别码 balance余额操作 frost冻结操作 await待收操作
        accountList.setTradeCode("balance");
        accountList.setTotal(account.getTotal());
        accountList.setBalance(account.getBalance());
        accountList.setFrost(account.getFrost());
        accountList.setAwait(account.getAwait());
        accountList.setRepay(account.getRepay());
        accountList.setBankTotal(account.getBankTotal().add(total)); // 银行总资产
        accountList.setBankBalance(account.getBankBalance().add(total)); // 银行可用余额
        accountList.setBankFrost(account.getBankFrost());// 银行冻结金额
        accountList.setBankWaitCapital(account.getBankWaitCapital());// 银行待还本金
        accountList.setBankWaitInterest(account.getBankWaitInterest());// 银行待还利息
        accountList.setBankAwaitCapital(account.getBankAwaitCapital());// 银行待收本金
        accountList.setBankAwaitInterest(account.getBankAwaitInterest());// 银行待收利息
        accountList.setBankAwait(account.getBankAwait());// 银行待收总额
        accountList.setBankInterestSum(account.getBankInterestSum()); // 银行累计收益
        accountList.setBankInvestSum(account.getBankInvestSum());// 银行累计投资
        accountList.setBankWaitRepay(account.getBankWaitRepay());// 银行待还金额
        accountList.setPlanBalance(account.getPlanBalance());//汇计划账户可用余额
        accountList.setPlanFrost(account.getPlanFrost());
        accountList.setSeqNo(aleveLogCustomize.getSeqno().toString());
        accountList.setTxDate(nowTime);
        accountList.setTxTime(nowTime);
        accountList.setAccountId(aleveLogCustomize.getCardnbr());
        accountList.setCreateTime(GetDate.getNowTime());
        accountList.setIsBank(1);
        accountList.setWeb(0);
        accountList.setCheckStatus(1);// 对账状态0：未对账 1：已对账
        accountList.setTradeStatus(1);// 0失败1成功2失败
        //插入资产明细
        boolean isAccountListFlag = accountListMapper.insertSelective(accountList) > 0 ? true : false;
        if (!isAccountListFlag) {
            logger.error("【自动冲正异常】：插入资产明细失败:" + aleveLogCustomize.getCardnbr() + "----Seqno:" + aleveLogCustomize.getSeqno() + "----CreateTime:" + aleveLogCustomize.getCreateTime());
            return false;
        }
        //用户相应余额增加
        Account newAccount = new Account();

        newAccount.setUserId(userId);// 用户Id
        newAccount.setBankTotal(total); // 累加到账户总资产
        newAccount.setBankBalance(total); // 累加可用余额
        newAccount.setBankBalanceCash(total);// 江西银行可用余额
        //余额恢复
        boolean isAccountUpdateFlag = this.adminAccountCustomizeMapper.updateManualReverseSuccess(newAccount) > 0 ? true : false;
        if (!isAccountUpdateFlag) {
            logger.error("【自动冲正异常】：余额恢复失败:" + aleveLogCustomize.getCardnbr() + "----Seqno:" + aleveLogCustomize.getSeqno() + "----CreateTime:" + aleveLogCustomize.getCreateTime());
            return false;
        }

        //同步冲正后更新处理flg
        if(!this.updateAleveLog(aleveLogCustomize)) {
            logger.error("【自动冲正异常】：aleve数据处理完成字段更新失败:" + aleveLogCustomize.getCardnbr() + "----Seqno:" + aleveLogCustomize.getSeqno() + "----CreateTime:" + aleveLogCustomize.getCreateTime());
            return false;
        }
        return true;
    }

    /**
     * 同步冲正后更新处理flg
     * @param aleveLogCustomize
     * @return
     */
    @Override
    public boolean updateAleveLog(AleveLogCustomize aleveLogCustomize) {

        //处理成功后、该条记录的处理flg变为1
        AleveLogExample example = new AleveLogExample();
        AleveLogExample.Criteria crt = example.createCriteria();
        crt.andIdEqualTo(aleveLogCustomize.getId());
        if (org.apache.commons.lang.StringUtils.isNotBlank(aleveLogCustomize.getCardnbr())) {
            crt.andCardnbrEqualTo(aleveLogCustomize.getCardnbr());
        }
        if (null != aleveLogCustomize.getSeqno()) {
            crt.andSeqnoEqualTo(aleveLogCustomize.getSeqno());
        }
        if (null != aleveLogCustomize.getCreateTime()) {
            crt.andCreateTimeEqualTo(aleveLogCustomize.getCreateTime());
        }
        //修改更新时间、处理flg
        aleveLogCustomize.setUpdateTime(GetDate.getNowTime());
        if (null != aleveLogCustomize.getCreateUserId()) {
            aleveLogCustomize.setUpdateUserId(aleveLogCustomize.getCreateUserId());
        } else {
            //无创建用户id记录的数据默认更新为1
            aleveLogCustomize.setUpdateUserId(1);
        }
        aleveLogCustomize.setUpdFlag(1);
        if (this.aleveLogMapper.updateByExampleSelective(aleveLogCustomize, example) > 0 ? false : true ) {
            return false;
        }
        return true;
    }
    
    private int countAleveByExample(String beforeDay){
        AleveLogExample example = new AleveLogExample();
        AleveLogExample.Criteria criteria = example.createCriteria();
        criteria.andCreateDayEqualTo(beforeDay);
        return aleveLogMapper.countByExample(example);
    }

    private int countEveByExample(String beforeDay){
        EveLogExample example = new EveLogExample();
        EveLogExample.Criteria criteria = example.createCriteria();
        criteria.andCreateDayEqualTo(beforeDay);
        return eveLogMapper.countByExample(example);
    }

    /**
     * 通过开户Id获取用户信息
     *
     * @param accountId
     * @return
     */
    public List<String> selectUserIdsByAccount(String accountId){
        Map<String, Object> param = new HashMap<String, Object>();
        // 原交易流水号
        param.put("accountId", accountId);
        List<String> userIds = manualReverseCustomizeMapper.selectUserIdsByAccount(param);
        return userIds;
    }
}
