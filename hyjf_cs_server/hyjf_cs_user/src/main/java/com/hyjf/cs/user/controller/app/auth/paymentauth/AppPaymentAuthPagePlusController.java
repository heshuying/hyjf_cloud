/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.auth.paymentauth;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.AuthorizedVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.bean.AuthBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.auth.AuthService;
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
 * @Author pangchengchao
 * @Version v0.1
 * @Date
 */
@Api(tags = {"app端-缴费授权（新）"})
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-app/bank/user/auth/paymentauthpageplus")
public class AppPaymentAuthPagePlusController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(AppPaymentAuthPagePlusController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    SystemConfig systemConfig;

    /**
     * 用户还款授权
     * @param userId
     * @param authorizedVO
     * @return
     */
    @ApiOperation(value = "用户缴费授权", notes = "用户缴费授权")
    @PostMapping(value = "/page", produces = "application/json; charset=utf-8")
    @ResponseBody
    public  WebResult<Object> page(@RequestHeader(value = "userId") Integer userId, HttpServletRequest request) {
        WebResult<Object> result = new WebResult<Object>();
        // 验证请求参数
        CheckUtil.check(userId != null,MsgEnum.ERR_USER_NOT_LOGIN);
        UserVO user = this.authService.getUsersById(userId);
        String platform = request.getParameter("platform");
        //检查用户信息
        checkUserMessage(user);

        // 拼装参数 调用江西银行
        // 失败页面
        String errorPath = "/user/setting/paymentauth/result/failed";
        // 成功页面
        String successPath = "/user/setting/paymentauth/result/success";
        String orderId = GetOrderIdUtils.getOrderId2(userId);
        // 同步地址  是否跳转到前端页面
        String retUrl = super.getFrontHost(systemConfig,platform) + errorPath +"?logOrdId="+orderId+"&authType="+AuthBean.AUTH_TYPE_PAYMENT_AUTH;
        String successUrl = super.getFrontHost(systemConfig,platform) + successPath;
        String bgRetUrl = "http://CS-USER/hyjf-app/bank/user/auth/paymentauthpageplus/paymentauthBgreturn" ;

        UserInfoVO usersInfo = authService.getUserInfo(userId);
        BankOpenAccountVO bankOpenAccountVO=authService.getBankOpenAccount(userId);
        // 用户ID
        AuthBean authBean = new AuthBean();
        authBean.setUserId(user.getUserId());
        authBean.setIp(CustomUtil.getIpAddr(request));
        authBean.setAccountId(bankOpenAccountVO.getAccount());
        // 同步 异步
        authBean.setRetUrl(splicingParam(retUrl,request));
        authBean.setSuccessUrl(splicingParam(successUrl,request));
        authBean.setNotifyUrl(splicingParam(bgRetUrl,request));
        // 0：PC 1：微官网 2：Android 3：iOS 4：其他
        authBean.setPlatform(platform);
        authBean.setAuthType(AuthBean.AUTH_TYPE_PAYMENT_AUTH);
        authBean.setChannel(BankCallConstant.CHANNEL_APP);
        authBean.setForgotPwdUrl(super.getForgotPwdUrl(platform,request,systemConfig));
        authBean.setName(usersInfo.getTruename());
        authBean.setIdNo(usersInfo.getIdcard());
        authBean.setIdentity(usersInfo.getRoleId() + "");
        authBean.setUserType(user.getUserType());
        // 跳转到江西银行画面
        try {

            authBean.setOrderId(orderId);
            Map<String,Object> map = authService.getCallbankMV(authBean);
            //开通缴费授权
            authService.insertUserAuthLog(authBean.getUserId(), orderId, Integer.parseInt(authBean.getPlatform()), "5");
            result.setData(map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CheckException(MsgEnum.STATUS_CE999999);
        }

        return result;
    }
    private String splicingParam(String bgRetUrl, HttpServletRequest request) {
        String sign=request.getParameter("sign");
        String token=request.getParameter("token");
        StringBuffer sb = new StringBuffer(bgRetUrl);

        if(bgRetUrl.indexOf("?")!=-1){
            sb.append("&sign=").append(sign);
        }else{
            sb.append("?sign=").append(sign);
        }
        return sb.toString();
    }
    private void checkUserMessage(UserVO user) {
        CheckUtil.check(user != null,MsgEnum.ERR_USER_NOT_EXISTS);
        // 判断用户是否开户
        CheckUtil.check(user.getBankOpenAccount() != 0,MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        // 判断用户是否设置过交易密码
        CheckUtil.check(user.getIsSetPassword() != 0,MsgEnum.ERR_TRADE_PASSWORD_NOT_SET);
        // 判断是否授权过
        CheckUtil.check(!authService.checkIsAuth(user.getUserId(), AuthBean.AUTH_TYPE_PAYMENT_AUTH),MsgEnum.STATUS_CE000009);
    }

    /**
     * 用户缴费授权异步回调
     * @param bean
     * @return
     */
    @ApiOperation(value = "用户缴费授权异步回调", notes = "用户缴费授权异步回调")
    @PostMapping(value = "/paymentauthBgreturn")
    @ResponseBody
    public String paymentAuthBgreturn(@RequestBody BankCallBean bean) {
        BankCallResult result = new BankCallResult();
        logger.info("[用户缴费授权回调开始]");
        bean.convert();
        Integer userId = Integer.parseInt(bean.getLogUserId()); // 用户ID
        // 查询用户开户状态
        UserVO user = this.authService.getUsersById(userId);
        if(authService.checkDefaultConfig(bean, AuthBean.AUTH_TYPE_PAYMENT_AUTH)){

            authService.updateUserAuthLog(bean.getLogOrderId(),"QuotaError");
            logger.info("[用户缴费授权完成后,回调结束]");
            result.setMessage("缴费授权成功");
            result.setStatus(true);
            return JSONObject.toJSONString(result, true);
        }
        // 成功
        if (user != null && bean != null
                && (BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))
                && "1".equals(bean.getPaymentAuth()))) {
            try {
                bean.setOrderId(bean.getLogOrderId());
                // 更新签约状态和日志表
                this.authService.updateUserAuth(Integer.parseInt(bean.getLogUserId()), bean, AuthBean.AUTH_TYPE_PAYMENT_AUTH);
            } catch (Exception e) {
                e.printStackTrace();
                authService.updateUserAuthLog(bean.getLogOrderId(),authService.getBankRetMsg(bean.getRetCode()));
            }
        }else{
            authService.updateUserAuthLog(bean.getLogOrderId(),authService.getBankRetMsg(bean.getRetCode()));
        }
        logger.info("[用户缴费授权完成后,回调结束]");
        result.setMessage("缴费授权成功");
        result.setStatus(true);
        return JSONObject.toJSONString(result, true);
    }

    /**
     * @Description web端查询提现失败原因
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @ApiOperation(value = "查询授权失败原因", notes = "查询授权失败原因")
    @PostMapping("/seachFiledMess")
    @ResponseBody
    public WebResult<Object> seachUserAuthErrorMessgae(HttpServletRequest request) {
        String logOrdId=request.getParameter("logOrdId");
        logger.info("查询授权失败原因start,logOrdId:{}", logOrdId);
        WebResult<Object> result = authService.seachUserAuthErrorMessgae(logOrdId);
        return result;
    }

}
