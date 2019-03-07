/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.CsMessageClient;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.service.BankMerchantAccountService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BankMerchantAccountResponse;
import com.hyjf.am.resquest.admin.BankMerchantAccountListRequest;
import com.hyjf.am.vo.admin.BankMerchantAccountInfoVO;
import com.hyjf.am.vo.admin.BankMerchantAccountVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.account.BankMerchantAccountListVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.GetterUtil;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.*;
import com.hyjf.pay.lib.chinapnr.util.ChinaPnrConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version BankMerchantAccountServiceImpl, v0.1 2018/7/9 16:11
 */
@Service
public class BankMerchantAccountServiceImpl implements BankMerchantAccountService {

    private static final Logger logger = LoggerFactory.getLogger(BankMerchantAccountServiceImpl.class);
    private static final String REQUEST_MAPPING = "/bank/merchant/account";
    private static final String OPT_SUCCESS_URL = "/result/success";
    private static final String OPT_ERROR_URL = "/result/fail";

    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    private AmConfigClient amConfigClient;
    @Autowired
    private CsMessageClient csMessageClient;
    @Autowired
    private SystemConfig systemConfig;

    @Override
    public BankMerchantAccountResponse selectBankMerchantAccount(BankMerchantAccountListRequest form) {
        return amTradeClient.selectBankMerchantAccount(form);
    }

    @Override
    public BankMerchantAccountVO getBankMerchantAccount(String accountCode) {
        BankMerchantAccountVO result = amTradeClient.getBankMerchantAccount(accountCode);
        return result;
    }

    @Override
    public BankMerchantAccountInfoVO getBankMerchantAccountInfoByCode(String accountCode) {
        BankMerchantAccountInfoVO result = amTradeClient.getBankMerchantAccountInfoByCode(accountCode);
        return result;
    }

