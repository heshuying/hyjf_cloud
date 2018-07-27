/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.web.password;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.bank.LogAcqResBean;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.MD5Utils;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.password.PassWordService;
import com.hyjf.cs.user.util.RSAJSPUtil;
import com.hyjf.cs.user.vo.PasswordRequest;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
@Api(value = "web端-密码相关服务",description = "web端-密码相关服务")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/hyjf-web/user/password")
public class WebPassWordController {
    private static final Logger logger = LoggerFactory.getLogger(WebPassWordController.class);

    @Autowired
    PassWordService passWordService;

    @Autowired
    SystemConfig systemConfig;

    @ApiOperation(value = "修改登陆密码", notes = "修改登陆密码")
    @PostMapping(value = "/login-password", produces = "application/json; charset=utf-8")
    public WebResult updateLoginPassWD(@RequestHeader(value = "userId") Integer userId,PasswordRequest passwordRequest){
        UserVO userVO = passWordService.getUsersById(userId);
        WebResult<String> response = new WebResult<>();
        String oldPW = passwordRequest.getOldPassword();
        String newPW = passwordRequest.getNewPassword();
        String pwSure = passwordRequest.getPwSure();
        passWordService.checkParam(userVO,oldPW,newPW,pwSure);
        int result = passWordService.updatePassword(userVO, newPW);
       if(result>0){
           WebViewUserVO webUser = passWordService.getWebViewUserByUserId(userId);
           if (null != webUser) {
               webUser = passWordService.setToken(webUser);
               RedisUtils.del(RedisConstants.PASSWORD_ERR_COUNT_WEB+webUser.getMobile());
               RedisUtils.del(RedisConstants.PASSWORD_ERR_COUNT_WEB+webUser.getUsername());
           }
       }
        return response;
    }

    /**
     * 设置交易密码
     * @return
     */
    @ApiOperation(value = "设置交易密码", notes = "设置交易密码")
    @PostMapping(value = "/transaction", produces = "application/json; charset=utf-8")
    public  WebResult<Object> setTransaction(@RequestHeader(value = "token") String token) {
        WebResult<Object> result = new WebResult<Object>();
        UserVO user = this.passWordService.getUsers(token);
        CheckUtil.check(null!=user,MsgEnum.ERR_USER_NOT_LOGIN);
        Map<String,Object> map = passWordService.setPassword(user);
        result.setData(map);
        return result;
    }

