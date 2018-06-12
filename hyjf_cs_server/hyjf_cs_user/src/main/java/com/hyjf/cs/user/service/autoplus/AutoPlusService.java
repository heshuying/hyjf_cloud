/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.autoplus;

import com.hyjf.cs.user.beans.AutoPlusRequestBean;
import com.hyjf.cs.user.beans.AutoPlusRetBean;
import com.hyjf.cs.user.beans.BaseMapBean;
import com.hyjf.cs.user.service.BaseService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;

import java.util.Map;

/**
 * @author zhangqingqing
 * @version AutoPlusService, v0.1 2018/6/11 15:38
 */
public interface AutoPlusService extends BaseService {

    /**
     * 授权自动债转、投资
     * @param token
     * @param client  0web 1wechat 2app
     * @param type 1表示投资 2表示债转
     * @param channel
     * @param lastSrvAuthCode
     * @param smsCode
     * @return
     */
    BankCallBean userCreditAuthInves(String token, Integer client, String type, String channel, String lastSrvAuthCode, String smsCode);

    /**
     * app、wechat授权自动债转、投资同步回调
     * @param token
     * @param bean
     * @param userAutoType
     * @param sign
     * @param isSuccess
     * @return
     */
    Map<String,BaseMapBean> userAuthCreditReturn(String token, BankCallBean bean, String userAutoType, String sign, String isSuccess);
    /**
     * web自动投资授权同步回调
     * @param token
     * @param bean
     * @param urlType
     * @param isSuccess
     * @return
     */
    Map<String,String> userAuthReturn(String token, BankCallBean bean, String urlType, String isSuccess);

    /**
     * 异步回调接口
     * @param bean
     * @return
     */
    String userBgreturn(BankCallBean bean);

    Map<String,String> checkParam(AutoPlusRequestBean payRequestBean);

    Map<String,String> getErrorMV(AutoPlusRequestBean payRequestBean, String status);

    BankCallBean apiUserAuth(String type, String smsSeq, AutoPlusRequestBean payRequestBean);

    AutoPlusRetBean userAuthCreditReturn(BankCallBean bean, String callback, String acqRes, String type);

    BankCallResult userAuthInvesBgreturn(BankCallBean bean, String callback, String acqRes);

}
