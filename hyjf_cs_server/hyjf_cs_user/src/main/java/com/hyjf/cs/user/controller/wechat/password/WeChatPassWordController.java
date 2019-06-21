/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.password;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.AppUserToken;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.config.SystemConfig;
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
@Api(value = "密码相关服务",tags = "weChat端-密码相关服务")
@RestController
@RequestMapping("/hyjf-wechat")
public class WeChatPassWordController {

    @Autowired
    PassWordService passWordService;
    private static final Logger logger = LoggerFactory.getLogger(WeChatPassWordController.class);
    @Autowired
    SystemConfig systemConfig;

    /**
     * 修改登录密码
     * @param request
     * @return
     */
    @ApiOperation(value = "修改登陆密码", notes = "修改登陆密码")
    @PostMapping(value = "/wx/user/resetpwd/modify.do")
    public JSONObject updateLoginPassWD(@RequestHeader(value = "userId") Integer userId,
                                        @RequestHeader(value = "wjtClient",required = false) String wjtClient,
                                        HttpServletRequest request){
        JSONObject ret = new JSONObject();
        // 新密码
        String newPassword = request.getParameter("newPassword");
        // 旧密码
        String oldPassword = request.getParameter("oldPassword");
        UserVO user = passWordService.getUsersById(userId);
        CheckUtil.check(StringUtils.isNotBlank(oldPassword)&&StringUtils.isNotBlank(newPassword), MsgEnum.STATUS_CE000001);
        // 汇盈的用户不能登录温金投
        if(wjtClient!=null && (wjtClient.equals(ClientConstants.WJT_PC_CLIENT+"") || wjtClient.equals(ClientConstants.WJT_WEI_CLIENT+""))
                && !user.getInstCode().equals(systemConfig.getWjtInstCode())){
            throw new CheckException(MsgEnum.ERR_USER_WJT_OPT_ERR);
        }
        newPassword = RSAJSPUtil.rsaToPassword(newPassword);
        oldPassword = RSAJSPUtil.rsaToPassword(oldPassword);
        ret = passWordService.weChatCheckParam(user,newPassword,oldPassword);
        if(null!=ret.get(CustomConstants.APP_STATUS)){
            return ret;
        }
        passWordService.updatePassWd(user,newPassword);
        ret.put("status", "000");
        ret.put("statusDesc", "修改密码成功");
        return ret;
    }

    /**
     * 重置登录密码(手机号显示)
     *
     * @param
     * @return
     */
    @ApiOperation(value = " 重置登录密码",notes = " 重置登录密码")
    @GetMapping(value = "/wx/user/resetpwd/resetLoginPassword")
    public JSONObject displayPhone(HttpServletRequest request, SendSmsVO sendSmsVo) {
        JSONObject ret = new JSONObject();
        String sign = sendSmsVo.getSign();
        if (StringUtils.isNotBlank(sign)) {
            // 取得用户ID
            AppUserToken at = SecretUtil.getAppUserToken(sign);
            Integer userId = at.getUserId();
            //根据用户Id查询用户
            UserVO users = passWordService.getUsersById(userId);
            if (users != null && users.getMobile() != null) {
                String mobile = users.getMobile();
                ret.put("mobile", mobile);
                ret.put("status","000");
                ret.put("statusDesc","成功");
                return ret;
            }
            ret.put("mobile","");
            ret.put("status","99");
            ret.put("statusDesc","获取用户信息失败");
            return ret;
        }
        ret.put("mobile","");
        ret.put("status","000");
        ret.put("statusDesc","用户未登录");
        return ret;
    }

    /**
     * 找回登录密码
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "找回密码",notes = "找回密码")
    @PostMapping(value = "/wx/user/resetpwd/reset.do")
    public JSONObject resetPassword(HttpServletRequest request, @RequestHeader(value = "wjtClient",required = false) String wjtClient,
                                    SendSmsVO sendSmsVo) {
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

        // 汇盈的用户不能登录温金投
        if(wjtClient!=null && (wjtClient.equals(ClientConstants.WJT_PC_CLIENT+"") || wjtClient.equals(ClientConstants.WJT_WEI_CLIENT+""))
                && !user.getInstCode().equals(systemConfig.getWjtInstCode())){
            throw new CheckException(MsgEnum.ERR_USER_WJT_OPT_ERR);
        }

        // 业务逻辑
        try {
            // 校验验证码
            passWordService.backCheck(sendSmsVo);
            passWordService.updatePassWd(user,newPassword);
            RedisUtils.del(RedisConstants.PASSWORD_ERR_COUNT_ALL+user.getUserId());
            // pc1.1.3 新增 如果重置密码成功 就解锁帐号锁定
            passWordService.unlockUser(user.getUserId());
            // pc1.1.3 新增 如果重置密码成功 就重新自动登陆  短信登录
            RedisUtils.del(RedisConstants.APP_SMS_LOGIN_KEY+user.getUserId());
            ret.put("status", "000");
            ret.put("statusDesc", "修改密码成功");
        }catch (CheckException e){
            ret.put("status", "99");
            ret.put("statusDesc",e.getMessage());
        }catch (Exception e) {
            logger.info("修改密码失败...");
            ret.put("status", "99");
            ret.put("statusDesc", "失败");
        }
        return ret;
    }


    /**
     * 微信端获取短信验证码
     * @param sendSmsVO
     * @return
     */
    @ApiOperation(value = "微信端获取短信验证码",notes = "微信端获取短信验证码")
    @PostMapping(value = "/wx/user/resetpwd/sendVerificationCode.do")
    public JSONObject sendVerificationCode(SendSmsVO sendSmsVO) {
        return passWordService.sendCode(sendSmsVO);
    }


    /**
     * 微信端验证短信验证码
     * @param
     * @param sendSmsVo
     * @return
     */
    @ApiOperation(value = "微信端验证短信验证码",notes = "微信端验证短信验证码")
    @PostMapping(value = "/wx/user/resetpwd/validateVerificationCodeAction.do")
    public JSONObject validateVerificationCoden(SendSmsVO sendSmsVo) {
        return passWordService.validateVerificationCoden(sendSmsVo,false);
    }


}