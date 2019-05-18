/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.login;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.am.vo.admin.UserOperationLogEntityVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.UserOperationLogConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.AppUserToken;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.user.bean.LoginResultBean;
import com.hyjf.cs.user.constants.ResultEnum;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.mq.base.CommonProducer;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.result.BaseResultBean;
import com.hyjf.cs.user.service.login.LoginService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.util.RSAJSPUtil;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author zhangqingqing
 * @version LoginController, v0.1 2018/6/11 14:33
 */
@Api(value = "weChat端用户登录接口", tags = "weChat端-用户登录接口")
@RestController
@RequestMapping("/hyjf-wechat/wx/login")
public class WeChatLoginController extends BaseUserController {
    private static final Logger logger = LoggerFactory.getLogger(WeChatLoginController.class);
    @Autowired
    private LoginService loginService;
    @Autowired
    private CommonProducer commonProducer;

    /**
     * 登录接口
     *
     * @param request
     * @param userName
     * @param password
     * @param env
     * @return
     */
    @ApiOperation(value = "用户登录接口", notes = "用户登录接口")
    @ResponseBody
    @PostMapping(value = "/doLogin.do")
    public BaseResultBean login(HttpServletRequest request, @RequestParam String userName, @RequestParam String password,
                                @RequestParam(value = "env", defaultValue = "") String env) {
        LoginResultBean result = new LoginResultBean();
        // 从payload里面获取预置属性
        String presetProps = getStringFromStream(request);
        CheckUtil.check(null != userName && null != password, MsgEnum.STATUS_CE000001);
        // 现只支持两个参数  1微信  2风车理财
        if (!"1".equals(env) && !"2".equals(env)) {
            throw new ReturnMessageException(MsgEnum.ERR_PARAM);
        }
        //密码解密
        password = RSAJSPUtil.rsaToPassword(password);
        // weChat 只支持手机号登录
        if (!CommonUtils.isMobile(userName)) {
            throw new ReturnMessageException(MsgEnum.ERR_USER_LOGIN);
        }
        //判断用户输入的密码错误次数---开始
        UserVO user = loginService.getUser(userName);
        Map<String, String> errorInfo=loginService.insertErrorPassword(userName,password,BankCallConstant.CHANNEL_WEI,user);
        if (!errorInfo.isEmpty()){
            logger.error("weChat端登录失败...");
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc(errorInfo.get("info"));
            return result;
        }
        //判断用户输入的密码错误次数---结束
        WebViewUserVO userVO = loginService.login(userName, password, GetCilentIP.getIpAddr(request), BankCallConstant.CHANNEL_WEI,user);
        if (userVO != null) {
            // add by liuyang 神策数据统计追加 登录成功后 将用户ID返回前端 20180717 start
            // 登录成功后,将用户ID返回给前端
            result.setUserId(String.valueOf(userVO.getUserId()));
            // 预置属性不为空,发送神策登陆事件MQ
            logger.info("presetProps:" + presetProps);
            if (StringUtils.isNotBlank(presetProps)){
                try {
                    SensorsDataBean sensorsDataBean = new SensorsDataBean();
                    // 将json串转换成Bean
                    Map<String, Object> sensorsDataMap = JSONObject.parseObject(presetProps, new TypeReference<Map<String, Object>>() {
                    });
                    sensorsDataBean.setPresetProps(sensorsDataMap);
                    sensorsDataBean.setUserId(userVO.getUserId());
                    // 发送神策数据统计MQ
                    this.loginService.sendSensorsDataMQ(sensorsDataBean);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            //登录成功发送mq
            UserOperationLogEntityVO userOperationLogEntity = new UserOperationLogEntityVO();
            userOperationLogEntity.setOperationType(UserOperationLogConstant.USER_OPERATION_LOG_TYPE1);
            userOperationLogEntity.setIp(GetCilentIP.getIpAddr(request));
            userOperationLogEntity.setPlatform(1);
            userOperationLogEntity.setRemark("");
            userOperationLogEntity.setOperationTime(new Date());
            userOperationLogEntity.setUserName(userVO.getUsername());
            userOperationLogEntity.setUserRole(userVO.getRoleId());
            try {
                commonProducer.messageSend(new MessageContent(MQConstant.USER_OPERATION_LOG_TOPIC, UUID.randomUUID().toString(), userOperationLogEntity));
            } catch (MQException e) {
                logger.error("保存用户日志失败", e);
            }
            if (StringUtils.isNotBlank(env)) {
                //登录成功之后风车理财的特殊标记，供后续出借使用
                RedisUtils.del("loginFrom" + userVO.getUserId());
                RedisUtils.set("loginFrom" + userVO.getUserId(), env, 1800);
            }
            // 登录完成返回值
            result.setStatus(ResultEnum.SUCCESS.getStatus());
            result.setStatusDesc("登录成功");

            // Add by huanghui 用户开户区分企业用户或个人用户
            result.setUserType(userVO.getUserType());
            result.setSign(userVO.getToken());
        } else {
            logger.error("weChat端登录失败...");
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc(MsgEnum.ERR_USER_LOGIN.getMsg());
        }
        return result;
    }

    /**
     * 退出操作
     * 请求地址:/wx/login/doLoginOut.do
     * 需要参数: sign
     * @param sign
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "登出", notes = "登出")
    @PostMapping(value = "/doLoginOut.do")
    public BaseResultBean doLoginOut( String sign,HttpServletRequest request) {
        LoginResultBean result = new LoginResultBean();
        result.setStatus(ResultEnum.SUCCESS.getStatus());
        result.setStatusDesc("退出成功");
        if(StringUtils.isBlank(sign)){
            return result.setEnum(ResultEnum.PARAM);
        }
        AppUserToken token = SecretUtil.getAppUserToken(sign);
        if (token != null && token.getUserId() != null) {
            UserInfoVO userInfoVO =  loginService.getUserInfo(token.getUserId());
            UserOperationLogEntityVO userOperationLogEntity = new UserOperationLogEntityVO();
            userOperationLogEntity.setOperationType(UserOperationLogConstant.USER_OPERATION_LOG_TYPE2);
            userOperationLogEntity.setIp(com.hyjf.common.util.GetCilentIP.getIpAddr(request));
            userOperationLogEntity.setPlatform(1);
            userOperationLogEntity.setRemark("");
            userOperationLogEntity.setOperationTime(new Date());
            userOperationLogEntity.setUserName(token.getUsername());
            userOperationLogEntity.setUserRole(String.valueOf(userInfoVO.getRoleId()));
            try {
                commonProducer.messageSend(new MessageContent(MQConstant.USER_OPERATION_LOG_TOPIC, UUID.randomUUID().toString(), userOperationLogEntity));
            } catch (MQException e) {
                logger.error("保存用户日志失败", e);
            }            // 清除sign
            SecretUtil.clearToken(sign);
            RedisUtils.del("loginFrom"+token.getUserId());
        } else {
            result.setEnum(ResultEnum.ERROR_004);
        }
        return result;
    }


    /**
     * 从payload里面取神策预置属性,为解决从request里面取乱码的问题
     *
     * @param req
     * @return
     */
    private String getStringFromStream(HttpServletRequest req) {
        ServletInputStream is;
        try {
            is = req.getInputStream();
            int nRead = 1;
            int nTotalRead = 0;
            byte[] bytes = new byte[10240];
            while (nRead > 0) {
                nRead = is.read(bytes, nTotalRead, bytes.length - nTotalRead);
                if (nRead > 0)
                    nTotalRead = nTotalRead + nRead;
            }
            String str = new String(bytes, 0, nTotalRead, "utf-8");
            return str;
        } catch (IOException e) {
            logger.error(e.getMessage());
            return "";
        }
    }



    @ApiOperation(value = "短信验证码登录", notes = "短信验证码登录")
    @ResponseBody
    @PostMapping(value = "/mobileCodeLogin.do")
    public BaseResultBean mobileCodeLogin(HttpServletRequest request, @RequestParam String userName, @RequestParam String smsCode,
                                @RequestParam(value = "env", defaultValue = "") String env) {
        LoginResultBean result = new LoginResultBean();
        // 从payload里面获取预置属性
        String presetProps = getStringFromStream(request);
        CheckUtil.check(null != userName && null != smsCode, MsgEnum.STATUS_CE000001);

        // 现只支持两个参数  1微信  2风车理财
        if (!"1".equals(env) && !"2".equals(env)) {
            throw new ReturnMessageException(MsgEnum.ERR_PARAM);
        }

        // weChat 只支持手机号登录
        if (!CommonUtils.isMobile(userName)) {
            throw new ReturnMessageException(MsgEnum.ERR_USER_LOGIN);
        }

        UserVO userVO = loginService.getUsersByMobile(userName);
        Map<String, String> errorInfo=loginService.checkMobileCodeLogin(smsCode,BankCallConstant.CHANNEL_APP,userVO);
        if (!errorInfo.isEmpty()){
            logger.error("web端登录失败...");
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc(errorInfo.get("info"));
            return result;
        }

        // 执行登录(登录时间，登录ip)
        WebViewUserVO webViewUserVO = loginService.loginByCode(userName, GetCilentIP.getIpAddr(request), BankCallConstant.CHANNEL_WEI,userVO);
        if (webViewUserVO != null) {
            // add by liuyang 神策数据统计追加 登录成功后 将用户ID返回前端 20180717 start
            // 登录成功后,将用户ID返回给前端
            result.setUserId(String.valueOf(userVO.getUserId()));
            // 预置属性不为空,发送神策登陆事件MQ
            logger.info("presetProps:" + presetProps);
            if (StringUtils.isNotBlank(presetProps)){
                try {
                    SensorsDataBean sensorsDataBean = new SensorsDataBean();
                    // 将json串转换成Bean
                    Map<String, Object> sensorsDataMap = JSONObject.parseObject(presetProps, new TypeReference<Map<String, Object>>() {
                    });
                    sensorsDataBean.setPresetProps(sensorsDataMap);
                    sensorsDataBean.setUserId(userVO.getUserId());
                    // 发送神策数据统计MQ
                    this.loginService.sendSensorsDataMQ(sensorsDataBean);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            //登录成功发送mq
            UserOperationLogEntityVO userOperationLogEntity = new UserOperationLogEntityVO();
            userOperationLogEntity.setOperationType(UserOperationLogConstant.USER_OPERATION_LOG_TYPE1);
            userOperationLogEntity.setIp(GetCilentIP.getIpAddr(request));
            userOperationLogEntity.setPlatform(1);
            userOperationLogEntity.setRemark("");
            userOperationLogEntity.setOperationTime(new Date());
            userOperationLogEntity.setUserName(webViewUserVO.getUsername());
            userOperationLogEntity.setUserRole(webViewUserVO.getRoleId());
            try {
                commonProducer.messageSend(new MessageContent(MQConstant.USER_OPERATION_LOG_TOPIC, UUID.randomUUID().toString(), userOperationLogEntity));
            } catch (MQException e) {
                logger.error("保存用户日志失败", e);
            }
            if (StringUtils.isNotBlank(env)) {
                //登录成功之后风车理财的特殊标记，供后续出借使用
                RedisUtils.del("loginFrom" + userVO.getUserId());
                RedisUtils.set("loginFrom" + userVO.getUserId(), env, 1800);
            }
            // 登录完成返回值
            result.setStatus(ResultEnum.SUCCESS.getStatus());
            result.setStatusDesc("登录成功");

            // Add by huanghui 用户开户区分企业用户或个人用户
            result.setUserType(userVO.getUserType());
            result.setSign(webViewUserVO.getToken());
        } else {
            logger.error("weChat端登录失败...");
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc(MsgEnum.ERR_USER_LOGIN.getMsg());
        }
        return result;
    }
}
