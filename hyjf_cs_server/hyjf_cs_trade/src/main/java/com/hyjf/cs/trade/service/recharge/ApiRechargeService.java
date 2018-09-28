/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.recharge;


import com.hyjf.cs.trade.bean.ApiUserRechargeRequestBean;
import com.hyjf.cs.trade.bean.ApiUserRechargeResultBean;
import com.hyjf.cs.trade.service.BaseTradeService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: sunpeikai
 * @version: ApiRechargeService, v0.1 2018/8/28 10:40
 */
public interface ApiRechargeService extends BaseTradeService {

    /**
     * 短信充值发送短信验证码
     * @auth sunpeikai
     * @param
     * @return
     */
    ApiUserRechargeResultBean sendSms(ApiUserRechargeRequestBean requestBean);

    /**
     * 短信充值
     * @auth sunpeikai
     * @param
     * @return
     */
    ApiUserRechargeResultBean recharge(HttpServletRequest request, ApiUserRechargeRequestBean requestBean);
}
