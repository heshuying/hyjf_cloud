/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.task.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.config.SystemConfig;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.AleveLogCustomize;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.front.account.AccountService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.trade.service.task.AleveLogFileService;
import com.hyjf.am.trade.utils.FileUtil;
import com.hyjf.am.trade.utils.FtpUtil;
import com.hyjf.am.trade.utils.SFTPParameter;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author wangjun
 * @version AleveLogFileServiceImpl, v0.1 2018/6/25 10:09
 */
@Service
public class AleveLogFileServiceImpl extends BaseServiceImpl implements AleveLogFileService {

    @Autowired
    private AccountService accountService;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    private CommonProducer commonProducer;

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
        FileUtil.createDir(localDir+"/"+date);
        String filePath = beforeYear+"/"+beforeMonth+"/"+beforeDay;
        para.downloadPath =systemConfig.getFtpDownloadPath()+ filePath;//ftp服务器文件目录
        para.savePath =localDir+"/"+date;
        String beforeDate = DateUtils.getBeforeDate();//当前前时间前一天的日期yyyyMMdd
        Integer countsAleve = this.countAleveByExample(beforeDate);
        Integer countsEve = this.countEveByExample(beforeDate);

        try {
            if(countsAleve==0 && countsEve==0){
                //删除前一天的文件目录
                FileUtil.deltree(localDir+"/"+beforeDate);
                if(!FtpUtil.downloadFiles(para)){
                    logger.error("下载ftp文件失败");
                }else{
                    File dir = new File(para.savePath);
                    // 只有aleve跟eve两个文件都下载成功才发MQ
                    if(dir.listFiles().length == 2){
                        logger.info("aleve与eve文件下载成功，开始发送MQ");
                        JSONObject params = new JSONObject();
                        params.put("status", "1");
                        params.put("savePath", para.savePath);
                        params.put("filePathEve", systemConfig.getEveFileName());
                        params.put("filePathAleve", systemConfig.getAleveFileName());
                        params.put("dualDate",beforeDate);
                        params.put("isBOA","1");
                        try {
                            commonProducer.messageSend(new MessageContent(MQConstant.ALEVE_FILE_TOPIC, UUID.randomUUID().toString(), params));
                        } catch (MQException e) {
                            logger.error("发送【导入手续费流水明细(aleve)】MQ失败...");
                        }
                        try {
                            commonProducer.messageSend(new MessageContent(MQConstant.EVE_FILE_TOPIC, UUID.randomUUID().toString(),params));
                        } catch (MQException e) {
                            logger.error("发送【导入红包账户流水明细(eve)】MQ失败...");
                        }
                    } else {
                        logger.info("下载文件失败或者不全。下载成功文件数：{}", dir.listFiles().length);
                    }
                    para.release();
                }
            }

        } catch (Exception e) {
            logger.error("下载ftp文件失败");
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
     * 自动冲正
     * @param aleveLogCustomizeList
     */
    @Override
    public void updateAutoCorretion(List<AleveLogCustomize> aleveLogCustomizeList){
        //循环遍历冲正数据
        for (AleveLogCustomize aleveLogCustomize : aleveLogCustomizeList) {
            if (!"1".equals(aleveLogCustomize.getRevind().toString())) {
                //处理完成flg变为1下次处理不再抽出
                if(!this.updateAleveLog(aleveLogCustomize)) {
                    logger.error("【自动冲正异常】：非冲正flg数据处理完成字段更新失败，银行账号：" + aleveLogCustomize.getCardnbr() + "----Seqno:" + aleveLogCustomize.getSeqno() + "----CreateTime:" + aleveLogCustomize.getCreateTime());
                    continue;
                }
                //非冲正交易的场合处理下一条
                logger.info("【自动冲正】非冲正交易，银行账号：" + aleveLogCustomize.getCardnbr());
                continue;
            }
            //白名单校验订单号+用户名存在的情况不再自动冲正
            boolean isExists = this.countManualReverse(aleveLogCustomize) > 0 ? true : false;
            if (isExists) {
                //处理完成flg变为1下次处理不再抽出
                if(!this.updateAleveLog(aleveLogCustomize)) {
                    logger.error("【自动冲正异常】：手动冲正数据处理完成字段更新失败，银行账号：" + aleveLogCustomize.getCardnbr() + "----Seqno:" + aleveLogCustomize.getSeqno() + "----CreateTime:" + aleveLogCustomize.getCreateTime());
                    continue;
                }
                //白名单手动冲正的数据不再处理
                logger.info("【自动冲正】白名单数据不再处理，银行账号：" + aleveLogCustomize.getCardnbr());
                continue;
            }
            //冲正出错的情况打印log处理下一条
            if (!this.updateAutoCorretion(aleveLogCustomize)) {
                logger.error("【自动冲正异常】用户资产/余额处理失败，银行账号：" + aleveLogCustomize.getCardnbr());
            }
        }
    }

    /**
     * 检索手动冲正数量
     * @param aleveLogCustomize
     * @return
     */
    private int countManualReverse(AleveLogCustomize aleveLogCustomize) {
        //通过账号ID获取用户信息
        //原代码使用的是hyjf_bank_open_account跟huiyingdai_users表查询，现在牵扯到跨库，使用ht_account与ht_r_user
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
    private boolean updateAutoCorretion(AleveLogCustomize aleveLogCustomize) {
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
        accountList.setBankInvestSum(account.getBankInvestSum());// 银行累计出借
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
    private boolean updateAleveLog(AleveLogCustomize aleveLogCustomize) {

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

    @Override
    public Integer countAleveByExample(String beforeDay){
        AleveLogExample example = new AleveLogExample();
        AleveLogExample.Criteria criteria = example.createCriteria();
        criteria.andCreateDayEqualTo(beforeDay);
        return aleveLogMapper.countByExample(example);
    }

    @Override
    public Integer countEveByExample(String beforeDay){
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
    private List<String> selectUserIdsByAccount(String accountId){
        Map<String, Object> param = new HashMap<String, Object>();
        // 原交易流水号
        param.put("accountId", accountId);
        List<String> userIds = manualReverseCustomizeMapper.selectUserIdsByAccount(param);
        return userIds;
    }

    /**
     * aleveLog表数据插入
     * @param list
     */
    @Override
    public void insertAleveLogByList(List<AleveLog> list){
        aleveCustomizeMapper.insertAleveLogByList(list);
    }

    /**
     * eveLog表数据插入
     * @param list
     */
    @Override
    public void insertEveLogByList(List<EveLog> list){
        aleveCustomizeMapper.insertEveLogByList(list);
    }

    /**
     * aleveErrorLog表数据插入
     * @param list
     */
    @Override
    public void insertAleveErrorLogByList(List<AleveErrorLog> list){
        aleveCustomizeMapper.insertAleveErrorLogByList(list);
    }
}
