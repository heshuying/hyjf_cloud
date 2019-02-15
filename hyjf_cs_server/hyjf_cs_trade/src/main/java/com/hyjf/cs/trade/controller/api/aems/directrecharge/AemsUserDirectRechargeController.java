package com.hyjf.cs.trade.controller.api.aems.directrecharge;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.cs.trade.bean.AemsUserDirectRechargeRequestBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.aems.directrecharge.AemsUserDirectRechargeService;
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
 * @version AemsUserDirectRechargeController, v0.1 2018/12/6 17:31
 * @Author: Zha Daojian
 */

@Api(value = "api端-AEMS用户充值(页面调用)",tags = "api端-AEMS用户充值(页面调用)")
@Controller
@RequestMapping(value = "/hyjf-api/aems/directRechargePage")
public class AemsUserDirectRechargeController extends BaseTradeController {

    @Autowired
    private AemsUserDirectRechargeService directRechargeService;

    /**
     * 充值页面
     *
     * @param userRechargeRequestBean
     * @param request
     * @return
     */
    @ApiOperation(value = "页面充值")
    @ResponseBody
    @PostMapping(value = "/recharge")
    public ModelAndView recharge(@RequestBody AemsUserDirectRechargeRequestBean userRechargeRequestBean, HttpServletRequest request) {
        logger.info("api充值   请求参数  ", userRechargeRequestBean);
        Map<String, Object> result = directRechargeService.recharge(userRechargeRequestBean, request);
        if (null != result && result.get("modelAndView") == null) {
            return callbackErrorViewForMap(result);
        }
        ModelAndView modelAndView = (ModelAndView) result.get("modelAndView");
        return modelAndView;
    }

    /**
    * 页面充值同步回调
    * @author Zha Daojian
    * @date 2018/12/6 18:07
    * @param request, bean
    * @return org.springframework.web.servlet.ModelAndView
    **/
    @ApiIgnore
    @GetMapping(value = "/directRechargePageReturn")
    public ModelAndView pageReturn(HttpServletRequest request, BankCallBean bean) {
        logger.info("页面充值同步回调start,请求参数为：【" + JSONObject.toJSONString(bean, true) + "】");
        Map<String, Object> result = directRechargeService.pageReturn(request, bean);
        return callbackErrorViewForMap(result);
    }

    /**
    * 页面充值异步回调
    * @author Zha Daojian
    * @date 2018/12/6 18:07
    * @param request, bean
    * @return com.hyjf.pay.lib.bank.bean.BankCallResult
    **/
    @ApiIgnore
    @ResponseBody
    @PostMapping(value = "/directRechargePageBgreturn")
    public BankCallResult bgreturn(HttpServletRequest request, @RequestBody BankCallBean bean) {
        logger.info("页面充值异步回调start");
        return directRechargeService.bgreturn(request, bean);
    }
}