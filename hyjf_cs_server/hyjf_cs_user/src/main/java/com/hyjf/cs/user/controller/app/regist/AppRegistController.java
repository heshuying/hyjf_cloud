/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.regist;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hyjf.am.bean.app.BaseResultBeanFrontEnd;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.DES;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.cs.common.util.GetJumpCommand;
import com.hyjf.cs.user.bean.BaseMapBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.password.PassWordService;
import com.hyjf.cs.user.service.register.RegisterService;
import com.hyjf.cs.user.vo.RegisterRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version RegistController, v0.1 2018/6/11 14:42
 */
@Api(value = "app端用户注册接口",tags = "app端-用户注册接口")
@RestController
@RequestMapping("/hyjf-app/appUser")
public class AppRegistController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(AppRegistController.class);

    @Autowired
    private RegisterService registService;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private PassWordService passWordService;

    /**
     * 注册
     * @param key
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping(value = "/registAction")
    public JSONObject register(@RequestHeader(value = "key") String key, HttpServletRequest request){
        JSONObject ret = new JSONObject();
        ret.put("request", "/hyjf-app/appUser/registAction");
        // 版本号
        String version = request.getParameter("version");
        // 网络状态
        String netStatus = request.getParameter("netStatus");
        // 平台
        String platform = request.getParameter("platform");
        // 唯一标识
        String sign = request.getParameter("sign");
        // 随机字符串
        String randomString = request.getParameter("randomString");
        // Order
        String order = request.getParameter("order");

        // 手机号
        String mobile = request.getParameter("mobile");
        // 验证码
        String verificationCode = request.getParameter("verificationCode");
        // 登录密码
        String password = request.getParameter("password");
        // 推荐人
        String reffer = request.getParameter("reffer");
        // 神策预置属性
        String presetProps = request.getParameter("presetProps");

        // 合规改造 add by huanghui 20181220 start
        String userTypeStr = request.getParameter("userType");
        Integer userType = Integer.valueOf(userTypeStr);
        logger.info("手机号码:" + mobile + ";注册平台:" + platform + ";userType:" + userTypeStr + " : " + userType);
        // 合规改造 add by huanghui 20181220 end

        String jumpCommand = GetJumpCommand.getLinkJumpPrefix(request, version);
        //检查版本
        if(version.length()>=5){
            version = version.substring(0, 5);
        }
        if(version.compareTo("1.4.0")<=0){
            ret.put(CustomConstants.APP_STATUS, 1);
            ret.put(CustomConstants.APP_STATUS_DESC, "此版本暂不可用，请更新至最新版本");
            return ret;
        }
        logger.info("当前注册手机号: {}", mobile);
        version = request.getParameter("version");
        // 取得加密用的Key
        if(StringUtils.isBlank(key)){
            ret.put(CustomConstants.APP_STATUS, 1);
            ret.put(CustomConstants.APP_STATUS_DESC, "请求参数异常");
            return ret;
        }
        mobile = DES.decodeValue(key, mobile);
        verificationCode = DES.decodeValue(key, verificationCode);
        password = DES.decodeValue(key, password);
        reffer = DES.decodeValue(key, reffer);
        RegisterRequest register = new RegisterRequest();
        register.setMobile(mobile);
        register.setPassword(password);
        register.setReffer(reffer);
        register.setVerificationCode(verificationCode);
        register.setPlatform(platform);
        try{
            ret = registService.appCheckParam(register);
        }catch (Exception e){
            return ret;
        }
        if(ret.get(CustomConstants.APP_STATUS)!=null){
            return ret;
        }
        WebViewUserVO webViewUserVO = registService.register(register.getMobile(),
                register.getVerificationCode(), register.getPassword(),
                register.getReffer(), CommonConstant.HYJF_INST_CODE, register.getUtmId(), platform, GetCilentIP.getIpAddr(request), userType);

        // add by liuyang 神策数据统计追加 20181029 start
        if (webViewUserVO != null && webViewUserVO.getUserId() != null && webViewUserVO.getUserId() != 0) {
            if (StringUtils.isNotBlank(presetProps)) {
                try {
                    SensorsDataBean sensorsDataBean = new SensorsDataBean();
                    // 将json串转换成Bean
                    Map<String, Object> sensorsDataMap = JSONObject.parseObject(presetProps, new TypeReference<Map<String, Object>>() {
                    });
                    sensorsDataBean.setPresetProps(sensorsDataMap);
                    sensorsDataBean.setUserId(webViewUserVO.getUserId());
                    // 发送神策数据统计MQ
                    this.registService.sendSensorsDataMQ(sensorsDataBean);
                } catch (MQException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        // add by liuyang 神策数据统计追加 20181029 end

        //发送mq同步推广表
        registService.sendMqToSaveAppChannel(version,webViewUserVO);
        String statusDesc = "注册成功";
        boolean active = false;
        try {
             active = registService.checkActivityIfAvailable(systemConfig.getActivity888Id());
        }catch (Exception e){
            logger.info("获取活动信息失败...");
        }
        if (active) {
            BaseMapBean baseMapBean=new BaseMapBean();
            baseMapBean.set("imageUrl", "");
            baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
            try {
                baseMapBean.set(CustomConstants.APP_STATUS_DESC, URLEncoder.encode(statusDesc, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage());
            }
            baseMapBean.set("imageUrlOperation", "");
            // 用户属性 0: 个人用户; 1,企业用户
            baseMapBean.set("userType", userTypeStr);
            baseMapBean.setCallBackAction(systemConfig.getAppServerHost()+"/user/regist/result/success");
            ret.put(CustomConstants.APP_STATUS, 0);
            ret.put(CustomConstants.APP_STATUS_DESC, statusDesc);
            ret.put("successUrl", baseMapBean.getUrl());
            logger.info("mobile:" + mobile + ";active:" + active +";注册成功:" + ret.toString());
            return ret;
        }else {
            AdsRequest adsRequest = new AdsRequest();
            adsRequest.setLimitStart(0);
            adsRequest.setLimitEnd(1);
            adsRequest.setHost(systemConfig.getFileDomainUrl());
            adsRequest.setCode("registpop");
            AppAdsCustomizeVO record = new AppAdsCustomizeVO();
            try {
                 record = registService.searchBanner(adsRequest);
            }catch (Exception e){
                logger.info("获取活动信息失败...");
            }
            // 注册成功发券提示
            String operationUrl = "";
            String operationUrlBase = "";
            try {
                operationUrl = jumpCommand + "://jumpCouponsList/?";

                /**
                 * 对App 原生的指令进行Base64编码, 防止返回给App的url出现两个 ?
                 * 赵成无法解析的情况出现
                 */
                operationUrlBase = Base64.getEncoder().encodeToString(operationUrl.getBytes("utf-8"));
            }catch (UnsupportedEncodingException e){
                logger.error(e.getMessage());
            }
            BaseMapBean baseMapBean = new BaseMapBean();
            baseMapBean.set("imageUrl", record.getImage());
            baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
            try {
                baseMapBean.set(CustomConstants.APP_STATUS_DESC, URLEncoder.encode(statusDesc, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage());
            }
            baseMapBean.set("imageUrlOperation", operationUrlBase);

            // 用户属性 0: 个人用户; 1,企业用户
            baseMapBean.set("userType", userTypeStr);
            baseMapBean.setCallBackAction(systemConfig.getAppServerHost()+"/user/regist/result/success");
            ret.put(CustomConstants.APP_STATUS, 0);
            ret.put(CustomConstants.APP_STATUS_DESC, statusDesc);
            ret.put("successUrl", baseMapBean.getUrl());
            logger.info("mobile:" + mobile + ";active:" + active +";注册成功:" + ret.toString());
            return ret;
        }
    }

    @ApiOperation(value = "校验手机号是否已注册", notes = "校验手机号是否已注册")
    @PostMapping(value = "/checkMobile")
    public JSONObject checkMobile(HttpServletRequest request){
        JSONObject checkResult = new JSONObject();
        // 手机号
        String mobile = request.getParameter("mobile");
        if(StringUtils.isBlank(mobile)){
            checkResult.put(CustomConstants.APP_STATUS, 1);
            checkResult.put(CustomConstants.APP_STATUS_DESC, "手机号为空");
        } else {
            if(passWordService.existPhone(mobile)){
                checkResult.put(CustomConstants.APP_STATUS, 1);
                checkResult.put(CustomConstants.APP_STATUS_DESC, "该手机号已经注册");
            } else {
                checkResult.put(CustomConstants.APP_STATUS, 0);
                checkResult.put(CustomConstants.APP_STATUS_DESC, "该手机号可以注册");
            }
        }
        return checkResult;
    }

    @ApiOperation(value = "校验验证码", notes = "校验验证码")
    @PostMapping(value = "/checkVerificationCode")
    public JSONObject checkVerificationCode(HttpServletRequest request){
        RegisterRequest registerRequest = new RegisterRequest();
        // 平台
        registerRequest.setPlatform(request.getParameter("platform"));
        // 手机号
        registerRequest.setMobile(request.getParameter("mobile"));
        // 验证码
        registerRequest.setVerificationCode(request.getParameter("verificationCode"));
        // 校验验证码
        JSONObject checkResult = registService.checkVerificationCode(registerRequest);
        return checkResult;
    }
}
