/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.regist;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.cs.user.bean.UserRegisterRequestBean;
import com.hyjf.cs.user.bean.UserRegisterResultBean;
import com.hyjf.cs.user.constants.ErrorCodeConstant;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.register.RegisterService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.vo.RegisterRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangqingqing
 * @version RegisterController, v0.1 2018/6/11 14:27
 */
@Api(value = "api端-用户注册接口", tags = "api端-用户注册接口")
@RestController
@RequestMapping("/hyjf-api/server/user")
public class ApiRegisterController extends BaseUserController {
    private static final Logger logger = LoggerFactory.getLogger(ApiRegisterController.class);

    @Autowired
    RegisterService registService;

    /**
     * @param request
     * @Author: zhangqingqing
     * @Desc :注册
     * @Param: * @param registerVO
     * @Date: 16:44 2018/5/30
     * @Return
     */
    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping(value = "/register/register.do")
    @ResponseBody
    public UserRegisterResultBean register(@RequestBody UserRegisterRequestBean userRegisterRequestBean, HttpServletRequest request) {
        logger.info("api端注册接口, registerVO is :{}", JSONObject.toJSONString(userRegisterRequestBean));
        UserRegisterResultBean result = new UserRegisterResultBean();
        RegisterRequest registerRequest = new RegisterRequest();
        //切换参数实体(调共用方法)
        BeanUtils.copyProperties(userRegisterRequestBean, registerRequest);
        registerRequest.setUtmId(userRegisterRequestBean.getChannel());
        registerRequest.setReffer(userRegisterRequestBean.getRecommended());
        //验证参数
        registService.apiCheckParam(registerRequest);
        //获取ip
        String ip = GetCilentIP.getIpAddr(request);
        //验证手机号是否已被注册
        UserVO user = registService.getUsersByMobile(registerRequest.getMobile());
        if (null!=user) {
            result.setStatus(MsgEnum.STATUS_ZC000005.getCode());
            result.setStatusForResponse(MsgEnum.STATUS_ZC000005.getMsg());
            result.setStatusDesc("手机号已在平台注册");
            result.setIsOpenAccount(String.valueOf(user.getBankOpenAccount()));
            if (user.getBankOpenAccount() != null && user.getBankOpenAccount() == 1) {
                result.setAccount(registService.getAccountId(user.getUserId()));
            }
            if (user.getIsSetPassword() != null) {
                result.setIsSetPassword(String.valueOf(user.getIsSetPassword()));
            }
            result.setAutoInvesStatus(registService.getAutoInvesStatus(user.getUserId()));
            result.setUserId(user.getUserId());
            return result;
        }
        //调用注册方法
        user = registService.apiRegister(userRegisterRequestBean,registerRequest, ip);
        if (user != null) {
            logger.info("api端注册成功, userId is :{}", user.getUserId());
            result.setStatus(ErrorCodeConstant.SUCCESS);
            result.setStatusForResponse(ErrorCodeConstant.SUCCESS);
            result.setStatusDesc("注册成功");
            // 用户Id
            result.setUserId(user.getUserId());
            // 用户名
            result.setUserName(user.getUsername());
            result.setIsOpenAccount("0");
        } else {
            logger.error("api端注册失败...");
            result.setStatus(MsgEnum.STATUS_CE999999.getCode());
            result.setStatusDesc(MsgEnum.ERR_USER_REGISTER.getMsg());
        }

        return result;
    }

}
