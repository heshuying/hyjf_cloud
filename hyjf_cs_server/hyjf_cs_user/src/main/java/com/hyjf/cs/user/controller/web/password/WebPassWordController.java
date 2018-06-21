/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.web.password;

import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.bank.LogAcqResBean;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.BindCardError;
import com.hyjf.cs.user.constants.PassWordError;
import com.hyjf.cs.user.service.password.PassWordService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangc
 */
@Api(value = "密码相关服务")
@Controller
@RequestMapping("/web/user/password")
public class WebPassWordController {
    private static final Logger logger = LoggerFactory.getLogger(WebPassWordController.class);

    @Autowired
    PassWordService passWordService;

    @Autowired
    SystemConfig systemConfig;

    @ApiOperation(value = "修改登陆密码", notes = "修改登陆密码")
    @PostMapping(value = "/updateLoginPassword", produces = "application/json; charset=utf-8")
    public WebResult updateLoginPassWD(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request, HttpServletResponse response){
        WebViewUserVO user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUserVO.class);
        WebResult<String> result = null;
        String oldpass = request.getParameter("oldPassword");
        String password = request.getParameter("newPw");
        String password2 = request.getParameter("pwSure");

        result = passWordService.updatePassWd(user.getUserId(),oldpass,password,password2);

        return result;
    }

    /**
     * 设置交易密码
     * @return
     */
    @PostMapping(value = "/setTeaderPassword", produces = "application/json; charset=utf-8")
    public  WebResult<Object> setPassword(@RequestHeader(value = "token", required = true) String token) {
        WebResult<Object> result = new WebResult<Object>();
        // 用户id
        WebViewUserVO user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUserVO.class);
        CheckUtil.check(null!=user,MsgEnum.ERR_USER_NOT_LOGIN);
        // 判断用户是否开户
        boolean accountFlag = user.isBankOpenAccount();
        // 未开户
        if (!accountFlag) {
            throw new ReturnMessageException(PassWordError.USER_OPENBANK_ERROR);
        }
        // 判断用户是否设置过交易密码
        Integer passwordFlag = user.getIsSetPassword();
        CheckUtil.check(passwordFlag != 1,MsgEnum.STATUS_TP000001);
        int userId = user.getUserId();
        String successUrl = systemConfig.getWebHost() + "/web/user/password/success";
        // 同步调用路径
        String retUrl = systemConfig.getWebHost() + "/web/user/password/faild";
        // 异步调用路
        String bgRetUrl = systemConfig.getWebHost() + "/web/user/password/passwordBgreturn";
        // 调用设置密码接口
        BankCallBean bean = new BankCallBean();
        bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        bean.setTxCode(BankCallConstant.TXCODE_PASSWORD_SET);// 消息类型(用户开户)
        bean.setInstCode(systemConfig.instcode);// 机构代码
        bean.setBankCode(systemConfig.bankCode);
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        bean.setChannel(BankCallConstant.CHANNEL_PC);
        if(user.getUserType() == 1){ //企业用户 传组织机构代码
            CorpOpenAccountRecordVO record= passWordService.getCorpOpenAccountRecord(userId);
            bean.setIdType(record.getCardType() != null ? String.valueOf(record.getCardType()) : BankCallConstant.ID_TYPE_COMCODE);// 证件类型 20：其他证件（组织机构代码）25：社会信用号
            bean.setIdNo(record.getBusiCode());
            bean.setName(record.getBusiName());
        }else{
            bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
            bean.setIdNo(user.getIdcard());
            bean.setName(user.getTruename());
        }
        bean.setAccountId(user.getBankAccount());// 电子账号
        bean.setMobile(user.getMobile());
        bean.setRetUrl(retUrl);// 页面同步返回 URL
        bean.setSuccessfulUrl(successUrl);
        bean.setNotifyUrl(bgRetUrl);// 页面异步返回URL(必须)
        // 商户私有域，存放开户平台,用户userId
        LogAcqResBean acqRes = new LogAcqResBean();
        acqRes.setUserId(userId);
        bean.setLogAcqResBean(acqRes);
        // 操作者ID
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_PASSWORDSET);
        bean.setLogOrderId(GetOrderIdUtils.getUsrId(userId));
        bean.setLogRemark("设置交易密码");
        // 跳转到汇付天下画面
        Map<String,Object> map = new HashMap<>();
        try {
            map = BankCallUtils.callApiMap(bean);
        } catch (Exception e) {
            throw new ReturnMessageException(PassWordError.BANK_CONNECT_ERROR);
        }
        result.setData(map);
        return result;
    }

    /**
     * 设置交易密码同步回调
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/passwordReturn", produces = "application/json; charset=utf-8")
    public  WebResult<String> passwordReturn(HttpServletRequest request, HttpServletResponse response,
                                       @ModelAttribute BankCallBean bean) {
        WebResult<String> result = new WebResult<String>();
        int userId = Integer.parseInt(bean.getLogUserId());
        UserVO user = passWordService.getUsersById(userId);
        // 返回失败
        if (bean.getRetCode()!=null&&!BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
            throw new ReturnMessageException(PassWordError.PASSWORK_SET_ERROR);
        }
        // 判断用户是否设置了交易密码
        boolean flag = user.getIsSetPassword() == 1 ? true : false;
        if (flag) {
            result.setStatus("0");
            result.setStatusDesc("交易密码设置成功");
            return result;
        }
        BankOpenAccountVO bankOpenAccount = passWordService.getBankOpenAccount(userId);
        // 调用查询电子账户密码是否设置
        BankCallBean selectbean = new BankCallBean();
        selectbean.setVersion(BankCallConstant.VERSION_10);
        selectbean.setTxCode(BankCallConstant.TXCODE_PASSWORD_SET_QUERY);
        // 机构代码
        selectbean.setInstCode(systemConfig.instcode);
        selectbean.setBankCode(systemConfig.bankCode);
        selectbean.setTxDate(GetOrderIdUtils.getTxDate());
        selectbean.setTxTime(GetOrderIdUtils.getTxTime());
        selectbean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        selectbean.setChannel(BankCallConstant.CHANNEL_PC);
        // 电子账号
        selectbean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));
        // 操作者ID
        selectbean.setLogUserId(String.valueOf(userId));
        selectbean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        selectbean.setLogClient(0);
        // 返回参数
        BankCallBean retBean = null;
        // 调用接口
        retBean = BankCallUtils.callApiBg(selectbean);

        try {
            if("1".equals(retBean.getPinFlag())){
                // 是否设置密码
                passWordService.updateUserIsSetPassword(userId);
                result.setStatus("0");
                result.setStatusDesc("交易密码设置成功");
                return result;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        result.setStatus("1");
        result.setStatusDesc("交易密码设置失败");
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
                // 开户后保存相应的数据以及日志
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
    @PostMapping(value = "/resetTeaderPassword", produces = "application/json; charset=utf-8")
    public WebResult<Object>  resetPassword(@RequestHeader(value = "token", required = true) String token) {
        WebResult<Object> result = new WebResult<Object>();
        // 用户id
        WebViewUserVO user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUserVO.class);
        if (user == null) {
            throw new ReturnMessageException(PassWordError.USER_LOGIN_ERROR);
        }
        int userId = user.getUserId();
        String successUrl = systemConfig.getWebHost() + "/web/user/password/success";
        // 同步调用路径
        String retUrl = systemConfig.getWebHost() + "/web/user/password/faild";
        // 异步调用路
        String bgRetUrl = systemConfig.webHost + "/web/user/password/resetPasswordBgreturn";

        // 调用设置密码接口
        BankCallBean bean = new BankCallBean();
        bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        bean.setTxCode(BankCallConstant.TXCODE_PASSWORD_RESET);// 消息类型(用户开户)
        bean.setInstCode(systemConfig.instcode);// 机构代码
        bean.setBankCode(systemConfig.bankCode);
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        bean.setChannel(BankCallConstant.CHANNEL_PC);
        if(user.getUserType() == 1){ //企业用户 传组织机构代码
            CorpOpenAccountRecordVO record= passWordService.getCorpOpenAccountRecord(userId);
            bean.setIdType(record.getCardType() != null ? String.valueOf(record.getCardType()) : BankCallConstant.ID_TYPE_COMCODE);// 证件类型 20：其他证件（组织机构代码）25：社会信用号
            bean.setIdNo(record.getBusiCode());
            bean.setName(record.getBusiName());
        }else{
            bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
            bean.setIdNo(user.getIdcard());
            bean.setName(user.getTruename());
        }
        bean.setAccountId(user.getBankAccount());// 电子账号
        bean.setMobile(user.getMobile());
        bean.setRetUrl(retUrl);// 页面同步返回 URL
        bean.setSuccessfulUrl(successUrl);
        bean.setNotifyUrl(bgRetUrl);// 页面异步返回URL(必须)
        // 商户私有域，存放开户平台,用户userId
        LogAcqResBean acqRes = new LogAcqResBean();
        acqRes.setUserId(userId);
        bean.setLogAcqResBean(acqRes);
        // 操作者ID
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_MOBILE);
        bean.setLogOrderId(GetOrderIdUtils.getUsrId(userId));
        Map<String,Object> map = new HashMap<>();
        try {
            map = BankCallUtils.callApiMap(bean);
        } catch (Exception e) {
            throw new ReturnMessageException(PassWordError.BANK_CONNECT_ERROR);
        }
        result.setData(map);
        return result;
    }

    /**
     * 重置交易密码同步回调
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/resetPasswordReturn", produces = "application/json; charset=utf-8")
    public WebResult<String> resetPasswordReturn(HttpServletRequest request, HttpServletResponse response,
                                            @ModelAttribute BankCallBean bean) {
        WebResult<String> result = new WebResult<String>();
        // 返回失败
        if (bean.getRetCode()!=null&&!BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
            result.setStatus("1");
            result.setStatusDesc("交易密码修改失败");
            return result;
        }

        result.setStatus("0");
        result.setStatusDesc("交易密码修改成功");
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
    public WebResult<Object> setPasswordSendCode(@RequestHeader(value = "token", required = true) String token,@RequestBody Map<String,String> param) {
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
            result.setStatusDesc(BindCardError.BANK_CALL_ERROR.getMsg());
            logger.error("请求验证码接口发生异常", e);
        }
        if(bankBean == null || !(BankCallStatusConstant.RESPCODE_SUCCESS.equals(bankBean.getRetCode()))) {
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc(BindCardError.BANK_CALL_ERROR.getMsg());
            logger.error("请求验证码接口失败");
        }else {
            result.setData(bankBean.getSrvAuthCode());
        }
        return result;
    }

}