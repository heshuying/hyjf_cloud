/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.recharge;

import com.hyjf.cs.user.bean.ApiUserRechargeRequestBean;
import com.hyjf.cs.user.bean.ApiUserRechargeResultBean;
import com.hyjf.cs.user.service.BaseUserService;

/**
 * @author: sunpeikai
 * @version: ApiRechargeService, v0.1 2018/8/28 10:40
 */
public interface ApiRechargeService extends BaseUserService {

    /**
     * 短信充值发送短信验证码
     * @auth sunpeikai
     * @param
     * @return
     */
    ApiUserRechargeResultBean sendSms(ApiUserRechargeRequestBean requestBean);
}
