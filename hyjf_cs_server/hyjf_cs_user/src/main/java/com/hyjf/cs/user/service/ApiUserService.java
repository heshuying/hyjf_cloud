/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service;

import com.hyjf.cs.user.beans.AutoPlusRequestBean;
import com.hyjf.cs.user.beans.AutoPlusRetBean;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;

/**
 * @author zhangqingqing
 * @version ApiUserService, v0.1 2018/5/30 17:46
 */

public interface ApiUserService {

    BankCallBean apiUserAuth(String type, String smsSeq, AutoPlusRequestBean payRequestBean);


    AutoPlusRetBean userAuthCreditReturn(BankCallBean bean, String callback, String acqRes, String type);

    BankCallResult userAuthInvesBgreturn(BankCallBean bean, String callback, String acqRes);
}
