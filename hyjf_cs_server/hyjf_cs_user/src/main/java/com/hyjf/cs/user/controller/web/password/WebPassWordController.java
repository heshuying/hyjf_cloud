/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.web.password;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.bank.LogAcqResBean;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.password.PassWordService;
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
@Api(value = "web端密码相关服务")
@RestController
@RequestMapping("/web/user/password")
public class WebPassWordController {
    private static final Logger logger = LoggerFactory.getLogger(WebPassWordController.class);

    @Autowired
    PassWordService passWordService;

    @Autowired
    SystemConfig systemConfig;

    @ApiOperation(value = "修改登陆密码", notes = "修改登陆密码")
    @PostMapping(value = "/updateLoginPassword", produces = "application/json; charset=utf-8")
    public WebResult updateLoginPassWD(@RequestHeader(value = "token") String token,PasswordRequest passwordRequest){
        UserVO userVO = passWordService.getUsers(token);
        WebResult<String> result = new WebResult<>();
        String oldPW = passwordRequest.getOldPassword();
        String newPW = passwordRequest.getNewPassword();
        String pwSure = passwordRequest.getPwSure();
        passWordService.checkParam(userVO,oldPW,newPW,pwSure);
        passWordService.updatePassWd(userVO,newPW);
        return result;
    }

    /**
     * 设置交易密码
     * @return
     */
    @ApiOperation(value = "设置交易密码", notes = "设置交易密码")
    @PostMapping(value = "/setTeaderPassword", produces = "application/json; charset=utf-8")
    public  WebResult<Object> setPassword(@RequestHeader(value = "token") String token) {
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
    @PostMapping(value = "/passwordBgreturn", produces = "application/json; charset=utf-8")
    public WebResult<String> passwordBgreturn(@RequestBody BankCallBean bean) {
        WebResult<String> result = new WebResult<String>();
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
        logger.info("Web端交易密码发送短信验证码, param :{}", param);
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
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc(MsgEnum.ERR_BANK_CALL.getMsg());
            logger.error("请求验证码接口失败");
        }else {
            result.setData(bankBean.getSrvAuthCode());
        }
        return result;
    }

}