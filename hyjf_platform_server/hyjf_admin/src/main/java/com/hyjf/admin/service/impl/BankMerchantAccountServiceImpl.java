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
import com.hyjf.common.bank.LogAcqResBean;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version BankMerchantAccountServiceImpl, v0.1 2018/7/9 16:11
 */
@Service
public class BankMerchantAccountServiceImpl implements BankMerchantAccountService {

    @Autowired
    AmTradeClient amTradeClient;

    @Autowired
    AmConfigClient amConfigClient;

    @Autowired
    CsMessageClient csMessageClient;

    @Autowired
    SystemConfig systemConfig;

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
        // 同步地址  是否跳转到前端页面
        String retUrl = systemConfig.getAdminFrontHost() +"/admin/openError"+"?logOrdId="+bean.getLogOrderId();
        String successUrl = systemConfig.getAdminFrontHost() +"/admin/openSuccess";
        // 异步调用路
        String bgRetUrl = systemConfig.getAdminHost() + CommonSoaUtils.REQUEST_MAPPING
                + "/passwordBgreturn";
        // 消息类型
        bean.setTxCode(BankCallConstant.TXCODE_PASSWORD_SET);
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
        // 商户私有域，存放开户平台,用户userId
        LogAcqResBean acqRes = new LogAcqResBean();
        acqRes.setUserId(40);
        bean.setLogAcqResBean(acqRes);
        // 操作者ID
        bean.setLogUserId(String.valueOf(40));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_PASSWORDSET);
        bean.setLogOrderId(GetOrderIdUtils.getUsrId(40));
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


}
