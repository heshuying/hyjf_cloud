/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.regist;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.DES;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.util.GetJumpCommand;
import com.hyjf.cs.user.bean.BaseMapBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.result.BaseResultBeanFrontEnd;
import com.hyjf.cs.user.service.regist.RegistService;
import com.hyjf.cs.user.util.GetCilentIP;
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
@Api(value = "app端用户注册接口",description = "app端-用户注册接口")
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
    @PostMapping(value = "/registAction", produces = "application/json; charset=utf-8")
    public JSONObject register(@RequestHeader(value = "key") String key, HttpServletRequest request) throws UnsupportedEncodingException {
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
        CheckUtil.check(version.compareTo("1.4.0")>0,MsgEnum.STATUS_CE000014);
        logger.info("当前注册手机号: {}", mobile);

        // 取得加密用的Key
        CheckUtil.check(StringUtils.isNotBlank(key),MsgEnum.STATUS_CE000001);
        mobile = DES.decodeValue(key, mobile);
        verificationCode = DES.decodeValue(key, verificationCode);
        password = DES.decodeValue(key, password);
        reffer = DES.decodeValue(key, reffer);
        RegisterRequest register = new RegisterRequest();
        register.setMobile(mobile);
        register.setPassword(password);
        register.setReffer(reffer);
        register.setVerificationCode(verificationCode);
        registService.checkParam(register);
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
            baseMapBean.set(CustomConstants.APP_STATUS_DESC, URLEncoder.encode(statusDesc, "UTF-8"));
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
            AppAdsCustomizeVO record = registService.searchBanner(adsRequest);
            // 注册成功发券提示
            String operationUrl = jumpCommand + "://jumpCouponsList/?";
            BaseMapBean baseMapBean = new BaseMapBean();
            baseMapBean.set("imageUrl", record.getImage());
            baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
            baseMapBean.set(CustomConstants.APP_STATUS_DESC, URLEncoder.encode(statusDesc, "UTF-8"));
            baseMapBean.set("imageUrlOperation", operationUrl);
            baseMapBean.setCallBackAction(systemConfig.getAppHost()+"/user/regist/result/success");
            ret.put(CustomConstants.APP_STATUS, 0);
            ret.put(CustomConstants.APP_STATUS_DESC, statusDesc);
            ret.put("successUrl", baseMapBean.getUrl());
            return ret;
        }
    }
}