    /**
     * 设置交易密码异步回调
     * @return
     */
    @ApiOperation(value = " 设置交易密码异步回调",notes = " 设置交易密码异步回调")
    @PostMapping(value = "/passwordBgreturn", produces = "application/json; charset=utf-8")
    public WebResult<Object> passwordBgreturn(@RequestBody BankCallBean bean) {
        WebResult<Object> result = new WebResult<Object>();
        bean.convert();
        LogAcqResBean acqes = bean.getLogAcqResBean();
        int userId = acqes.getUserId();
        // 查询用户开户状态
        UserVO user = passWordService.getUsersById(userId);
        // 成功或审核中
        if (user != null && BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))) {
            try {
                // 修改密码后保存相应的数据以及日志
                passWordService.updateUserIsSetPassword(userId);
                WebViewUserVO webUser = passWordService.getWebViewUserByUserId(userId);
                if (null != webUser) {
                    passWordService.setToken(webUser);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        result.setStatus("0");
        result.setStatusDesc("交易密码设置成功");
        return result;
    }


    /**
     * 重置交易密码
     * @return
     */
    @ApiOperation(value = "重置交易密码", notes = "重置交易密码")
    @PostMapping(value = "/resetTeaderPassword", produces = "application/json; charset=utf-8")
    public WebResult<Object>  resetPassword(@RequestHeader(value = "token") String token) {
        WebResult<Object> result = new WebResult<Object>();
        UserVO user = this.passWordService.getUsers(token);
        CheckUtil.check(null!=user,MsgEnum.ERR_USER_NOT_LOGIN);
        Map<String,Object> map = passWordService.resetPassword(user);
        result.setData(map);
        return result;
    }

    /**
     * 重置交易密码异步回调
     *
     * @return
     */
    @ApiOperation(value = " 重置交易密码异步回调",notes = " 重置交易密码异步回调")
    @PostMapping(value = "/resetPasswordBgreturn", produces = "application/json; charset=utf-8")
    public WebResult<String> resetPasswordBgreturn(@RequestBody BankCallBean bean) {
        WebResult<String> result = new WebResult<String>();
        result.setStatus("0");
        result.setStatusDesc("交易密码修改成功");
        return result;
    }

    @ApiOperation(value = "修改交易密码发送短信验证码", notes = "修改交易密码发送短信验证码")
    @ApiImplicitParam(name = "param",value = "{mobile: string}", dataType = "Map")
    @PostMapping(value = "/setPasswordSendCode", produces = "application/json; charset=utf-8")
    public WebResult<Object> setPasswordSendCode(@RequestHeader(value = "token") String token,@RequestBody Map<String,String> param) {
        logger.info("web端-交易密码发送短信验证码, param :{}", param);
        WebResult<Object> result = new WebResult<Object>();
        UserVO user = passWordService.getUsers(token);
        CheckUtil.check(user!=null, MsgEnum.ERR_USER_NOT_LOGIN);
        CheckUtil.check(null!=param && StringUtils.isNotBlank(param.get("mobile")), MsgEnum.ERR_PARAM_TYPE);
        String srvTxCode = BankCallConstant.TXCODE_MOBILE_MODIFY_PLUS;
        // 请求银行绑卡接口
        BankCallBean bankBean = null;
        try {
            bankBean = passWordService.callSendCode(user.getUserId(),user.getMobile(),srvTxCode, ClientConstants.CHANNEL_PC,null);
        } catch (Exception e) {
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc(MsgEnum.ERR_BANK_CALL.getMsg());
            logger.error("请求验证码接口发生异常", e);
        }
        if(bankBean == null || !(BankCallStatusConstant.RESPCODE_SUCCESS.equals(bankBean.getRetCode()))) {
            throw new CheckException(MsgEnum.ERR_BANK_CALL);
        }else {
            result.setData(bankBean.getSrvAuthCode());
        }
        return result;
    }

    /**
     * 检查密码格式
     * @param
     * @return
     */
    @ApiOperation(value = "检查密码格式",notes = "检查密码格式")
    @ApiImplicitParam(name = "param",value = "{name:String,oldpass:String}/{password:String}/{password:String,repassword:String}/{rlName:String}/{rlPhone:String}",dataType = "Map")
    @GetMapping(value = "/check-password", produces = "application/json; charset=utf-8")
    public boolean checkParam(@RequestHeader(value = "userId") Integer userId,@RequestBody Map<String,String> param)  {
        UserVO userVO = passWordService.getUsersById(userId);
        String name = param.get("name");
        if (StringUtils.isNotBlank(name)) {
            if ("oldpass".equals(name)) {
                String password = param.get("oldpass");
                password = MD5Utils.MD5(MD5Utils.MD5(password) + userVO.getSalt());
                CheckUtil.check(password.equals(userVO.getPassword()),MsgEnum.ERR_PASSWORD_OLD_INCORRECT);
                return true;
            } else if ("password".equals(name)) {
                String password = param.get("password");
                passWordService.checkPassword(password);
                return true;
            } else if ("repassword".equals(name)) {
                String passwrod = param.get("password");
                String passwrod2 = param.get("repassword");
                passWordService.checkPassword(passwrod2);
                if (!passwrod.equals(passwrod2)) {
                    return false;
                } else {
                    return true;
                }
            } else if ("rlName".equals(name)) {
                String rlName = param.get("rlName");
                if (rlName.length() < 2 || rlName.length() > 4) {
                    return false;
                } else {
                    return true;
                }
            } else if ("rlPhone".equals(name)) {
                String rlPhone = param.get("rlPhone");
                if (rlPhone.length() != 11 || !Validator.isMobile(rlPhone)) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 验证手机号是否已注册
     * @param
     * @param
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "验证手机号是否已注册",notes = "验证手机号是否已注册")
    @ApiImplicitParam(name = "param",value = "{mobile:String}",dataType = "Map")
    @PostMapping(value = "checkPhone", produces = "application/json; charset=utf-8")
    public boolean checkPhone(@RequestBody Map<String,String> param) {
        String mobile = param.get("mobile");
        if (StringUtils.isEmpty(mobile)) {
            return false;
        } else {
            // 验证是否存在账号
            return passWordService.existPhone(mobile);
        }
    }

    /**
     * 找回密码
     * @param request
     * @return
     */
    @ApiOperation(value = "找回密码",notes = "找回密码")
    @PostMapping(value = "/recover", produces = "application/json; charset=utf-8")
    public WebResult recover(@RequestBody PasswordRequest request) {
        WebResult result = new WebResult();
        // 密码1
        String password1 = request.getNewPassword();
        // 密码2
        String password2 = request.getPwSure();
        // 手机号
        String mobile = request.getMobile();
        // 短信验证码
        String code = request.getVerificationCode();
        CheckUtil.check(StringUtils.isNotBlank(password1),MsgEnum.ERR_OBJECT_REQUIRED,"密码");
        password1 = RSAJSPUtil.rsaToPassword(password1);
        password2 = RSAJSPUtil.rsaToPassword(password2);
        CheckUtil.check(StringUtils.isNotBlank(password2)&&password1.equals(password2),MsgEnum.ERR_PASSWORD_TWO_DIFFERENT_PASSWORD);
        CheckUtil.check(password1.length() >= 6 && password1.length() <= 16,MsgEnum.ERR_PASSWORD_LENGTH);
        // 改变验证码状态
        int checkStatus = this.passWordService.updateCheckMobileCode(mobile, code, CommonConstant.PARAM_TPL_ZHAOHUIMIMA, CustomConstants.CLIENT_PC, CommonConstant.CKCODE_YIYAN, CommonConstant.CKCODE_USED);
        // 再次验证验证码
        CheckUtil.check(checkStatus==1,MsgEnum.STATUS_ZC000015);
        UserVO user = passWordService.getUsersByMobile(mobile);
        CheckUtil.check(null!=user,MsgEnum.STATUS_CE000006);
        int cnt = passWordService.updatePassword(user, password1);
        CheckUtil.check(cnt>0,MsgEnum.ERR_PASSWORD_MODIFY);
        RedisUtils.del(RedisConstants.PASSWORD_ERR_COUNT_WEB+mobile);
        RedisUtils.del(RedisConstants.PASSWORD_ERR_COUNT_WEB+user.getUsername());
        return result;
    }

    /**
     * 检查输入的 原登录密码 是否已存在
     * @param userId
     * @param param
     * @return
     */
    @ApiOperation(value = "验证原密码是否存在",notes = "验证原密码是否存在")
    @ApiImplicitParam(name = "param",value = "{oldPassword:String}",dataType = "Map")
    @PostMapping(value = "/checkoriginapw", produces = "application/json; charset=utf-8")
    public boolean checkPw(@RequestHeader(value = "userId") Integer userId,@RequestBody Map<String,String> param) {
        String pw = param.get("oldPassword");
        if (pw != null && !"".equals(pw.trim())) {
            pw = RSAJSPUtil.rsaToPassword(pw);
            if (!passWordService.validPassword(userId, pw)) {
                return false;
            } else {
                return true;
            }
        }
        //画面未输入原密码
        else {
            return false;
        }
    }

    /**
     * 跳转到找回密码第二步
     *
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "跳转到找回密码第二步",notes = "跳转到找回密码第二步")
    @ApiImplicitParam(name = "param",value = "{telnum:String,code:String}",dataType = "Map")
    @RequestMapping(value = "/senCodePage", method = RequestMethod.POST)
    public WebResult sencodPage(@RequestBody Map<String,String> param){
        WebResult result = new WebResult();
        JSONObject ret = new JSONObject();
        String telnum = param.get("telnum");
        String code = param.get("code");
        ret.put("telnum", telnum);
        ret.put("code", code);
        ret.put("pubexponent", "10001");
        ret.put("pubmodules", RSAJSPUtil.getPunlicKeys());
        result.setData(ret);
        return result;
    }

    /**
     * 修改 登录密码 页面初始化
     *
     * @param token
     * @return
     */
    @ApiOperation(value = "修改 登录密码 页面初始化",notes = "修改 登录密码 页面初始化")
    @PostMapping(value = "/modifyCode")
    public WebResult modifyCode(@RequestHeader(value = "token") String token,@RequestHeader(value = "userId") Integer userId) {
        WebResult result = new WebResult();
        JSONObject ret = new JSONObject();
        try {
            ret.put("tokenGrant",token);
            ret.put("pubexponent", "10001");
            ret.put("pubmodules", RSAJSPUtil.getPunlicKeys());
            ret.put("userId",userId);
            result.setData(ret);
        } catch (Exception e) {
            logger.error("修改密码时，生成密码加密密钥错误",e);
        }
        return result;
    }

}