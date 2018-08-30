/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.user.withdraw;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.cs.user.bean.UserWithdrawRequestBean;
import com.hyjf.cs.user.controller.BaseUserController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: sunpeikai
 * @version: UserWithdrawController, v0.1 2018/8/30 10:26
 */
@Api(value = "api端-用户提现接口",tags = "api端-用户提现接口")
@Controller
@RequestMapping(value = "/server/user/withdraw")
public class ApiUserWithdrawController extends BaseUserController {

    @ApiOperation(value = "外部服务接口用户提现",notes = "外部服务接口用户提现")
    @PostMapping(value = "/withdraw")
    public ModelAndView withdraw(@RequestBody UserWithdrawRequestBean userWithdrawRequestBean, HttpServletRequest request, HttpServletResponse response){
        logger.info("外部服务接口用户提现");
        logger.info("用户提现第三方请求参数:" + JSONObject.toJSONString(userWithdrawRequestBean));
        return new ModelAndView();
    }
}
