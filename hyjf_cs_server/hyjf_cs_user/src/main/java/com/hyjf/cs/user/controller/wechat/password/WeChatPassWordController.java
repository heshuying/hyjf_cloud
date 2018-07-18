/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.password;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.WeChatResult;
import com.hyjf.cs.user.service.password.PassWordService;
import com.hyjf.cs.user.util.RSAJSPUtil;
import com.hyjf.cs.user.vo.SendSmsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangc
 */
@Api(value = "密码相关服务",description = "密码相关服务")
@RestController
@RequestMapping("/weChat/user/password")
public class WeChatPassWordController {

    @Autowired
    PassWordService passWordService;
    private static final Logger logger = LoggerFactory.getLogger(WeChatPassWordController.class);

    @ApiOperation(value = "修改登陆密码", notes = "修改登陆密码")
    @PostMapping(value = "/updateLoginPassword", produces = "application/json; charset=utf-8")
    public WeChatResult updateLoginPassWD(HttpServletRequest request){
        WeChatResult<String> result = new WeChatResult<>();
        Integer userId = (Integer) request.getAttribute("userId");
        // 新密码
        String newPassword = request.getParameter("newPassword");
        // 旧密码
        String oldPassword = request.getParameter("oldPassword");
        UserVO user = passWordService.getUsersById(userId);
        CheckUtil.check(StringUtils.isNotBlank(oldPassword)&&StringUtils.isNotBlank(newPassword), MsgEnum.STATUS_CE000001);
        newPassword = RSAJSPUtil.rsaToPassword(newPassword);
        oldPassword = RSAJSPUtil.rsaToPassword(oldPassword);
        passWordService.weChatCheckParam(user,newPassword,oldPassword);
        passWordService.updatePassWd(user,newPassword);
        return result;
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
     * @param request
     * @return
     */
    @ApiOperation(value = "找回密码",notes = "找回密码")
    @PostMapping(value = "/reset", produces = "application/json; charset=utf-8")
    public JSONObject resetPassword(HttpServletRequest request, SendSmsVO sendSmsVo) {
        JSONObject ret = new JSONObject();
        String mobile = request.getParameter("mobile");
        UserVO user = passWordService.getUsersByMobile(mobile);
        Integer userId = user.getUserId();
        if (userId==null) {
            ret.put("status", "99");
            ret.put("statusDesc", "用户不存在");
            return ret;
        }
        // 新密码
        String newPassword = request.getParameter("newPassword");
        //密码解密
        newPassword = RSAJSPUtil.rsaToPassword(newPassword);

        // 检查参数正确性
        if (Validator.isNull(userId) || Validator.isNull(newPassword) || Validator.isNull(sendSmsVo.getSmscode())) {
            ret.put("status", "997");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }
        // 校验验证码
        passWordService.backCheck(sendSmsVo);
        // 业务逻辑
        try {
            passWordService.updatePassWd(user,newPassword);
            ret.put("status", "000");
            ret.put("statusDesc", "修改密码成功");
        } catch (Exception e) {
            logger.info("修改密码失败...");
            ret.put("status", "99");
            ret.put("statusDesc", "失败");
        }
        return ret;
    }
}