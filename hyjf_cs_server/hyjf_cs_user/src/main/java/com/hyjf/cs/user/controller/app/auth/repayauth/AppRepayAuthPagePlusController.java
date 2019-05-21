/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.auth.repayauth;

import com.alibaba.fastjson.JSONObject;
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
import com.hyjf.cs.user.util.BankCommonUtil;
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
import java.util.Map;

/**
 * @Description 
 * @Author pangchengchao
 * @Version v0.1
 * @Date
 */
@Api(tags = {"app端-还款授权（新）"})
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-app/bank/user/auth/repayauthpageplus")
public class AppRepayAuthPagePlusController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(AppRepayAuthPagePlusController.class);
    private static final String APP_REPAY_AUTH = "/page";
    private static final String APP_REPAY_SEACH_AUTH = "/seachFiledMess";
    private static final String APP_REPAY_BG_AUTH = "/repayauthBgreturn";
    private static final String APP_REPAY_CLASS_NAME = "/hyjf-app/bank/user/auth/repayauthpageplus";

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    private AuthService authService;

    /**
     * APP-还款授权
     * @param userId
     * @return
     */
    @ApiOperation(value = "APP-还款授权", notes = "APP-还款授权")
    @GetMapping(value = APP_REPAY_AUTH, produces = "application/json; charset=utf-8")
    public  WebResult<Object> page(@RequestHeader(value = "userId") Integer userId, HttpServletRequest request) {
        logger.info("APP-还款授权开始,"+
                            "className:["+ this.getClass().getName() +"],"+
                            "methodPath:["+ APP_REPAY_CLASS_NAME + APP_REPAY_AUTH +"]");

        WebResult<Object> result = new WebResult<>();
        // 验证请求参数
        CheckUtil.check(userId != null,MsgEnum.ERR_USER_NOT_LOGIN);

        UserVO user = this.authService.getUsersById(userId);
        String platform = request.getParameter("platform");

        //检查用户信息
        checkUserMessage(user);

        ///拼装调用江西银行的参数
        //生成orderId
        String orderId = GetOrderIdUtils.getOrderId2(userId);

        // 成功页面
        String successPath = "/user/setting/repayauth/result/success";
        // 失败页面
        String errorPath = "/user/setting/repayauth/result/failed";

        //同步地址
        String retUrl = BankCommonUtil.getFrontHost(systemConfig,platform)+errorPath+"?logOrdId="+orderId+"&authType="+AuthBean.AUTH_TYPE_REPAY_AUTH+"&platform="+platform;
        String successUrl = BankCommonUtil.getFrontHost(systemConfig,platform)+successPath+"?logOrdId="+orderId+"&authType="+AuthBean.AUTH_TYPE_REPAY_AUTH+"&platform="+platform;
        //异步地址
        String bgRetUrl = "http://CS-USER"+APP_REPAY_CLASS_NAME+APP_REPAY_BG_AUTH;

        UserInfoVO usersInfo = authService.getUserInfo(userId);
        BankOpenAccountVO bankOpenAccountVO = authService.getBankOpenAccount(userId);

        AuthBean authBean = new AuthBean();
        // 用户ID
        authBean.setUserId(user.getUserId());
        authBean.setIp(CustomUtil.getIpAddr(request));
        authBean.setAccountId(bankOpenAccountVO.getAccount());
        //同步路径
        authBean.setRetUrl(splicingParam(retUrl,request));
        authBean.setSuccessUrl(splicingParam(successUrl,request));
        //异步路径
        authBean.setNotifyUrl(splicingParam(bgRetUrl,request));
        // 0：PC 1：微官网 2：Android 3：iOS 4：其他
        authBean.setPlatform(platform);
        authBean.setAuthType(AuthBean.AUTH_TYPE_REPAY_AUTH);
        authBean.setChannel(BankCallConstant.CHANNEL_APP);
        authBean.setForgotPwdUrl(BankCommonUtil.getForgotPwdUrl(platform, request.getParameter("sign"), systemConfig));
        authBean.setName(usersInfo.getTruename());
        authBean.setIdNo(usersInfo.getIdcard());
        authBean.setIdentity(String.valueOf(usersInfo.getRoleId()));
        authBean.setUserType(user.getUserType());
        authBean.setOrderId(orderId);
        try {
            //请求银行，得到数据
            Map<String,Object> map = authService.getCallbankMV(authBean);
            authService.insertUserAuthLog(authBean.getUserId(), orderId,Integer.parseInt(authBean.getPlatform()), "6");
            result.setData(map);
        } catch (Exception e) {
            logger.info("APP-还款授权 请求银行 或 插入授权日志表ht_hjh_user_auth_log 异常,详情如下:["+ e +"]");
            throw new CheckException(MsgEnum.STATUS_CE999999);
        }

        logger.info("APP-还款授权开始,"+
                            "className:["+ this.getClass().getName() +"],"+
                            "methodPath:["+ APP_REPAY_CLASS_NAME + APP_REPAY_AUTH +"]");
        return result;
    }

    /**
     * APP-还款授权异步回调
     * @param bean
     * @return
     */
    @ApiOperation(value = "APP-还款授权异步回调", notes = "APP-还款授权异步回调")
    @PostMapping(APP_REPAY_BG_AUTH)
    public String repayauthBgreturn(@RequestBody BankCallBean bean) {
        logger.info("APP-还款授权[异步回调]开始,"+
                            "className:["+ this.getClass().getName() +"],"+
                            "methodPath:["+ APP_REPAY_CLASS_NAME + APP_REPAY_BG_AUTH +"]");

        BankCallResult result = new BankCallResult();
        //包装信息到map, 用作请求银行
        bean.convert();
        //用户ID
        Integer userId = Integer.parseInt(bean.getLogUserId());
        // 查询用户开户状态
        UserVO user = this.authService.getUsersById(userId);

        //校验用户是否在银行页面修改授权信息
        //是, 跳转 "授权期限过短或额度过低,请重新授权" 异常提示页面, 并通知银行已接收异步请求
        if(authService.checkDefaultConfig(bean, AuthBean.AUTH_TYPE_REPAY_AUTH)){
            authService.updateUserAuthLog(bean.getLogOrderId(),"QuotaError");
            logger.info("APP-还款授权[异步回调]结束, 用户 授权期限过短或额度过低,请重新授权");
            result.setMessage("还款授权成功");
            result.setStatus(true);
            return JSONObject.toJSONString(result, true);
        }
        //否, 判断授权信息，保存数据
        if (user != null &&
            bean != null &&
            (BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))&& "1".equals(bean.getRepayAuth()))
            ) {
            try {
                bean.setOrderId(bean.getLogOrderId());
                //更新签约状态和日志表  ht_hjh_user_auth、ht_hjh_user_auth_log
                this.authService.updateUserAuth(Integer.parseInt(bean.getLogUserId()), bean, AuthBean.AUTH_TYPE_REPAY_AUTH);
            } catch (Exception e) {
                logger.info("APP-还款授权[异步回调], 更新签约状态和日志表异常");
                //表 ht_hjh_user_auth_log
                authService.updateUserAuthLog(bean.getLogOrderId(),authService.getBankRetMsg(bean.getRetCode()));
            }
        }else{
            //表 ht_hjh_user_auth_log
            authService.updateUserAuthLog(bean.getLogOrderId(),authService.getBankRetMsg(bean.getRetCode()));
        }

        logger.info("APP-还款授权[异步回调]结束,"+
                                "className:["+ this.getClass().getName() +"],"+
                                "methodPath:["+ APP_REPAY_CLASS_NAME + APP_REPAY_BG_AUTH +"]");
        result.setMessage("还款授权成功");
        result.setStatus(true);
        return JSONObject.toJSONString(result, true);
    }

    /**
     * APP-查询授权失败原因
     * @param request
     * @return
     */
    @ApiOperation(value = "APP-查询授权失败原因", notes = "APP-查询授权失败原因")
    @PostMapping(APP_REPAY_SEACH_AUTH)
    @ResponseBody
    public WebResult<Object> seachUserAuthErrorMessgae(HttpServletRequest request) {
        logger.info("APP-查询授权失败原因,"+
                            "className:["+ this.getClass().getName() +"],"+
                            "methodPath:["+ APP_REPAY_CLASS_NAME + APP_REPAY_SEACH_AUTH +"]");

        String logOrdId = request.getParameter("logOrdId");
        logger.info("正在查询授权失败原因的logOrdId:["+ logOrdId +"]");
        //表 ht_hjh_user_auth_log
        WebResult<Object> result = authService.seachUserAuthErrorMessgae(logOrdId);

        logger.info("APP-查询授权失败原因,"+
                            "className:["+ this.getClass().getName() +"],"+
                            "methodPath:["+ APP_REPAY_CLASS_NAME + APP_REPAY_SEACH_AUTH +"]");
        return result;
    }

    /**
     * 检查用户信息
     * @param user
     */
    private void checkUserMessage(UserVO user) {
        CheckUtil.check(user != null,MsgEnum.ERR_USER_NOT_EXISTS);
        // 判断用户是否开户
        CheckUtil.check(user.getBankOpenAccount() != 0,MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        // 判断用户是否设置过交易密码
        CheckUtil.check(user.getIsSetPassword() != 0,MsgEnum.ERR_TRADE_PASSWORD_NOT_SET);
        // 判断是否授权过
        CheckUtil.check(!authService.checkIsAuth(user.getUserId(), AuthBean.AUTH_TYPE_REPAY_AUTH),MsgEnum.ERR_AUTH_REPEAT);
    }

    /**
     * 同步、异步路径拼接
     * @param bgRetUrl
     * @param request
     * @return
     */
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
}
