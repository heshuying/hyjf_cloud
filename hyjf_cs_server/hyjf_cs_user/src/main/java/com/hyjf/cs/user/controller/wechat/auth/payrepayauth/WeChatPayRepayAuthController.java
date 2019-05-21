/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.auth.payrepayauth;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.bean.AuthBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.auth.AuthService;
import com.hyjf.cs.user.util.BankCommonUtil;
import com.hyjf.cs.user.vo.AuthVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * @Description
 * @Author dangzw
 * @Version v0.1
 * @Date
 */
@Api(tags = {"web端-二合一授权(缴费授权、还款授权)"})
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-wechat/bank/user/auth/payrepayauth")
public class WeChatPayRepayAuthController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(WeChatPayRepayAuthController.class);
    private static final String PAY_REPAY_AUTH = "/auth";
    private static final String PAY_REPAY_SEACH_AUTH = "/seachFiledMess";
    private static final String PAY_REPAY_BG_AUTH = "/payRepayAuthBgreturn";
    private static final String PAY_REPAY_CLASS_NAME = "/hyjf-wechat/bank/user/auth/payrepayauth";

    @Autowired
    private AuthService authService;

    @Autowired
    SystemConfig systemConfig;

    /**
     * 二合一授权(缴费授权、还款授权)
     * @param userId
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "二合一授权(缴费授权、还款授权)", notes = "二合一授权(缴费授权、还款授权)")
    @GetMapping(value = PAY_REPAY_AUTH, produces = "application/json; charset=utf-8")
    public  WebResult<Object> auth(@RequestHeader(value = "wjtClient",required = false) String wjtClient,
                                   @RequestHeader(value = "userId", required = false) Integer userId, HttpServletRequest request) {
        logger.info("缴费、还款二合一授权开始", "className："+ this.getClass().getName() + "methodPath："+ PAY_REPAY_CLASS_NAME + PAY_REPAY_AUTH);
        WebResult<Object> result = new WebResult<>();
        // 验证请求参数
        CheckUtil.check(userId != null,MsgEnum.ERR_USER_NOT_LOGIN);
        UserVO user = this.authService.getUsersById(userId);
        //检查用户信息
        checkUserMessage(user);

        // 拼装参数 调用江西银行
        // 同步地址  是否跳转到前端页面
        String host = BankCommonUtil.getFrontHost(systemConfig,CustomConstants.CLIENT_WECHAT);
        if(StringUtils.isNotBlank(wjtClient)){
            // 如果是温金投的  则跳转到温金投那边
            host = BankCommonUtil.getWjtFrontHost(systemConfig,wjtClient);
        }
        // 失败页面
        String errorPath = "/user/setting/repayauth/result/failed";
        // 成功页面
        String successPath = "/user/setting/repayauth/result/success";
        String orderId = GetOrderIdUtils.getOrderId2(userId);
        // 同步地址  是否跳转到前端页面
        String retUrl = host + errorPath +"?logOrdId="+orderId+"&authType="+AuthBean.AUTH_TYPE_PAY_REPAY_AUTH;
        String successUrl = host+ successPath+"?logOrdId="+orderId+"&authType="+AuthBean.AUTH_TYPE_PAY_REPAY_AUTH;
        String bgRetUrl = "http://CS-USER" + PAY_REPAY_CLASS_NAME + PAY_REPAY_BG_AUTH;

        // 忘记密码跳转链接
        String forgotPwdUrl = BankCommonUtil.getForgotPwdUrl(CommonConstant.CLIENT_WECHAT, null, systemConfig);
        if(StringUtils.isNotBlank(wjtClient)){
            // 如果是温金投的  则跳转到温金投那边
            forgotPwdUrl = BankCommonUtil.getWjtForgotPwdUrl(wjtClient, null, systemConfig);
        }

        UserInfoVO usersInfo = authService.getUserInfo(userId);
        BankOpenAccountVO bankOpenAccountVO = authService.getBankOpenAccount(userId);
        // 用户ID
        AuthBean authBean = new AuthBean();
        authBean.setUserId(user.getUserId());
        authBean.setIp(CustomUtil.getIpAddr(request));
        authBean.setAccountId(bankOpenAccountVO.getAccount());
        // 同步 异步
        authBean.setRetUrl(retUrl);
        authBean.setSuccessUrl(successUrl);
        authBean.setNotifyUrl(bgRetUrl);
        // 0：PC 1：微官网 2：Android 3：iOS 4：其他
        authBean.setPlatform(CustomConstants.CLIENT_WECHAT);
        authBean.setAuthType(AuthBean.AUTH_TYPE_PAY_REPAY_AUTH);
        authBean.setChannel(BankCallConstant.CHANNEL_WEI);
        authBean.setForgotPwdUrl(forgotPwdUrl);
        authBean.setName(usersInfo.getTruename());
        authBean.setIdNo(usersInfo.getIdcard());
        authBean.setIdentity(String.valueOf(usersInfo.getRoleId()));
        authBean.setUserType(user.getUserType());
        // 跳转到江西银行画面
        try {

            authBean.setOrderId(orderId);
            Map<String,Object> map = authService.getCallbankMV(authBean);
            String type;
            if(authBean.getPaymentAuthStatus() && authBean.getRepayAuthAuthStatus()){
                // 缴费授权、还款授权
                type = "15";
            }else if(authBean.getPaymentAuthStatus()){
                // 缴费授权
                type = "5";
            }else if(authBean.getRepayAuthAuthStatus()){
                // 还款授权
                type = "6";
            }else{
                // 缴费授权、还款授权
                type = "15";
            }
            authService.insertUserAuthLog(authBean.getUserId(), orderId, Integer.parseInt(authBean.getPlatform()), type);
            result.setData(map);
        } catch (Exception e) {
            logger.info("缴费、还款二合一授权、插入日志表异常",
                    "className："+ this.getClass().getName() + "methodPath："+ PAY_REPAY_CLASS_NAME + PAY_REPAY_AUTH,
                    e);
            throw new CheckException(MsgEnum.STATUS_CE999999);
        }
        logger.info("缴费、还款二合一授权结束", "className："+ this.getClass().getName() + "methodPath："+ PAY_REPAY_CLASS_NAME + PAY_REPAY_AUTH);
        return result;
    }

    private void checkUserMessage(UserVO user) {
        CheckUtil.check(user != null,MsgEnum.ERR_USER_NOT_EXISTS);
        // 判断用户是否开户
        CheckUtil.check(user.getBankOpenAccount() != 0,MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        // 判断用户是否设置过交易密码
        CheckUtil.check(user.getIsSetPassword() != 0,MsgEnum.ERR_TRADE_PASSWORD_NOT_SET);
        // 判断是否授权过
        CheckUtil.check(!authService.checkIsAuth(user.getUserId(), AuthBean.AUTH_TYPE_PAY_REPAY_AUTH),MsgEnum.ERR_AUTH_REPEAT);
    }

    /**
     * 缴费、还款二合一授权异步回调
     * @param bean
     * @return
     */
    @ApiOperation(value = "缴费、还款二合一授权异步回调", notes = "缴费、还款二合一授权异步回调")
    @PostMapping(value = PAY_REPAY_BG_AUTH)
    @ResponseBody
    public String mergeAuthBgreturn(@RequestBody BankCallBean bean) {
        BankCallResult result = new BankCallResult();
        logger.info("缴费、还款二合一授权[异步回调]开始", "className："+ this.getClass().getName() + "methodPath："+ PAY_REPAY_CLASS_NAME + PAY_REPAY_BG_AUTH);
        bean.convert();
        Integer userId = Integer.parseInt(bean.getLogUserId()); // 用户ID
        // 查询用户开户状态
        UserVO user = this.authService.getUsersById(userId);
        if(authService.checkDefaultConfig(bean, AuthBean.AUTH_TYPE_PAY_REPAY_AUTH)){

            authService.updateUserAuthLog(bean.getLogOrderId(),"QuotaError");
            logger.info("缴费、还款二合一授权[异步回调]结束");
            result.setMessage("缴费、还款二合一授权成功");
            result.setStatus(true);
            return JSONObject.toJSONString(result, true);
        }
        // 成功
        if (user != null && bean != null
                && (BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE)))) {
            try {
                bean.setOrderId(bean.getLogOrderId());
                // 更新签约状态和日志表
                this.authService.updateUserAuth(Integer.parseInt(bean.getLogUserId()), bean, AuthBean.AUTH_TYPE_PAY_REPAY_AUTH);
            } catch (Exception e) {
                logger.info("缴费、还款二合一授权[异步回调]更新签约状态和日志表异常！",
                        "className："+ this.getClass().getName() + "methodPath："+ PAY_REPAY_CLASS_NAME + PAY_REPAY_BG_AUTH,
                        e);
                authService.updateUserAuthLog(bean.getLogOrderId(),authService.getBankRetMsg(bean.getRetCode()));
            }
        }else{
            authService.updateUserAuthLog(bean.getLogOrderId(),authService.getBankRetMsg(bean.getRetCode()));
        }
        logger.info("缴费、还款二合一授权[异步回调]结束", "className："+ this.getClass().getName() + "methodPath："+ PAY_REPAY_CLASS_NAME + PAY_REPAY_BG_AUTH);
        result.setMessage("缴费、还款二合一授权成功");
        result.setStatus(true);
        return JSONObject.toJSONString(result, true);
    }

    /**
     * wechat端-用户缴费、还款二合一授权失败原因查询
     * @param vo
     * @return
     */
    @ApiOperation(value = "缴费、还款二合一授权失败原因查询", notes = "缴费、还款二合一授权失败原因查询")
    @PostMapping(PAY_REPAY_SEACH_AUTH)
    @ResponseBody
    public WebResult<Object> seachUserAuthErrorMessgae(@RequestBody @Valid AuthVO vo) {
        String logOrdId = vo.getLogOrdId();
        logger.info("缴费、还款二合一授权失败原因查询[开始],订单号:[" + logOrdId + "].");
        WebResult<Object> result = authService.seachUserAuthErrorMessgae(logOrdId);
        logger.info("缴费、还款二合一授权失败原因查询[结束]");
        return result;
    }

}
