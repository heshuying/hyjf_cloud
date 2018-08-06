/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.regist;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.app.BaseResultBeanFrontEnd;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.DES;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.cs.common.util.GetJumpCommand;
import com.hyjf.cs.user.bean.BaseMapBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.regist.RegistService;
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
    private RegistService registService;

    @Autowired
    private SystemConfig systemConfig;

    /**
     * 注册
     * @param key
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @ApiOperation(value = "用户注册", notes = "app端-用户注册")
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
        registService.register(register, GetCilentIP.getIpAddr(request));
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
                e.printStackTrace();
            }
            baseMapBean.set("imageUrlOperation", "");
            baseMapBean.setCallBackAction(systemConfig.getAppHost()+"/user/regist/result/success");
            ret.put(CustomConstants.APP_STATUS, 0);
            ret.put(CustomConstants.APP_STATUS_DESC, statusDesc);
            ret.put("successUrl", baseMapBean.getUrl());
            return ret;
        }else {
            AdsRequest adsRequest = new AdsRequest();
            adsRequest.setLimitStart(0);
            adsRequest.setLimitEnd(1);
            adsRequest.setHost(systemConfig.getDomainAppUrl());
            adsRequest.setCode("registpop");
            AppAdsCustomizeVO record = new AppAdsCustomizeVO();
            try {
                 record = registService.searchBanner(adsRequest);
            }catch (Exception e){
                logger.info("获取活动信息失败...");
            }
            // 注册成功发券提示
            String operationUrl = jumpCommand + "://jumpCouponsList/?";
            BaseMapBean baseMapBean = new BaseMapBean();
            baseMapBean.set("imageUrl", record.getImage());
            baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
            try {
                baseMapBean.set(CustomConstants.APP_STATUS_DESC, URLEncoder.encode(statusDesc, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            baseMapBean.set("imageUrlOperation", operationUrl);
            baseMapBean.setCallBackAction(systemConfig.getAppHost()+"/user/regist/result/success");
            ret.put(CustomConstants.APP_STATUS, 0);
            ret.put(CustomConstants.APP_STATUS_DESC, statusDesc);
            ret.put("successUrl", baseMapBean.getUrl());
            return ret;
        }
    }
}
