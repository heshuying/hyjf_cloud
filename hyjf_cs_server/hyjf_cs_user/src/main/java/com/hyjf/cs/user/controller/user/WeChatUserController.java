/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.user;

import com.hyjf.cs.user.beans.BaseMapBean;
import com.hyjf.cs.user.service.UserService;
import com.hyjf.cs.user.util.ClientConstant;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author zhangqq
 * @version WeChatUserController, v0.1 2018/5/21 09:57
 */

@RestController
@RequestMapping("/wechat/user")
public class WeChatUserController {

    @Autowired
    private UserService userService;

    /**
     * 用户自动债转授权
     * @param token
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/userAuthCredit")
    public ModelAndView userAuthCredit(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request, HttpServletResponse response){
        String lastSrvAuthCode = request.getParameter("lastSrvAuthCode");
        String smsCode = request.getParameter("smsCode");
        ModelAndView modelAndView = userService.userCreditAuthInves(token, ClientConstant.WECHAT_CLIENT, BankCallConstant.QUERY_TYPE_2,ClientConstant.CHANNEL_WEI,lastSrvAuthCode,smsCode);
        return modelAndView;
    }

    /**
     * 自动投资授权接口
     * @param token
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/userAuthInves")
    public ModelAndView userAuthInves(@RequestHeader(value = "token", required = true) String token,HttpServletRequest request, HttpServletResponse response){
        String lastSrvAuthCode = request.getParameter("lastSrvAuthCode");
        String smsCode = request.getParameter("smsCode");
        ModelAndView modelAndView = userService.userCreditAuthInves(token, ClientConstant.WECHAT_CLIENT, BankCallConstant.QUERY_TYPE_1,ClientConstant.CHANNEL_WEI,lastSrvAuthCode,smsCode);
        return modelAndView;
    }

    /**
     * 用户授权自动债转同步回调
     * @param token
     * @param bean
     * @param request
     * @return
     */
    @RequestMapping("/userAuthCreditReturn")
    public Map<String,BaseMapBean> userAuthCreditReturn(@RequestHeader(value = "token", required = true) String token,BankCallBean bean, HttpServletRequest request) {
        String sign = request.getHeader("sign");
        String isSuccess = request.getParameter("isSuccess");
        Map<String,BaseMapBean> result = userService.userAuthCreditReturn(token,bean,ClientConstant.CREDIT_AUTO_TYPE,sign,isSuccess);
        return result;
    }

    /**
     * 用户授权自动投资同步回调
     * @param token
     * @param bean
     * @param request
     * @return
     */
    @RequestMapping("/userAuthInvesReturn")
    public Map<String,BaseMapBean> userAuthInvesReturn(@RequestHeader(value = "token", required = true) String token,BankCallBean bean, HttpServletRequest request) {
        String sign = request.getHeader("sign");
        String isSuccess = request.getParameter("isSuccess");
        Map<String,BaseMapBean> result = userService.userAuthCreditReturn(token,bean,ClientConstant.INVES_AUTO_TYPE,sign,isSuccess);
        return result;
    }

    /**
     * 用户授权自动投资异步回调
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping("/userAuthInvesBgreturn")
    public String userAuthInvesBgreturn(BankCallBean bean) {
        String result = userService.userBgreturn(bean);
        return result;
    }

    /**
     * 用户授权自动债转异步回调
     * @param bean
     * @return
     */
    @RequestMapping("/userAuthCreditBgreturn")
    public String userCreditAuthInvesBgreturn(BankCallBean bean) {
        String result = userService.userBgreturn(bean);
        return result;
    }

}
