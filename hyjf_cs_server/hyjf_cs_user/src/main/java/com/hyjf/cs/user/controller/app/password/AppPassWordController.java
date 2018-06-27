/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.password;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.SmsCodeRequest;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.bank.LogAcqResBean;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.DES;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.common.bean.result.BaseResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.bankopen.BankOpenService;
import com.hyjf.cs.user.service.password.PassWordService;
import com.hyjf.cs.user.vo.PasswordRequest;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author wangc
 */
@Api(value = "app端密码相关服务")
@Controller
@RestController
@RequestMapping("/app/user/password")
public class AppPassWordController {

    @Autowired
    BankOpenService bankOpenService;

    @Autowired
    PassWordService passWordService;

    @Autowired
    SystemConfig systemConfig;

    private static final Logger logger = LoggerFactory.getLogger(AppPassWordController.class);

    @ApiOperation(value = "修改登陆密码", notes = "修改登陆密码")
    @PostMapping(value = "/updateLoginPassword", produces = "application/json; charset=utf-8")
    public BaseResult<Object> updateLoginPassWD(@RequestHeader String key,@RequestHeader(value = "token") String token, @RequestBody PasswordRequest passwordRequest) {
        logger.info("app修改登陆密码 updateLoginPassword start");
        CheckUtil.check(token != null&&null!=key, MsgEnum.ERR_USER_NOT_LOGIN);
        UserVO userVO = passWordService.getUsers(token);
        CheckUtil.check(null!=userVO&&null!=userVO.getUserId(),MsgEnum.STATUS_CE000006);
        passWordService.appCheckParam(key,userVO,passwordRequest);
        AppResult<Object> result = new AppResult<>();
        // 获取登录信息
        String newPassword = DES.decodeValue(key, passwordRequest.getNewPassword());
        passWordService.updatePassWd(userVO,newPassword);
        return result;
    }

    /**
     * 设置交易密码
     * @return
     */
    @ApiOperation(value = "设置交易密码", notes = "设置交易密码")
    @PostMapping(value = "/setTeaderPassword", produces = "application/json; charset=utf-8")
    public AppResult<Object> setPassword(@RequestHeader(value = "token") String token) {
        AppResult<Object> result = new AppResult<Object>();
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
    @ResponseBody
    @PostMapping(value = "/passwordBgreturn")
    public String passwordBgreturn(@RequestBody BankCallBean bean) {
        BankCallResult result = new BankCallResult();
        bean.convert();
        LogAcqResBean acqes = bean.getLogAcqResBean();
        int userId = acqes.getUserId();
        // 查询用户开户状态
        UserVO user = this.passWordService.getUsersById(userId);
        // 成功或审核中
        if (user != null && BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))) {
            try {
                // 开户后保存相应的数据以及日志
                this.passWordService.updateUserIsSetPassword(userId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        result.setMessage("交易密码设置成功");
        result.setStatus(true);
        return JSONObject.toJSONString(result, true);
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
     * @return
     */
    @PostMapping(value = "/resetPasswordBgreturn")
    public String resetPasswordBgreturn(@RequestBody BankCallBean bean) {
        BankCallResult result = new BankCallResult();
        result.setMessage("交易密码修改成功");
        result.setStatus(true);
        return JSONObject.toJSONString(result, true);
    }

    /**
     * 找回密码(重置密码)
     *
     * @paramrequest
     * @param
     * @return
     */
    @PostMapping(value = "/getBackPasswordAction")
    public AppResult getBackPasswordAction(@RequestBody SmsCodeRequest request,@RequestBody Map<String,String> param) {
        AppResult result = new AppResult();
        // 新密码
        String newPassword = param.get("newPassword");
        String verificationType = CustomConstants.PARAM_TPL_ZHAOHUIMIMA;
        request.setVerificationType(verificationType);
        passWordService.backCheck(request,newPassword);
        String mobile = request.getMobile();
        UserVO user = passWordService.getUsersByMobile(mobile);
        CheckUtil.check(null!=user&&null!=user.getUserId(),MsgEnum.ERR_USER_NOT_EXISTS);
        passWordService.updatePassWd(user, newPassword);
        return result;
    }
}