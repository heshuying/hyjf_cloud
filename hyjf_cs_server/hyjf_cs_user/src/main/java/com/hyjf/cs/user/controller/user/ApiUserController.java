/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.user;

import com.hyjf.cs.user.beans.AutoPlusRequestBean;
import com.hyjf.cs.user.service.UserService;
import com.hyjf.cs.user.util.ClientConstant;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhangqq
 * @version ApiUserController, v0.1 2018/5/23 14:38
 */
@Api(value = "api端用户接口")
@RestController
@RequestMapping("/api/user")
public class ApiUserController {

    @Autowired
    UserService userService;

    /**
     * 自动投资授权
     * @param payRequestBean
     * @return
     */
    @RequestMapping(value = "/userAuthInves")
    public ModelAndView userAuthInves(AutoPlusRequestBean payRequestBean){
        ModelAndView modelAndView =  userService.apiUserAuth(ClientConstant.INVES_AUTO_TYPE,payRequestBean);
        return modelAndView;
    }

    /**
     * 用户自动债转授权
     * @param payRequestBean
     * @return
     */
    @RequestMapping("/userAuthCredit")
    public ModelAndView userAuthCredit(AutoPlusRequestBean payRequestBean){
        ModelAndView modelAndView =  userService.apiUserAuth(ClientConstant.CREDIT_AUTO_TYPE,payRequestBean);
        return modelAndView;
    }

}