    @Override
    public AdminResult setPassword(String accountCode) {
        AdminResult result = new AdminResult();
        BankMerchantAccountVO bankMerchantAccount = this.getBankMerchantAccount(accountCode);
        // 判断用户是否设置过交易密码
        Integer passwordFlag = bankMerchantAccount.getIsSetPassword();
        CheckUtil.check(passwordFlag != 1, MsgEnum.ERR_TRADE_PASSWORD_ALREADY_SET);
        BankMerchantAccountInfoVO info=this.getBankMerchantAccountInfoByCode(accountCode);
        String userName="";
        String mobile="";
        String idNo="";
        String idType="";
        if(info!=null){
            //服务费账户
            mobile=info.getMobile();
            idNo=info.getIdNo();
            idType=info.getIdType();
            userName = info.getAccountName();
            // 用户名
        } else{
            throw new CheckException("1","账户不存在");
        }
        // 调用设置密码接口
        BankCallBean bean = new BankCallBean();
        bean.setLogOrderId(GetOrderIdUtils.getUsrId(40));
        // 同步地址  是否跳转到前端页面
        String retUrl = systemConfig.getAdminFrontHost() +OPT_ERROR_URL+"?logOrdId="+bean.getLogOrderId()+"&type=pwd";
        String successUrl = systemConfig.getAdminFrontHost() +OPT_SUCCESS_URL+"?type=pwd";
        // 异步调用路
        String bgRetUrl = systemConfig.getAdminHost() + REQUEST_MAPPING
                + "/passwordBgreturn";
        // 消息类型
        bean.setVersion(BankCallConstant.VERSION_10);
        bean.setTxCode(BankCallConstant.TXCODE_PASSWORD_RESET_PAGE);
        bean.setInstCode(systemConfig.getBANK_INSTCODE());// 机构代码
        bean.setBankCode(systemConfig.getBANK_BANKCODE());
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        bean.setChannel(BankCallConstant.CHANNEL_PC);
        bean.setIdType(idType);
        bean.setIdNo(idNo);
        bean.setName(userName);
        // 电子账号
        bean.setAccountId(accountCode);
        bean.setMobile(mobile);
        // 页面同步返回 URL
        bean.setRetUrl(retUrl);
        bean.setSuccessfulUrl(successUrl);
        // 页面异步返回URL(必须)
        bean.setNotifyUrl(bgRetUrl);
        // 操作者ID
        bean.setLogUserId(String.valueOf(40));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_PASSWORDRESETPAGE);
        // 跳转到江西银行画面
        Map<String, Object> map = new HashMap<>();
        try {
            map = BankCallUtils.callApiMap(bean);
        } catch (Exception e) {
            throw new CheckException("1","调用银行接口失败!");
        }
        result.setData(map);
        return result;
    }

    @Override
    public String getBankRetMsg(String retCode) {
        BankReturnCodeConfigVO vo = amConfigClient.getBankReturnCodeConfig(retCode);
        if (vo == null) {
            return Response.ERROR_MSG;
        }
        return StringUtils.isNotBlank(vo.getRetMsg()) ? vo.getRetMsg() : Response.ERROR_MSG;
    }

    @Override
    public void updateBankMerchantAccountIsSetPassword(String accountId, int flag) {
        amTradeClient.updateBankMerchantAccountIsSetPassword(accountId,flag);
    }

    @Override
    public String getFiledMess(String logOrdId) {
        //根据ordid获取retcode
        String retCode = csMessageClient.getRetCode(logOrdId);
        if (retCode==null){
            return "未知错误";
        }
        //根据retCode获取retMsg
        String retMsg = this.getBankRetMsg(retCode);
        return retMsg;

    }

    @Override
    public AdminResult resetPassword(String accountCode) {
        AdminResult result = new AdminResult();
        BankMerchantAccountVO bankMerchantAccount = this.getBankMerchantAccount(accountCode);
        // 判断用户是否设置过交易密码
        Integer passwordFlag = bankMerchantAccount.getIsSetPassword();
        CheckUtil.check(passwordFlag != 0, MsgEnum.ERR_TRADE_PASSWORD_NOT_SET);
        BankMerchantAccountInfoVO info=this.getBankMerchantAccountInfoByCode(accountCode);
        String userName="";
        String mobile="";
        String idNo="";
        String idType="";
        if(info!=null){
            //服务费账户
            mobile=info.getMobile();
            idNo=info.getIdNo();
            idType=info.getIdType();
            //用户名
            userName = info.getAccountName();
        } else{
            throw new CheckException("1","账户不存在");
        }
     // 调用设置密码接口
        BankCallBean bean = new BankCallBean();
        bean.setLogOrderId(GetOrderIdUtils.getUsrId(40));
        // 同步地址  是否跳转到前端页面
        String retUrl = systemConfig.getAdminFrontHost() +OPT_ERROR_URL+"?logOrdId="+bean.getLogOrderId()+"&type=pwd";
        String successUrl = systemConfig.getAdminFrontHost() +OPT_SUCCESS_URL+"?type=pwd";
        // 异步调用路
        String bgRetUrl = systemConfig.getAdminHost() + REQUEST_MAPPING
                + "/resetPasswordBgreturn";
        // 消息类型(用户开户)
        bean.setTxCode(BankCallConstant.TXCODE_PASSWORD_RESET_PAGE);
        bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        bean.setChannel(BankCallConstant.CHANNEL_PC);
        bean.setInstCode(systemConfig.getBANK_INSTCODE());// 机构代码
        bean.setBankCode(systemConfig.getBANK_BANKCODE());
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        bean.setIdType(idType);
        bean.setIdNo(idNo);
        bean.setName(userName);
        // 电子账号
        bean.setAccountId(accountCode);
        bean.setMobile(mobile);
        // 页面同步返回 URL
        bean.setRetUrl(retUrl);
        bean.setSuccessfulUrl(successUrl);
        // 页面异步返回URL(必须)
        bean.setNotifyUrl(bgRetUrl);
        // 商户私有域，存放开户平台,用户userId
/*        LogAcqResBean acqRes = new LogAcqResBean();
        acqRes.setUserId(40);
        bean.setLogAcqResBean(acqRes);*/
        // 操作者ID
        bean.setLogUserId(String.valueOf(40));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_PASSWORDRESETPAGE);

        // 跳转到江西银行画面
        Map<String, Object> map = new HashMap<>();
        try {
            map = BankCallUtils.callApiMap(bean);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CheckException("1","调用银行接口失败!");
        }
        result.setData(map);
        return result;
    }


    /**
     * 充值前处理
     * @author zhangyk
     * @date 2018/8/7 16:23
     */
    @Override
    public int updateBeforeRecharge(BankCallBean bean, Map<String, String> params, BankMerchantAccountVO bankMerchantAccount) {
        int ret = 0;
        String ordId = bean.getLogOrderId() == null ? bean.get(ChinaPnrConstant.PARAM_ORDID) : bean.getLogOrderId(); // 订单号

        Integer count = amTradeClient.getBankMerchantAccountListByOrderId(ordId);
        if (null != count && count >0) {
            return ret;
        }
        BigDecimal money = new BigDecimal(bean.getTxAmount()); //充值金额
        Integer userId = GetterUtil.getInteger(params.get("userId")); // 用户ID
        BankMerchantAccountListVO bankMerchantAccountList = new BankMerchantAccountListVO();
        bankMerchantAccountList.setOrderId(ordId);
        bankMerchantAccountList.setUserId(userId);
        bankMerchantAccountList.setAccountId(bean.getAccountId());
        bankMerchantAccountList.setAmount(money);
        bankMerchantAccountList.setBankAccountCode(bean.getAccountId());
        bankMerchantAccountList.setBankAccountBalance(bankMerchantAccount.getAccountBalance());
        bankMerchantAccountList.setTransType(CustomConstants.BANK_MER_TRANS_TYPE_MANUAL);
        bankMerchantAccountList.setType(CustomConstants.BANK_MER_TYPE_INCOME);
        bankMerchantAccountList.setStatus(CustomConstants.BANK_MER_TRANS_UNDERWAY);
        bankMerchantAccountList.setSeqNo(bean.getSeqNo());
        bankMerchantAccountList.setTxDate(Integer.parseInt(bean.getTxDate()));
        bankMerchantAccountList.setTxTime(Integer.parseInt(bean.getTxTime()));
        bankMerchantAccountList.setCreateUserId(userId);
        bankMerchantAccountList.setCreateUserName("0");
        bankMerchantAccountList.setCreateTime(new Date());
        bankMerchantAccountList.setUpdateUserId(userId);
        bankMerchantAccountList.setUpdateUserName("0");
        bankMerchantAccountList.setBankAccountFrost(bankMerchantAccount.getFrost());
        bankMerchantAccountList.setUpdateTime(new Date());
        bankMerchantAccountList.setRemark("红包账户或手续费账户充值");
        ret += amTradeClient.insertBankMerchantAccountList(bankMerchantAccountList);
        return ret;
    }


    /**
     * 回调处理
     * @author zhangyk
     * @date 2018/8/7 18:41
     */
    @Override
    public void handlerAfterRecharge(BankCallBean bean, Map<String, String> params) {
        // 用户ID
        int userId = Integer.parseInt(params.get("userId"));
        String orderId = bean.getLogOrderId() == null ? "" : bean.getLogOrderId(); // 订单号

        Map<String ,Object> reqMap = new HashMap<>();
        reqMap.put("userId",userId);
        reqMap.put(BankCallParamConstant.PARAM_LOGORDERID,orderId);
        reqMap.put(BankCallParamConstant.PARAM_ACCOUNTID,bean.getAccountId());
        reqMap.put(BankCallParamConstant.PARAM_TXAMOUNT,bean.getTxAmount());
        boolean flag = amTradeClient.updateAccountCallbackRecharge(reqMap);
        if (!flag){
            throw new RuntimeException("圈存失败");
        }
    }


    /**
     * 更新圈提和圈存的交易状态为交易失败
     * @author zhangyk
     * @date 2018/8/7 19:29
     */
    @Override
    public void updateBankAccountListFailByOrderId(String orderId) {
        amTradeClient.updateBankAccountListFailByOrderId(orderId);
    }

    @Override
    public int updateBankMerchantAccount(String accountCode, BigDecimal currBalance, BigDecimal balance, BigDecimal frost) {
        BankMerchantAccountVO bankMerchantAccount=new BankMerchantAccountVO();
        bankMerchantAccount.setAccountCode(accountCode);
        bankMerchantAccount.setAccountBalance(currBalance);
        bankMerchantAccount.setAvailableBalance(balance);
        bankMerchantAccount.setFrost(frost);
        bankMerchantAccount.setUpdateTime(new Date());
        return amTradeClient.updateBankMerchantAccountByCode(bankMerchantAccount);
    }

    /**
     * 根据电子账号查询用户在江西银行的可用余额
     * @param userId
     * @param accountId
     * @return
     */
    @Override
    public BigDecimal getBankBalance(Integer userId, String accountId) {
        // 账户可用余额
        BigDecimal balance = BigDecimal.ZERO;
        BankCallBean bean = new BankCallBean();
        // 交易代码
        bean.setTxCode(BankCallMethodConstant.TXCODE_BALANCE_QUERY);
        // 交易渠道
        bean.setChannel(BankCallConstant.CHANNEL_PC);
        // 电子账号
        bean.setAccountId(accountId);
        // 订单号
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        // 订单时间(必须)格式为yyyyMMdd，例如：20130307
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        bean.setLogUserId(String.valueOf(userId));
        // 平台
        bean.setLogClient(0);
        try {
            BankCallBean resultBean = BankCallUtils.callApiBg(bean);
            if (resultBean != null && BankCallStatusConstant.RESPCODE_SUCCESS.equals(resultBean.getRetCode())) {
                balance = new BigDecimal(resultBean.getAvailBal().replace(",", ""));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return balance;
    }



    /**
     * 统一参数校验
     * @author zhangyk
     * @date 2018/8/8 11:40
     */
    @Override
    public String checkParam(String amount, String accountCode) {
        if (StringUtils.isBlank(amount) || amount.equals("0")) {
            return "金额不能为0";
        }
        BigDecimal txAmount = new BigDecimal("0");
        try {
            txAmount = new BigDecimal(amount);
        } catch (Exception e) {
            return "请输入正确的金额";
        }
        if (txAmount.intValue() <= 1) {
            return "金额必须大于1元";
        }
        if (StringUtils.isBlank(accountCode)){
            return "请输入账户号";
        }
        BankMerchantAccountVO bankMerchantAccount =  amTradeClient.getBankMerchantAccount(accountCode);
        if (bankMerchantAccount == null ) {
            return "账户不存在";
        }
        if(bankMerchantAccount.getIsSetPassword()==0){
            return "请先设置交易密码";
        }
        return null;
    }


    /**
     * 提现前处理
     * @author zhangyk
     * @date 2018/8/8 15:37
     */
    @Override
    public int updateBeforeCash(BankCallBean bean, Map<String, String> params, BankMerchantAccountVO bankMerchantAccount) {
        int ret = 0;
        String ordId = bean.getLogOrderId() == null ? bean.get(ChinaPnrConstant.PARAM_ORDID) : bean.getLogOrderId(); // 订单号

        Integer count = amTradeClient.getBankMerchantAccountListByOrderId(ordId);
        if (null != count && count >0) {
            return ret;
        }
        BigDecimal money = new BigDecimal(bean.getTxAmount()); //充值金额
        Integer userId = GetterUtil.getInteger(params.get("userId")); // 用户ID
        BankMerchantAccountListVO bankMerchantAccountList = new BankMerchantAccountListVO();
        bankMerchantAccountList.setOrderId(ordId);
        bankMerchantAccountList.setUserId(userId);
        bankMerchantAccountList.setAccountId(bean.getAccountId());
        bankMerchantAccountList.setAmount(money);
        bankMerchantAccountList.setBankAccountCode(bean.getAccountId());
        bankMerchantAccountList.setBankAccountBalance(bankMerchantAccount.getAccountBalance());
        bankMerchantAccountList.setBankAccountFrost(bankMerchantAccount.getFrost());
        bankMerchantAccountList.setTransType(CustomConstants.BANK_MER_TRANS_TYPE_MANUAL);
        bankMerchantAccountList.setType(CustomConstants.BANK_MER_TYPE_EXPENDITURE);
        bankMerchantAccountList.setStatus(CustomConstants.BANK_MER_TRANS_UNDERWAY);
        bankMerchantAccountList.setSeqNo(bean.getSeqNo());
        bankMerchantAccountList.setTxDate(Integer.parseInt(bean.getTxDate()));
        bankMerchantAccountList.setTxTime(Integer.parseInt(bean.getTxTime()));
        bankMerchantAccountList.setCreateUserId(userId);
        bankMerchantAccountList.setCreateUserName("0");
        bankMerchantAccountList.setCreateTime(new Date());
        bankMerchantAccountList.setUpdateUserId(userId);
        bankMerchantAccountList.setUpdateUserName("0");
        bankMerchantAccountList.setUpdateTime(new Date());
        bankMerchantAccountList.setRemark("红包账户或手续费账户提现");
        ret += amTradeClient.insertBankMerchantAccountList(bankMerchantAccountList);
        return ret;
    }


    /**
     * 提现后回调
     * @author zhangyk
     * @date 2018/8/8 16:38
     */
    @Override
    public void handlerAfterCash(BankCallBean bean, Map<String, String> params) {
        // 用户ID
        int userId = Integer.parseInt(params.get("userId"));
        String orderId = bean.getLogOrderId() == null ? "" : bean.getLogOrderId(); // 订单号

        Map<String ,Object> reqMap = new HashMap<>();
        reqMap.put("userId",userId);
        reqMap.put(BankCallParamConstant.PARAM_LOGORDERID,orderId);
        reqMap.put(BankCallParamConstant.PARAM_ACCOUNTID,bean.getAccountId());
        reqMap.put(BankCallParamConstant.PARAM_TXAMOUNT,bean.getTxAmount());
        reqMap.put(BankCallParamConstant.PARAM_TXFEE,null == bean.getLogAcqResBean() ? "0.00" : bean.getLogAcqResBean().getFee());
        boolean flag = amTradeClient.updateAccountCallbackWithdraw(reqMap);
        if (!flag){
            throw new RuntimeException("圈提失败");
        }
    }
}
