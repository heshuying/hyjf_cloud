/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.web.auth.mergeauth;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.AuthorizedVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.bean.AuthBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.auth.AuthService;
import com.hyjf.cs.user.service.autoplus.AutoPlusService;
import com.hyjf.cs.user.vo.AuthVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
@Api(tags = {"web端-多合一授权（新）"})
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-web/user/auth/mergeauthpageplus")
public class MergeAuthPagePlusController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(MergeAuthPagePlusController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    SystemConfig systemConfig;

    /**
     * 用户合并授权
     * @param userId
     * @return
     */
    @ApiOperation(value = "用户合并授权", notes = "用户合并授权")
    @PostMapping(value = "/page", produces = "application/json; charset=utf-8")
    public  WebResult<Object> page(@RequestHeader(value = "userId") Integer userId, HttpServletRequest request) {
        WebResult<Object> result = new WebResult<Object>();
        // 验证请求参数
        CheckUtil.check(userId != null,MsgEnum.ERR_USER_NOT_LOGIN);
        UserVO user = this.authService.getUsersById(userId);

        //检查用户信息
        checkUserMessage(user);

        // 拼装参数 调用江西银行
        // 失败页面
        String errorPath = "/user/autoplus/autoTenderFail";
        // 成功页面
        String successPath = "/user/autoplus/autoTenderSuccess";
        String orderId = GetOrderIdUtils.getOrderId2(userId);
        // 同步地址  是否跳转到前端页面
        String retUrl = super.getFrontHost(systemConfig,CustomConstants.CLIENT_PC) + errorPath +"?logOrdId="+orderId+"&authType="+AuthBean.AUTH_TYPE_MERGE_AUTH;
        String successUrl = super.getFrontHost(systemConfig,CustomConstants.CLIENT_PC) + successPath+"?logOrdId="+orderId+"&authType="+AuthBean.AUTH_TYPE_MERGE_AUTH;
        String bgRetUrl = "http://CS-USER/hyjf-web/user/auth/mergeauthpageplus/mergeAuthBgreturn" ;

        UserInfoVO usersInfo = authService.getUserInfo(userId);
        BankOpenAccountVO bankOpenAccountVO=authService.getBankOpenAccount(userId);
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
        authBean.setAuthType(AuthBean.AUTH_TYPE_MERGE_AUTH);
        authBean.setChannel(BankCallConstant.CHANNEL_PC);
        authBean.setForgotPwdUrl(super.getForgotPwdUrl(CustomConstants.CLIENT_PC,request,systemConfig));
        authBean.setName(usersInfo.getTruename());
        authBean.setIdNo(usersInfo.getIdcard());
        authBean.setIdentity(usersInfo.getRoleId() + "");
        authBean.setUserType(user.getUserType());
        // 跳转到江西银行画面
        try {

            authBean.setOrderId(orderId);
            Map<String,Object> map = authService.getCallbankMV(authBean);
            if(authBean.getAutoBidStatus()&&authBean.getAutoCreditStatus()&&authBean.getPaymentAuthStatus()){
                //开通自动出借、自动债转、缴费授权
                authService.insertUserAuthLog(authBean.getUserId(), orderId, Integer.parseInt(authBean.getPlatform()), "14");
            }else if(authBean.getAutoBidStatus()&&authBean.getAutoCreditStatus()){
                //开通自动出借、自动债转
                authService.insertUserAuthLog(authBean.getUserId(), orderId, Integer.parseInt(authBean.getPlatform()), "11");
            }else if(authBean.getAutoBidStatus()&&authBean.getPaymentAuthStatus()){
                //开通自动出借、缴费授权
                authService.insertUserAuthLog(authBean.getUserId(), orderId, Integer.parseInt(authBean.getPlatform()), "12");
            }else if(authBean.getPaymentAuthStatus()&&authBean.getAutoCreditStatus()){
                //开通自动债转、缴费授权
                authService.insertUserAuthLog(authBean.getUserId(), orderId, Integer.parseInt(authBean.getPlatform()), "13");
            }else if(authBean.getAutoBidStatus()){
                //开通自动出借
                authService.insertUserAuthLog(authBean.getUserId(), orderId, Integer.parseInt(authBean.getPlatform()), "1");
            }else if(authBean.getAutoCreditStatus()){
                //开通自动债转
                authService.insertUserAuthLog(authBean.getUserId(), orderId, Integer.parseInt(authBean.getPlatform()), "4");
            }else if(authBean.getPaymentAuthStatus()){
                //开通缴费授权
                authService.insertUserAuthLog(authBean.getUserId(), orderId, Integer.parseInt(authBean.getPlatform()), "5");
            }else{
                //开通自动出借、自动债转、缴费授权
                authService.insertUserAuthLog(authBean.getUserId(), orderId, Integer.parseInt(authBean.getPlatform()), "14");
            }
            result.setData(map);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CheckException(MsgEnum.STATUS_CE999999);
        }

        return result;
    }

    private void checkUserMessage(UserVO user) {
        CheckUtil.check(user != null,MsgEnum.ERR_USER_NOT_EXISTS);
        // 判断用户是否开户
        CheckUtil.check(user.getBankOpenAccount() != 0,MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        // 判断用户是否设置过交易密码
        CheckUtil.check(user.getIsSetPassword() != 0,MsgEnum.ERR_TRADE_PASSWORD_NOT_SET);
        // 判断是否授权过
        CheckUtil.check(!authService.checkIsAuth(user.getUserId(), AuthBean.AUTH_TYPE_MERGE_AUTH),MsgEnum.ERR_AUTHORIZE_REPEAT);
    }

    /**
     * 用户合并授权异步回调
     * @param bean
     * @return
     */
    @ApiOperation(value = "用户合并授权异步回调", notes = "用户合并授权异步回调")
    @PostMapping(value = "/mergeAuthBgreturn")
    @ResponseBody
    public String mergeAuthBgreturn(@RequestBody BankCallBean bean) {
        BankCallResult result = new BankCallResult();
        logger.info("[合并授权异步回调开始]");
        bean.convert();
        Integer userId = Integer.parseInt(bean.getLogUserId()); // 用户ID
        // 查询用户开户状态
        UserVO user = this.authService.getUsersById(userId);
        if(authService.checkDefaultConfig(bean, AuthBean.AUTH_TYPE_MERGE_AUTH)){

            authService.updateUserAuthLog(bean.getLogOrderId(),"QuotaError");
            logger.info("[用户合并授权完成后,回调结束]");
            result.setMessage("合并授权成功");
            result.setStatus(true);
            return JSONObject.toJSONString(result, true);
        }
        // 成功
        if (user != null && bean != null
                && (BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE)))) {
            try {
                bean.setOrderId(bean.getLogOrderId());
                // 更新签约状态和日志表
                this.authService.updateUserAuth(Integer.parseInt(bean.getLogUserId()), bean, AuthBean.AUTH_TYPE_MERGE_AUTH);
            } catch (Exception e) {
                logger.error(e.getMessage());
                authService.updateUserAuthLog(bean.getLogOrderId(),authService.getBankRetMsg(bean.getRetCode()));
            }
        }else{
            authService.updateUserAuthLog(bean.getLogOrderId(),authService.getBankRetMsg(bean.getRetCode()));
        }
        logger.info("[用户合并授权完成后,回调结束]");
        result.setMessage("合并授权成功");
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
    public WebResult<Object> seachUserAuthErrorMessgae(@RequestBody @Valid AuthVO vo) {
        logger.info("查询授权失败原因start,logOrdId:{}", vo.getLogOrdId());
        WebResult<Object> result = authService.seachUserAuthErrorMessgae(vo.getLogOrdId());
        return result;
    }

}
