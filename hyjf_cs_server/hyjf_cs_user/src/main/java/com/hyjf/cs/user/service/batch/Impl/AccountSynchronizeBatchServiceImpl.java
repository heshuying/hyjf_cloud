/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.batch.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.AccountMobileSynchRequest;
import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.user.AccountMobileSynchVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.service.batch.AccountSynchronizeBatchService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 * @author wangjun
 * @version AccountSynchronizeBatchServiceImpl, v0.1 2018/6/22 13:41
 */
@Service
public class AccountSynchronizeBatchServiceImpl implements AccountSynchronizeBatchService {
    private static final Logger logger = LoggerFactory.getLogger(AccountSynchronizeBatchServiceImpl.class);

    @Autowired
    AmUserClient amUserClient;

    @Autowired
    AmConfigClient amConfigClient;

    //机构代码
    @Value("${hyjf.bank.instcode}")
    private String instCode;

    //银行代码
    @Value("${hyjf.bank.bankcode}")
    private String bankCode;


    /**
     * 同步银行卡号
     */
    @Override
    public void accountSynchronize(){
        try {
            List<AccountMobileSynchVO> accountMobileSynches = amUserClient.searchAccountMobileSynch("2");
            if(CollectionUtils.isEmpty(accountMobileSynches)){
                logger.info("未查询到同步银行卡号数据，同步银行卡号(每日)定时任务结束....");
                return;
            }
            for (AccountMobileSynchVO accountMobileSynchVO : accountMobileSynches) {
                // 同步银行卡号
                this.bankCardSynchronize(accountMobileSynchVO);
            }
        }catch(Exception e){
            throw new RuntimeException("查询同步银行卡号异常", e);
        }
    }

    @Override
    public void mobileSychronize() {
        try {
            List<AccountMobileSynchVO> accountMobileAynches = amUserClient.searchAccountMobileSynch("1");
            for (AccountMobileSynchVO accountMobileAynch : accountMobileAynches) {
                // 同步手机号
                updateMobile(accountMobileAynch);
            }
        }catch(Exception e){
            throw new RuntimeException("查询同步手机号异常");        }
    }


