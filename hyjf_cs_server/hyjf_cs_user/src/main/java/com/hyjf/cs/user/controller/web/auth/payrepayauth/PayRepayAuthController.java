/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.web.auth.payrepayauth;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.StringUtil;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.bean.AuthBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.auth.AuthService;
import com.hyjf.cs.user.vo.AuthVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/hyjf-web/user/auth/payrepayauth")
public class PayRepayAuthController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(PayRepayAuthController.class);
    private static final String PAY_REPAY_AUTH = "/auth";
    private static final String PAY_REPAY_SEACH_AUTH = "/seachFiledMess";
    private static final String PAY_REPAY_BG_AUTH = "/payRepayAuthBgreturn";
    private static final String PAY_REPAY_CLASS_NAME = "/hyjf-web/user/auth/payrepayauth";

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    private AuthService authService;

    /**
     * 二合一授权(缴费授权、还款授权)
     * @param userId
     * @return
     */
    @ApiOperation(value = "二合一授权(缴费授权、还款授权)", notes = "二合一授权(缴费授权、还款授权)")
    @PostMapping(value = PAY_REPAY_AUTH, produces = "application/json; charset=utf-8")
    public  WebResult<Object> auth(@RequestHeader(value = "userId") Integer userId, HttpServletRequest request) {
        logger.info("缴费、还款二合一授权开始------------------------------接口路径:{}", PAY_REPAY_CLASS_NAME + PAY_REPAY_AUTH);
        WebResult<Object> result = new WebResult<>();
        // 验证请求参数
        CheckUtil.check(userId != null,MsgEnum.ERR_USER_NOT_LOGIN);
        UserVO user = this.authService.getUsersById(userId);

        //检查用户信息
        checkUserMessage(user);

        // 拼装参数 调用江西银行
        // 生成唯一订单号
        String orderId = GetOrderIdUtils.getOrderId2(userId);
        // 成功同步调用路径
        String successPath = "/user/autoplus/autoTenderSuccess";
        String successUrl = super.getFrontHost(systemConfig,CustomConstants.CLIENT_PC) + successPath+"?logOrdId="
                + orderId + "&authType="
                + AuthBean.AUTH_TYPE_PAY_REPAY_AUTH;
        // 失败同步调用路径
        String errorPath = "/user/autoplus/autoTenderFail";
        String retUrl = super.getFrontHost(systemConfig,CustomConstants.CLIENT_PC) + errorPath+"?logOrdId="
                + orderId + "&authType="
                + AuthBean.AUTH_TYPE_PAY_REPAY_AUTH;
        String bgRetUrl = "http://CS-USER" + PAY_REPAY_CLASS_NAME + PAY_REPAY_BG_AUTH;

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
        authBean.setPlatform(CustomConstants.CLIENT_PC);
        authBean.setAuthType(AuthBean.AUTH_TYPE_PAY_REPAY_AUTH);
        authBean.setChannel(BankCallConstant.CHANNEL_PC);
        authBean.setForgotPwdUrl(super.getForgotPwdUrl(CustomConstants.CLIENT_PC,request,systemConfig));
        authBean.setName(usersInfo.getTruename());
        authBean.setIdNo(usersInfo.getIdcard());
        authBean.setIdentity(StringUtil.valueOf(usersInfo.getRoleId()));
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
            logger.info("缴费、还款二合一授权、插入日志表异常,异常报文如下:{}", e);
            throw new CheckException(MsgEnum.STATUS_CE999999);
        }
        logger.info("缴费、还款二合一授权结束------------------------------");
        return result;
    }

    private void checkUserMessage(UserVO user) {
        CheckUtil.check(user != null,MsgEnum.ERR_USER_NOT_EXISTS);
        // 判断用户是否开户
        CheckUtil.check(user.getBankOpenAccount() != 0,MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        // 判断用户是否设置过交易密码
        CheckUtil.check(user.getIsSetPassword() != 0,MsgEnum.ERR_TRADE_PASSWORD_NOT_SET);
        // 判断是否授权过
        CheckUtil.check(!authService.checkIsAuth(user.getUserId(), AuthBean.AUTH_TYPE_PAY_REPAY_AUTH),MsgEnum.ERR_AUTHORIZE_REPEAT);
    }

    /**
     * 缴费、还款二合一授权异步回调
     * @param bean
     * @return
     */
    @ApiOperation(value = "缴费、还款二合一授权异步回调", notes = "缴费、还款二合一授权异步回调")
    @PostMapping(value = PAY_REPAY_BG_AUTH)
    @ResponseBody
    public String payRepayAuthBgreturn(@RequestBody BankCallBean bean) {
        BankCallResult result = new BankCallResult();
        logger.info("缴费、还款二合一授权[异步回调]开始------------------------------接口路径:{}", PAY_REPAY_CLASS_NAME + PAY_REPAY_BG_AUTH);
        bean.convert();
        // 用户ID
        Integer userId = Integer.parseInt(bean.getLogUserId());
        // 查询用户开户状态
        UserVO user = this.authService.getUsersById(userId);
        if(authService.checkDefaultConfig(bean, AuthBean.AUTH_TYPE_PAY_REPAY_AUTH)){

            authService.updateUserAuthLog(bean.getLogOrderId(),"QuotaError");
            logger.info("缴费、还款二合一授权[异步回调]结束");
            result.setStatus(true);
            return JSONObject.toJSONString(result, true);
        }

        if (user != null
            && bean != null
            && (BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE)))
            ) {
                try {
                    bean.setOrderId(bean.getLogOrderId());
                    // 更新授权状态和日志表
                    this.authService.updateUserAuth(Integer.parseInt(bean.getLogUserId()), bean, AuthBean.AUTH_TYPE_PAY_REPAY_AUTH);
                    logger.info("缴费、还款二合一授权成功");
                } catch (Exception e) {
                    logger.error("缴费、还款二合一授权[异步回调]更新签约状态和日志表异常,异常报文如下:{}", e);
                    authService.updateUserAuthLog(bean.getLogOrderId(), "缴费、还款二合一授权[异步回调]更新授权状态和日志表异常");
                }
            }else{
                logger.info("缴费、还款二合一授权失败,银行返回码:{},失败原因:{}", bean.getRetCode(), authService.getBankRetMsg(bean.getRetCode()));
                authService.updateUserAuthLog(bean.getLogOrderId(),authService.getBankRetMsg(bean.getRetCode()));
            }
        logger.info("缴费、还款二合一授权[异步回调]结束------------------------------");
        result.setStatus(true);
        return JSONObject.toJSONString(result, true);
    }

    /**
     * web端-用户缴费、还款二合一授权失败原因查询
     * @param vo
     * @return
     */
    @ApiOperation(value = "缴费、还款二合一授权失败原因查询", notes = "缴费、还款二合一授权失败原因查询")
    @PostMapping(PAY_REPAY_SEACH_AUTH)
    @ResponseBody
    public WebResult<Object> seachUserAuthErrorMessgae(@RequestBody @Valid AuthVO vo) {
        String orderId = vo.getLogOrdId();
        logger.info("缴费、还款二合一授权失败原因查询[开始],orderId:{}------------------------------", orderId);
        WebResult<Object> result = authService.seachUserAuthErrorMessgae(orderId);
        logger.info("缴费、还款二合一授权失败原因查询[结束]------------------------------");
        return result;
    }

}
