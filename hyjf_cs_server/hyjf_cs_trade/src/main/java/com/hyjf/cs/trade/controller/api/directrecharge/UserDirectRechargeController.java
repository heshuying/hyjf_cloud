/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.api.directrecharge;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.cs.trade.bean.UserDirectRechargeRequestBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.recharge.DirectRechargeService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author: sunpeikai
 * @version: UserDirectRechargeController, v0.1 2018/8/28 19:23
 */
@Api(value = "api端-用户充值(页面调用)",tags = "api端-用户充值(页面调用)")
@Controller
@RequestMapping(value = "/hyjf-api/server/user/directRechargePage")
public class UserDirectRechargeController extends BaseTradeController {

    @Autowired
    private DirectRechargeService directRechargeService;

    /**
     * 充值页面
     *
     * @param userRechargeRequestBean
     * @param request
     * @return
     */
    @ApiOperation(value = "页面充值")
    @ResponseBody
    @PostMapping(value = "/recharge.do")
    public ModelAndView recharge(@RequestBody UserDirectRechargeRequestBean userRechargeRequestBean, HttpServletRequest request) {
        logger.info("api充值   请求参数  ",userRechargeRequestBean);
        ModelAndView modelAndView = new ModelAndView();
        Map<String,Object> result = directRechargeService.recharge(userRechargeRequestBean, request);
        if (null!=result&&result.get("modelAndView")==null){
            return callbackErrorViewForMap(result);
        }
        if(null!=result) {
             modelAndView = (ModelAndView) result.get("modelAndView");
        }
        return modelAndView;
    }

    /**
     *
     * 同步
     * @author sunss
     * @param request
     * @param bean
     * @return
     */
    @ApiIgnore
    @GetMapping(value = "/directRechargePageReturn")
    public ModelAndView pageReturn(HttpServletRequest request, BankCallBean bean) {
        logger.info("页面充值同步回调start,请求参数为：【" + JSONObject.toJSONString(bean, true) + "】");
        Map<String,Object> result =  directRechargeService.pageReturn(request, bean);
        return callbackErrorViewForMap(result);
    }

    /**
     * 异步回调
     */
    @ApiIgnore
    @ResponseBody
    @PostMapping(value = "/directRechargePageBgreturn")
    public BankCallResult bgreturn(HttpServletRequest request, @RequestBody BankCallBean bean) {
        logger.info("页面充值异步回调start");
        return directRechargeService.bgreturn(request, bean);
    }






}