    public boolean bankCardSynchronize(AccountMobileSynchVO accountMobileSynchVO) {
        Integer userId = accountMobileSynchVO.getUserId();
        String account = accountMobileSynchVO.getAccount();
        String respCode = "";
        //查询次数加一
        Integer searchtime = accountMobileSynchVO.getSearchtime()+1;
        accountMobileSynchVO.setSearchtime(searchtime);
        try {
            BankOpenAccountVO bankOpenAccountVO = amUserClient.selectById(userId);
            if(bankOpenAccountVO != null){
                UserVO user = amUserClient.findUserById(userId);
                // 银行接口类赋值
                BankCallBean bean = new BankCallBean();
                bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
                bean.setTxCode(BankCallMethodConstant.TXCODE_CARD_BIND_DETAILS_QUERY);
                bean.setInstCode(instCode);// 机构代码
                bean.setBankCode(bankCode);// 银行代码
                bean.setTxDate(GetOrderIdUtils.getTxDate());// 交易日期
                bean.setTxTime(GetOrderIdUtils.getTxTime());// 交易时间
                bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号6位
                bean.setChannel(BankCallConstant.CHANNEL_APP);// 交易渠道
                bean.setAccountId(bankOpenAccountVO.getAccount());// 存管平台分配的账号
                bean.setState("1"); // 查询状态 0-所有（默认） 1-当前有效的绑定卡
                bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
                bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
                bean.setLogRemark("绑卡关系查询");
                bean.setLogUserId(String.valueOf(userId));
                // 调用银行接口
                BankCallBean bankCallBean = BankCallUtils.callApiBg(bean);
                respCode = bankCallBean == null ? "" : bankCallBean.getRetCode();

                // 如果接口调用成功
                if (BankCallConstant.RESPCODE_SUCCESS.equals(respCode)) {
                    String cardNo ="";
                    String txnDate = "";
                    String txnTime = "";
                    String usrCardInfolist = bankCallBean.getSubPacks();
                    JSONArray array = JSONObject.parseArray(usrCardInfolist);
                    if (array != null && array.size() != 0) {
                        JSONObject obj = array.getJSONObject(0);
                        cardNo=obj.getString("cardNo");
                        txnDate=obj.getString("txnDate");
                        txnTime=obj.getString("txnTime");
                    }
                    //初始化更新类
                    AccountMobileSynchRequest accountMobileSynchRequest = new AccountMobileSynchRequest();
                    if(!StringUtils.equals(cardNo,account)){
                        accountMobileSynchRequest.setUpdateFlag(1);

                        BankCardRequest bank = new BankCardRequest();
                        bank.setUserId(bankOpenAccountVO.getUserId());
                        bank.setUserName(user.getUsername());
                        bank.setStatus(1);// 默认都是1
                        bank.setCardNo(cardNo);
                        // 根据银行卡号查询银行ID
                        String bankId = amConfigClient.queryBankIdByCardNo(cardNo);
                        logger.info(bankId + "==>" + "bankId！");
                        bank.setBankId(!StringUtils.isNotBlank(bankId) ? 0 : Integer.valueOf(bankId));
                        BanksConfigVO banksConfigVO = amConfigClient.getBankNameByBankId(bankId);
                        if(banksConfigVO != null){
                            bank.setBank(banksConfigVO.getBankName());
                        }
                        // 银行联号
                        String payAllianceCode = "";
                        // 调用江西银行接口查询银行联号
                        BankCallBean payAllianceCodeQueryBean = this.payAllianceCodeQuery(cardNo, userId);
                        if (payAllianceCodeQueryBean != null && BankCallConstant.RESPCODE_SUCCESS.equals(payAllianceCodeQueryBean.getRetCode())) {
                            payAllianceCode = payAllianceCodeQueryBean.getPayAllianceCode();
                        }
                        // 如果此时银行联号还是为空,调用本地查询银行联号
                        if (StringUtils.isBlank(payAllianceCode)) {
                            BanksConfigVO allianceCodeOfBanksConfig = amConfigClient.getBanksConfigByBankId(bankId);
                            if(allianceCodeOfBanksConfig!=null){
                                payAllianceCode = allianceCodeOfBanksConfig.getPayAllianceCode();
                            } else{
                                payAllianceCode="";
                            }
                        }
                        bank.setPayAllianceCode(payAllianceCode);
                        SimpleDateFormat sdf = GetDate.yyyymmddhhmmss;
                        bank.setCreateTime(sdf.parse(txnDate + txnTime));
                        bank.setCreateUserId(userId);

                        accountMobileSynchVO.setNewAccount(cardNo);
                        accountMobileSynchVO.setStatus(1);

                        accountMobileSynchRequest.setBankCardRequest(bank);
                        accountMobileSynchRequest.setAccountMobileSynchVO(accountMobileSynchVO);

                        return amUserClient.updateAccountMobileSynch(accountMobileSynchRequest);
                    }else{
                        accountMobileSynchRequest.setUpdateFlag(0);
                        accountMobileSynchRequest.setAccountMobileSynchVO(accountMobileSynchVO);
                        return amUserClient.updateAccountMobileSynch(accountMobileSynchRequest);
                    }
                }else{
                    logger.error("接口查询银行卡号异常，返回错误码：{}，用户ID：{}", respCode, userId);
                }
            }
        } catch (Exception e) {
            logger.error("更新用户银行卡信息失败----userId:" + userId, e);
        }
        return false;
    }


