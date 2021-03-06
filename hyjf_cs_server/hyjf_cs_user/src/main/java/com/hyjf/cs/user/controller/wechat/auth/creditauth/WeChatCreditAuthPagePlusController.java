/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.auth.creditauth;

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
 * @Author pangchengchao
 * @Version v0.1
 * @Date
 */
@Api(tags = {"WECHAT-自动债转授权"})
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-wechat/bank/user/auth/creditauthpageplus")
public class WeChatCreditAuthPagePlusController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(WeChatCreditAuthPagePlusController.class);
    private static final String WECHAT_CREDIT_AUTH = "/page";
    private static final String WECHAT_CREDIT_SEACH_AUTH = "/seachFiledMess";
    private static final String WECHAT_CREDIT_BG_AUTH = "/creditAuthBgreturn";
    private static final String WECHAT_CREDIT_CLASS_NAME = "/hyjf-app/bank/user/auth/creditauthpageplus";

    @Autowired
    private AuthService authService;

    @Autowired
    SystemConfig systemConfig;

    /**
     * WECHAT-自动债转授权
     * @param userId
     * @return
     */
    @ApiOperation(value = "WECHAT-自动债转授权", notes = "WECHAT-自动债转授权")
    @GetMapping(value = WECHAT_CREDIT_AUTH, produces = "application/json; charset=utf-8")
    @ResponseBody
    public  WebResult<Object> page(@RequestHeader(value = "wjtClient",required = false) String wjtClient,
                                   @RequestHeader(value = "userId") Integer userId, HttpServletRequest request) {
        WebResult<Object> result = new WebResult<Object>();
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
        String errorPath = "/user/setting/authorization/result/failed";
        // 成功页面
        String successPath = "/user/setting/authorization/result/success";
        String orderId = GetOrderIdUtils.getOrderId2(userId);
        // 同步地址  是否跳转到前端页面
        String retUrl = host + errorPath +"?logOrdId="+orderId+"&authType="+AuthBean.AUTH_TYPE_AUTO_CREDIT;
        String successUrl = host + successPath+"?logOrdId="+orderId+"&authType="+AuthBean.AUTH_TYPE_AUTO_CREDIT;
        String bgRetUrl = "http://CS-USER/hyjf-wechat/bank/user/auth/creditauthpageplus/creditAuthBgreturn" ;

        // 忘记密码跳转链接
        String forgotPwdUrl = BankCommonUtil.getForgotPwdUrl(CommonConstant.CLIENT_WECHAT, null, systemConfig);
        if(StringUtils.isNotBlank(wjtClient)){
            // 如果是温金投的  则跳转到温金投那边
            forgotPwdUrl = BankCommonUtil.getWjtForgotPwdUrl(wjtClient, null, systemConfig);
        }

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
        authBean.setPlatform(CustomConstants.CLIENT_WECHAT);
        authBean.setAuthType(AuthBean.AUTH_TYPE_AUTO_CREDIT);
        authBean.setChannel(BankCallConstant.CHANNEL_WEI);
        authBean.setForgotPwdUrl(forgotPwdUrl);
        authBean.setName(usersInfo.getTruename());
        authBean.setIdNo(usersInfo.getIdcard());
        authBean.setIdentity(usersInfo.getRoleId() + "");
        authBean.setUserType(user.getUserType());
        // 跳转到江西银行画面
        try {
            authBean.setOrderId(orderId);
            Map<String,Object> map = authService.getCallbankMV(authBean);
            authService.insertUserAuthLog(authBean.getUserId(), orderId,Integer.parseInt(authBean.getPlatform()), "4");
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
        CheckUtil.check(!authService.checkIsAuth(user.getUserId(), AuthBean.AUTH_TYPE_AUTO_CREDIT),MsgEnum.ERR_AUTH_REPEAT);
    }

    /**
     * 用户自动债转授权异步回调
     * @param bean
     * @return
     */
    @ApiOperation(value = "用户自动债转授权异步回调", notes = "用户自动债转授权异步回调")
    @PostMapping(value = "/creditAuthBgreturn")
    @ResponseBody
    public String creditAuthBgreturn(@RequestBody BankCallBean bean) {
        BankCallResult result = new BankCallResult();
        logger.info("[用户自动债转授权回调开始]");
        bean.convert();
        Integer userId = Integer.parseInt(bean.getLogUserId()); // 用户ID
        // 查询用户开户状态
        UserVO user = this.authService.getUsersById(userId);
        if(authService.checkDefaultConfig(bean, AuthBean.AUTH_TYPE_AUTO_CREDIT)){

            authService.updateUserAuthLog(bean.getLogOrderId(),"QuotaError");
            logger.info("[用户自动债转授权完成后,回调结束]");
            result.setMessage("自动债转授权成功");
            result.setStatus(true);
            return JSONObject.toJSONString(result, true);
        }
        // 成功
        if (user != null && bean != null
                && (BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))
                && "1".equals(bean.getAutoCredit()))) {
            try {
                bean.setOrderId(bean.getLogOrderId());
                // 更新签约状态和日志表
                this.authService.updateUserAuth(Integer.parseInt(bean.getLogUserId()), bean, AuthBean.AUTH_TYPE_AUTO_CREDIT);
            } catch (Exception e) {
                logger.error(e.getMessage());
                authService.updateUserAuthLog(bean.getLogOrderId(),authService.getBankRetMsg(bean.getRetCode()));
            }
        }else{
            authService.updateUserAuthLog(bean.getLogOrderId(),authService.getBankRetMsg(bean.getRetCode()));
        }
        logger.info("[用户自动债转授权完成后,回调结束]");
        result.setMessage("自动债转授权成功");
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
