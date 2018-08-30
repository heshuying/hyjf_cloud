/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.user.directrecharge;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.UserDirectRechargeRequestBean;
import com.hyjf.cs.user.bean.UserDirectRechargeResultBean;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.recharge.DirectRechargeService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: sunpeikai
 * @version: UserDirectRechargeController, v0.1 2018/8/28 19:23
 */
@Controller
@RequestMapping(value = "/server/user/directRechargePage")
public class UserDirectRechargeController extends BaseUserController {

    @Autowired
    private DirectRechargeService directRechargeService;

    /**
     * 充值页面
     *
     * @param userRechargeRequestBean
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/recharge")
    public ModelAndView recharge(@RequestBody UserDirectRechargeRequestBean userRechargeRequestBean, HttpServletRequest request) {
        return directRechargeService.recharge(userRechargeRequestBean, request);
    }

    /**
     *
     * 同步
     * @author sunss
     * @param request
     * @param response
     * @param bean
     * @return
     */
    @RequestMapping(value = "/directRechargePageReturn")
    public ModelAndView pageReturn(HttpServletRequest request, BankCallBean bean) {
        logger.info("页面充值同步回调start,请求参数为：【" + JSONObject.toJSONString(bean, true) + "】");
        return directRechargeService.pageReturn(request, bean);
    }

    /**
     * 异步回调
     */
    @ResponseBody
    @RequestMapping(value = "/directRechargePageBgreturn")
    public BankCallResult bgreturn(HttpServletRequest request, @ModelAttribute BankCallBean bean) {
        logger.info("页面充值异步回调start");
        return directRechargeService.bgreturn(request, bean);
    }






}