    public boolean updateMobile(AccountMobileSynchVO accountMobileAynch) throws Exception {
        AccountMobileSynchRequest accountMobileSynchRequest = new AccountMobileSynchRequest();
        Integer userId = accountMobileAynch.getUserId();
        String accountId = accountMobileAynch.getAccountid();
        Integer id = accountMobileAynch.getId();
        Integer searchtime = accountMobileAynch.getSearchtime()+1;
        accountMobileAynch.setSearchtime(searchtime);
        // 根据用户ID查询用户当前手机号
        UserVO users = amUserClient.findUserById(userId);
        if (users == null) {
            throw new Exception("获取用户信息失败,用户ID:" + userId);
        }
        String mobile = accountMobileAynch.getMobile();
        // 调用银行接口查询电子账户手机号
        // 获取共同参数
        String channel = BankCallConstant.CHANNEL_PC;
        String orderId = GetOrderIdUtils.getOrderId2(userId);
        String orderDate = GetOrderIdUtils.getOrderDate();
        String txDate = GetOrderIdUtils.getTxDate();
        String txTime = GetOrderIdUtils.getTxTime();
        String seqNo = GetOrderIdUtils.getSeqNo(6);
        BankCallBean bean = new BankCallBean();
        bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        bean.setTxCode(BankCallConstant.TXCODE_MOBILE_MAINTAINACE);// 消息类型
        bean.setInstCode(instCode);// 机构代码
        bean.setBankCode(bankCode);
        bean.setTxDate(txDate);
        bean.setTxTime(txTime);
        bean.setSeqNo(seqNo);
        bean.setChannel(channel);
        bean.setAccountId(accountId);
        bean.setOption("0");
        bean.setLogUserId(userId+"");
        bean.setLogOrderId(orderId);
        bean.setLogOrderDate(orderDate);
        bean.setLogRemark("电子账户手机号查询");
        try {
            BankCallBean resultBean = BankCallUtils.callApiBg(bean);
            if (Validator.isNotNull(resultBean)) {
                String retCode = StringUtils.isNotBlank(resultBean.getRetCode()) ? resultBean.getRetCode() : "";
                // 如果返回是成功
                if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
                    // 获取银行返回的手机号
                    String bankMobile = resultBean.getMobile();
                    // 如果本地记录的手机号跟银行的手机号不一致,更新本地记录的手机号
                    if (!bankMobile.equals(mobile)) {
                        users.setMobile(bankMobile);
                        accountMobileAynch.setNewMobile(bankMobile);
                        accountMobileAynch.setStatus(1);
                        accountMobileSynchRequest.setAccountMobileSynchVO(accountMobileAynch);
                        boolean c = this.amUserClient.updateByPrimaryKey(users);
                        boolean b = this.amUserClient.updateMobileSynch(accountMobileSynchRequest);
                        //amUserClient.updateAccountMobileSynch(accountMobileSynchRequest);
                        if(b&c){
                            return true;
                        }else {
                            return false;
                        }
                    } else {
                        accountMobileSynchRequest.setAccountMobileSynchVO(accountMobileAynch);
                        // 如果手机号相同,不用同步,更新查询次数
                        return this.amUserClient.updateMobileSynch(accountMobileSynchRequest) ;
                    }
                }
            }
            logger.error("==>" + userId + "==>" + "接口查询银行手机号异常！");

        } catch (Exception e) {
            throw new RuntimeException("更新用户银行卡信息失败:" + userId);
        }

        return false;
    }

    /**
     * 银联行号查询
     * @param cardNo
     * @param userId
     * @return
     */
    private BankCallBean payAllianceCodeQuery(String cardNo,Integer userId) {
        BankCallBean bean = new BankCallBean();
        String channel = BankCallConstant.CHANNEL_PC;
        String orderDate = GetOrderIdUtils.getOrderDate();
        String txDate = GetOrderIdUtils.getTxDate();
        String txTime = GetOrderIdUtils.getTxTime();
        String seqNo = GetOrderIdUtils.getSeqNo(6);
        bean.setVersion(BankCallConstant.VERSION_10);// 版本号
        bean.setTxCode(BankCallConstant.TXCODE_PAY_ALLIANCE_CODE_QUERY);// 交易代码
        bean.setTxDate(txDate);
        bean.setTxTime(txTime);
        bean.setSeqNo(seqNo);
        bean.setChannel(channel);
        bean.setAccountId(cardNo);
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        bean.setLogOrderDate(orderDate);
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogRemark("联行号查询");
        bean.setLogClient(0);
        return BankCallUtils.callApiBg(bean);
    }

}
