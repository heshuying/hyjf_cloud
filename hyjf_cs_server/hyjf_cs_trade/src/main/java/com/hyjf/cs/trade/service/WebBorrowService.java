/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.util.Map;

/**
 * @author zhangqingqing
 * @version WebService, v0.1 2018/6/5 9:48
 */
public interface WebBorrowService {
    /**
     * 构建银行调用数据
     * @param user
     * @param transAmt
     * @param cardNo
     * @param payAllianceCode
     * @param clientPc
     * @param channelPc
     * @param ip
     * @return
     */
    BankCallBean getUserBankWithdrawView(UserVO user, String transAmt, String cardNo, String payAllianceCode, String clientPc, String channelPc, String ip);

    /**
     * 提现银行同步调用数据处理
     * @param bean
     * @param isSuccess
     * @param wifee
     * @param withdrawmoney
     * @return
     */
    Map<String,String> userBankWithdrawReturn(BankCallBean bean, String isSuccess, String wifee, String withdrawmoney);

    /**
     * 提现银行异步调用数据处理
     * @param bean
     * @param params
     * @return
     */
    JSONObject handlerAfterCash(BankCallBean bean, Map<String,String> params);

    /**
     * 根据userId获取用户信息
     * @param userId
     * @return
     */
    UserVO getUserByUserId(Integer userId);
}
