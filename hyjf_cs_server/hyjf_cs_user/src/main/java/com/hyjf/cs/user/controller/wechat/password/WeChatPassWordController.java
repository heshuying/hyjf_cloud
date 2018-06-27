/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.password;

import com.hyjf.am.resquest.user.SmsCodeRequest;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.WeChatResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.service.password.PassWordService;
import com.hyjf.cs.user.util.RSAJSPUtil;
import com.hyjf.cs.user.vo.PasswordRequest;
import com.hyjf.cs.user.vo.SendSmsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author wangc
 */
@Api(value = "密码相关服务")
@RestController
@RequestMapping("/weChat/user/password")
public class WeChatPassWordController {

    @Autowired
    PassWordService passWordService;
    private static final Logger logger = LoggerFactory.getLogger(WeChatPassWordController.class);

    @ApiOperation(value = "修改登陆密码", notes = "修改登陆密码")
    @PostMapping(value = "/updateLoginPassword", produces = "application/json; charset=utf-8")
    public WebResult updateLoginPassWD(@RequestHeader(value = "token") String token,PasswordRequest passwordRequest){
        UserVO userVO = passWordService.getUsers(token);
        WebResult<String> result = new WebResult<>();
        String oldPassword = passwordRequest.getOldPassword();
        String newPassword = passwordRequest.getNewPassword();
        CheckUtil.check(StringUtils.isNotBlank(oldPassword)&&StringUtils.isNotBlank(newPassword), MsgEnum.STATUS_CE000001);
        newPassword = RSAJSPUtil.rsaToPassword(newPassword);
        oldPassword = RSAJSPUtil.rsaToPassword(oldPassword);
        passWordService.weChatCheckParam(userVO,newPassword,oldPassword);
        passWordService.updatePassWd(userVO,newPassword);
        return result;
    }

    /**
     * @author fengping
     * 微信端获取短信验证码
     * @param
     * @return
     */
    @PostMapping(value = "sendVerificationCode", produces = "application/json; charset=utf-8")
    public WeChatResult sendVerificationCode(@RequestBody SendSmsVO sendSmsVo) {
         passWordService.sendCode(sendSmsVo);
         return null;
    }

    /**
     * @author fengping
     * 微信端验证短信验证码
     * @param
     * @return
     */
    @PostMapping(value = "validateVerificationCode", produces = "application/json; charset=utf-8")
    public WeChatResult validateVerificationCoden(@RequestBody SendSmsVO sendSmsVo) {
        //passWordService.validateVerificationCoden(sendSmsVo,false);
        return null;
    }

    /**
     * 重置登录密码(手机号显示)
     *
     * @param
     * @return
     */
    @PostMapping(value = "/resetLoginPassword", produces = "application/json; charset=utf-8")
    public WeChatResult<String> displayPhone(@RequestHeader(value = "userId") Integer userId) {
            WeChatResult<String> result = new WeChatResult<>();
            CheckUtil.check(null!=userId,MsgEnum.ERR_USER_NOT_LOGIN);
            //根据用户Id查询用户
            UserVO userVO = passWordService.getUsersById(userId);
            CheckUtil.check(null!=userVO&&StringUtils.isNotBlank(userVO.getMobile()),MsgEnum.STATUS_CE000006);
            result.setData(userVO.getMobile());
        return result;
    }

    /**
     * 找回登录密码
     *
     * @param
     * @return
     */
    @PostMapping(value = "/reset", produces = "application/json; charset=utf-8")
    public WeChatResult resetPassword(@RequestBody SmsCodeRequest request, @RequestBody Map<String,String> param) {
        WeChatResult result = new WeChatResult();
        String mobile = request.getMobile();
        UserVO user = passWordService.getUsersByMobile(mobile);
        CheckUtil.check(null!=user&&null!=user.getUserId(),MsgEnum.ERR_USER_NOT_EXISTS);
        // 新密码
        String newPassword = param.get("newPassword");
        //密码解密
        newPassword = RSAJSPUtil.rsaToPassword(newPassword);
        String verificationType = CustomConstants.PARAM_TPL_ZHAOHUIMIMA;
        request.setVerificationType(verificationType);
        passWordService.backCheck(request,newPassword);
        passWordService.updatePassWd(user,newPassword);
        return result;
    }
}