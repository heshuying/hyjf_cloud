/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.user.beans.AutoPlusRequestBean;
import com.hyjf.cs.user.beans.AutoPlusRetBean;
import com.hyjf.cs.user.beans.BaseResultBean;
import com.hyjf.cs.user.service.UserService;
import com.hyjf.cs.user.util.ClientConstant;
import com.hyjf.cs.user.util.ErrorCodeConstant;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author zhangqq
 * @version ApiUserController, v0.1 2018/5/23 14:38
 */
@Api(value = "api端用户接口")
@RestController
@RequestMapping("/api/user")
public class ApiUserController {
    private static final Logger logger = LoggerFactory.getLogger(ApiUserController.class);

    @Autowired
    UserService userService;

    /**
     * 自动投资授权
     * @param payRequestBean
     * @return
     */
    @RequestMapping(value = "/userAuthInves")
    public ModelAndView userAuthInves(AutoPlusRequestBean payRequestBean){
        ModelAndView modelAndView = new ModelAndView();
        Map<String,String> paramMap = userService.checkParam(payRequestBean);
        if("false".equals(paramMap.get("isSuccess"))){
            modelAndView.addObject("callBackForm",paramMap);
            return modelAndView;
        }
        BankCallBean bean =  userService.apiUserAuth(ClientConstant.INVES_AUTO_TYPE,paramMap.get("smsSeq"),payRequestBean);
        try {
            modelAndView = BankCallUtils.callApi(bean);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("调用银行接口失败！"+e.getMessage());
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE999999,"系统异常！");
            payRequestBean.doNotify(params);
            Map<String,String> resultMap = userService.getErrorMV(payRequestBean, ErrorCodeConstant.STATUS_CE999999);
            modelAndView.addObject("callBackForm",resultMap);
            return modelAndView;
        }
        return modelAndView;
    }

    /**
     * 用户自动债转授权
     * @param payRequestBean
     * @return
     */
    @RequestMapping("/userAuthCredit")
    public ModelAndView userAuthCredit(AutoPlusRequestBean payRequestBean){
        ModelAndView modelAndView = new ModelAndView();
        Map<String,String> paramMap = userService.checkParam(payRequestBean);
        if("false".equals(paramMap.get("isSuccess"))){
            modelAndView.addObject("callBackForm",paramMap);
            return modelAndView;
        }
        BankCallBean bean =  userService.apiUserAuth(ClientConstant.CREDIT_AUTO_TYPE,paramMap.get("smsSeq"),payRequestBean);
        try {
            modelAndView = BankCallUtils.callApi(bean);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("调用银行接口失败！"+e.getMessage());
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE999999,"系统异常！");
            payRequestBean.doNotify(params);
            Map<String,String> autoPlusRetBean = userService.getErrorMV(payRequestBean, ErrorCodeConstant.STATUS_CE999999);
            modelAndView.addObject("callBackForm",autoPlusRetBean);
            return modelAndView;
        }
        return modelAndView;
    }



}
