/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.regist;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.BaseMapBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.result.BaseResultBeanFrontEnd;
import com.hyjf.cs.user.service.regist.RegistService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.util.RSAJSPUtil;
import com.hyjf.cs.user.vo.RegisterRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author zhangqingqing
 * @version RegistController, v0.1 2018/6/11 14:35
 */

@Api(value = "weChat端-用户注册接口",description = "weChat端-用户注册接口")
@RestController
@RequestMapping("/hyjf-wechat/userRegist")
public class WeChatRegistController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(WeChatRegistController.class);
    @Autowired
    private RegistService registService;

    @Autowired
    SystemConfig systemConfig;

    /**
     * 获取密码加密的公钥
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "获取密码加密的公钥",notes = "获取密码加密的公钥")
    @PostMapping(value = "/findpublickey.do")
    public JSONObject findPublicKeys() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pubexponent", "10001");
        jsonObject.put("pubmodules", RSAJSPUtil.getPunlicKeys());
        return jsonObject;
    }

    /**
     * 注册
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    @ResponseBody
    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping(value = "/registAction.do", produces = "application/json; charset=utf-8")
    public UserRegistResultVO registAction(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        UserRegistResultVO ret = new UserRegistResultVO();
        ret.setRequest("/registAction");
        // 手机号
        String mobile = request.getParameter("mobile");
        // 验证码
        String verificationCode = request.getParameter("verificationCode");
        // 登录密码
        String password = request.getParameter("password");
        //密码解密
        password = RSAJSPUtil.rsaToPassword(password);
        // 推荐人
        String reffer = request.getParameter("reffer");
        logger.info("当前注册手机号: {}", mobile);
        RegisterRequest register = new RegisterRequest();
        register.setMobile(mobile);
        register.setPassword(password);
        register.setReffer(reffer);
        register.setVerificationCode(verificationCode);
        ret = registService.wechatCheckParam(mobile,password,reffer,verificationCode);
        if (null!=ret){
            return ret;
        }
        registService.register(register, GetCilentIP.getIpAddr(request));
        String statusDesc = "注册成功";
        if (registService.checkActivityIfAvailable(systemConfig.getActivity888Id())) {
            BaseMapBean baseMapBean=new BaseMapBean();
            baseMapBean.set("imageUrl", "");
            baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
            baseMapBean.set(CustomConstants.APP_STATUS_DESC, URLEncoder.encode(statusDesc, "UTF-8"));
            baseMapBean.set("imageUrlOperation", "");
            baseMapBean.setCallBackAction(systemConfig.getAppHost()+"/user/regist/result/success");
            ret.setStatus("000");
            ret.setStatusDesc(statusDesc);
            ret.setSuccessUrl(baseMapBean.getUrl());
            return ret;
        }else {
            AdsRequest adsRequest = new AdsRequest();
            adsRequest.setLimitStart(0);
            adsRequest.setLimitEnd(1);
            adsRequest.setHost(systemConfig.getDomainAppUrl());
            adsRequest.setCode("registpop");
            AppAdsCustomizeVO record = registService.searchBanner(adsRequest);
            // 注册成功发券提示
            String operationUrl = "://jumpCouponsList/?";
            BaseMapBean baseMapBean = new BaseMapBean();
            baseMapBean.set("imageUrl", record.getImage());
            baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
            baseMapBean.set(CustomConstants.APP_STATUS_DESC, URLEncoder.encode(statusDesc, "UTF-8"));
            baseMapBean.set("imageUrlOperation", operationUrl);
            baseMapBean.setCallBackAction(systemConfig.getAppHost()+"/user/regist/result/success");
            ret.setStatus("000");
            ret.setStatusDesc(statusDesc);
            ret.setSuccessUrl(baseMapBean.getUrl());
            return ret;
        }
    }
    /**
     * 点击下一步验证
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "点击下一步验证",notes = "点击下一步验证")
    @PostMapping(value = "/nextStepAction.do")
    public JSONObject nextStepAction(HttpServletRequest request, HttpServletResponse response) {
        JSONObject ret = new JSONObject();

        ret.put("status", "000");
        ret.put("statusDesc", "手机号校验通过");

        // 手机号
        String mobile = request.getParameter("mobile");
        if (Validator.isNull(mobile)) {
            ret.put("status", "99");
            ret.put("statusDesc", "手机号不能为空");
            return ret;
        }
        if (registService.existUser(mobile)) {
            ret.put("status", "99");
            ret.put("statusDesc", "该手机号已经注册");
            return ret;
        }

        return ret;

    }

}
