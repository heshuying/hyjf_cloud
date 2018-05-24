/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.user;

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
        ModelAndView modelAndView = userService.userCreditAuthInves(token, ClientConstant.WECHAT_CLIENT, BankCallConstant.QUERY_TYPE_2,request);
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
        ModelAndView modelAndView = userService.userCreditAuthInves(token, ClientConstant.WECHAT_CLIENT, BankCallConstant.QUERY_TYPE_1,request);
        return modelAndView;
    }

    /**
     * 用户授权自动债转同步回调
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/userAuthCreditReturn")
    public ModelAndView userAuthCreditReturn(@RequestHeader(value = "token", required = true) String token,BankCallBean bean, HttpServletRequest request) {
        ModelAndView result = userService.userAuthCreditReturn(token,bean,ClientConstant.CREDIT_AUTO_TYPE,request);
        return result;
    }

    /**
     * 用户授权自动投资同步回调
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/userAuthInvesReturn")
    public ModelAndView userAuthInvesReturn(@RequestHeader(value = "token", required = true) String token,BankCallBean bean, HttpServletRequest request) {
        ModelAndView result = userService.userAuthCreditReturn(token,bean,ClientConstant.INVES_AUTO_TYPE,request);
        return result;
    }

    /**
     * 用户授权自动投资异步回调
     * @param request
     * @param response
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping("/userAuthInvesBgreturn")
    public String userAuthInvesBgreturn(HttpServletRequest request, HttpServletResponse response,BankCallBean bean) {
        String result = userService.userBgreturn(bean);
        return result;
    }

    /**
     * 用户授权自动债转异步回调
     * @param request
     * @param response
     * @param bean
     * @return
     */
    @RequestMapping("/userAuthCreditBgreturn")
    public String userCreditAuthInvesBgreturn(HttpServletRequest request, HttpServletResponse response,
                                              BankCallBean bean) {
        String result = userService.userBgreturn(bean);
        return result;
    }

}
