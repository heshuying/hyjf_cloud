/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.am.response.admin.BankMerchantAccountResponse;
import com.hyjf.am.resquest.admin.BankMerchantAccountListRequest;
import com.hyjf.am.vo.admin.BankMerchantAccountInfoVO;
import com.hyjf.am.vo.admin.BankMerchantAccountVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.util.Map;

/**
 * @author zhangqingqing
 * @version BankMerchantAccountService, v0.1 2018/7/9 16:10
 */
public interface BankMerchantAccountService {

    BankMerchantAccountResponse selectBankMerchantAccount(BankMerchantAccountListRequest form);

    /**
     * 获取平台子账户信息
     * @param accountCode
     * @return
     */
    BankMerchantAccountVO getBankMerchantAccount(String accountCode);

    BankMerchantAccountInfoVO getBankMerchantAccountInfoByCode(String accountCode);

    /**
     * admin设置交易密码
     * @param accountCode
     * @return
     */
    AdminResult setPassword(String accountCode);

    /**
     * 获取银行返回错误信息
     * @param retCode
     * @return
     */
    String getBankRetMsg(String retCode);

    /**
     * 更新账户信息已设置交易密码
     * @param accountId
     * @param flag
     */
    void updateBankMerchantAccountIsSetPassword(String accountId, int flag);

    /**
     * 查询交易密码失败原因
     * @param logOrdId
     * @return
     */
    String getFiledMess(String logOrdId);

    /**
     * 重置交易密码
     * @param accountCode
     * @return
     */
    AdminResult resetPassword(String accountCode);

    /**
     * 充值前处理
     * @author zhangyk
     * @date 2018/8/7 16:23
     */
    int updateBeforeRecharge(BankCallBean bean, Map<String, String> params,
                             BankMerchantAccountVO bankMerchantAccount);


    /**
     * 回调处理
     * @author zhangyk
     * @date 2018/8/7 18:41
     */
    void handlerAfterRecharge(BankCallBean bean , Map<String,String> params);


    /**
     * 更新圈提和圈存的交易状态为交易失败
     * @author zhangyk
     * @date 2018/8/7 19:29
     */
    void updateBankAccountListFailByOrderId(String orderId);
}
