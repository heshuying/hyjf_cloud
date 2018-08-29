/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.recharge;

import com.hyjf.cs.user.bean.UserDirectRechargeRequestBean;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: sunpeikai
 * @version: DirectRechargeService, v0.1 2018/8/28 19:29
 */
public interface DirectRechargeService extends BaseUserService {
    ModelAndView recharge(UserDirectRechargeRequestBean userRechargeRequestBean, HttpServletRequest request);
    ModelAndView pageReturn(HttpServletRequest request, BankCallBean bean);
    BankCallResult bgreturn(HttpServletRequest request, BankCallBean bean);
}
