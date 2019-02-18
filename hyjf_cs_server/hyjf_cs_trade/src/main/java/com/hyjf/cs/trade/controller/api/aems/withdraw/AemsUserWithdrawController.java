/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.api.aems.withdraw;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.trade.bean.AemsUserWithdrawRequestBean;
import com.hyjf.cs.trade.bean.BaseResultBean;
import com.hyjf.cs.trade.bean.assetpush.UserWithdrawRequestBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.aems.withdraw.AemsUserWithdrawService;
import com.hyjf.cs.trade.service.withdraw.BankWithdrawService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
* AEMS用户提现接口
* @author Zha Daojian
* @date 2018/12/12 15:33
* @param
* @return
**/
@Api(value = "api端-AEMS用户提现接口",tags = "api端-AEMS用户提现接口")
@Controller
@RequestMapping(value = "/hyjf-api/aems/user/withdraw")
public class AemsUserWithdrawController extends BaseTradeController {

    @Autowired
    private AemsUserWithdrawService userWithdrawService;

    /**
     * 用户提现
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "AEMS用户提现",notes = "AEMS用户提现")
    @PostMapping(value = "/withdraw")
    public ModelAndView withdraw(@RequestBody AemsUserWithdrawRequestBean userWithdrawRequestBean, HttpServletRequest request){
        logger.info("用户提现第三方请求参数:" + JSONObject.toJSONString(userWithdrawRequestBean));
        Map<String,Object>  withdrawResult = userWithdrawService.withdraw(userWithdrawRequestBean, request);
        if(withdrawResult.get("modelAndView")!=null){
            ModelAndView result = (ModelAndView) withdrawResult.get("modelAndView");
            return result;
        }
        return callbackErrorViewForMap(withdrawResult);
    }

    /**
     * 用户提现后处理
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiIgnore
    @RequestMapping(value = "/return")
    public ModelAndView cashReturn(HttpServletRequest request,  BankCallBean bean) {
        logger.info("用户提现后同步处理请求参数:" + JSONObject.toJSONString(bean));
        Map<String,Object> result = userWithdrawService.cashReturn(request, bean);
        return callbackErrorViewForMap(result);
    }

    /**
     * 用户提现异步回调处理
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiIgnore
    @ResponseBody
    @RequestMapping(value = "/callback")
    public BankCallResult withdrawBgReturn(HttpServletRequest request, @RequestBody BankCallBean bean) {
        logger.info("用户提现异步回调处理请求参数:" + JSONObject.toJSONString(bean));
        return userWithdrawService.withdrawBgReturn(request, bean);
    }

    /**
     * 获取用户提现记录
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "获取用户提现记录",notes = "获取用户提现记录")
    @ResponseBody
    @PostMapping(value = "/getUserWithdrawRecord")
    public BaseResultBean getUserWithdrawRecord(@RequestBody UserWithdrawRequestBean userWithdrawRequestBean){
       logger.info("获取用户提现记录请求参数:" + JSONObject.toJSONString(userWithdrawRequestBean));
        return userWithdrawService.getUserWithdrawRecord(userWithdrawRequestBean);
    }
}
