/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service;

import com.hyjf.pay.lib.bank.bean.BankCallBean;

/**
 * @author zhangqingqing
 * @version AppUserService, v0.1 2018/5/30 16:50
 */
public interface AppUserService {

    /**
     * app、wechat授权自动债转、投资同步回调
     * @param token
     * @param bean
     * @param userAutoType
     * @param sign
     * @param isSuccess
     * @return
     */
    String userAuthCreditReturn(String token, BankCallBean bean, String userAutoType, String sign, String isSuccess);
}
