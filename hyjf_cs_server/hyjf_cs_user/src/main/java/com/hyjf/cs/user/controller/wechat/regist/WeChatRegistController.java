/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.regist;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.app.BaseResultBeanFrontEnd;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.BaseMapBean;
import com.hyjf.cs.user.bean.RegistLandingPageCommitRequestBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.controller.wechat.login.LoginResultBean;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.mq.producer.CouponProducer;
import com.hyjf.cs.user.mq.producer.SmsProducer;
import com.hyjf.cs.user.result.BaseResultBean;
import com.hyjf.cs.user.service.regist.RegistService;
import com.hyjf.cs.user.util.RSAJSPUtil;
import com.hyjf.cs.user.util.ResultEnum;
import com.hyjf.cs.user.vo.RegisterRequest;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.hyjf.common.constants.CommonConstant.*;
/**
 * @author zhangqingqing
 * @version RegistController, v0.1 2018/6/11 14:35
 */

@Api(tags = "weChat端-用户注册接口")
@RestController
@RequestMapping("/hyjf-wechat/userRegist")
public class WeChatRegistController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(WeChatRegistController.class);
    @Autowired
    private RegistService registService;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private SmsProducer smsProducer;

    @Autowired
    private CouponProducer couponProducer;

    /** 短信验证码状态,新验证码 */
    private static final Integer CKCODE_NEW = 0;
    /** 短信验证码状态,失效 */
    private static final Integer CKCODE_FAILED = 7;
    /** 短信验证码状态,已验 */
    private static final Integer CKCODE_YIYAN = 8;
    /** 短信验证码状态,已用 */
    private static final Integer CKCODE_USED = 9;
    /**
     * 获取密码加密的公钥
     * @return
     */
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
    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping(value = "/registAction.do")
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
        if(null!=ret.getStatus()){
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

    @ApiOperation(value = "获取图形验证码",notes = "获取图形验证码")
    @GetMapping(value = "/getcaptcha")
    public void randomCode(HttpServletRequest request, HttpServletResponse response) {
        logger.info("enter the randomCode");
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        try {
            randomValidateCode.getRandcode(request, response);// 输出图片方法
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("错误信息:[{}]",e.getMessage());
        }
    }

    /**
     * 注册着陆页提交
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "注册着陆页提交",notes = "注册着陆页提交")
    @PostMapping(value = "/registLandingPageCommitAction", produces = "application/json; charset=utf-8")
    public JSONObject registLandingPageCommitAction(HttpServletRequest request, HttpServletResponse response, @RequestBody RegistLandingPageCommitRequestBean bean) {

        JSONObject ret = new JSONObject();
        // 手机号
        String mobile = bean.getMobile();
        logger.info("当前注册手机号: {}", mobile);
        if (Validator.isNull(mobile)) {
            ret.put("status", "99");
            ret.put("statusDesc", "手机号为空!");
            return ret;
        }
        if (!Validator.isMobile(mobile)) {
            ret.put("status", "99");
            ret.put("statusDesc", "手机号格式不正确!");
            return ret;
        }
        //查看当前手机号是否已经注册
        boolean exist = registService.existUser(mobile);
        if (exist) {
            ret.put("status", "99");
            ret.put("statusDesc", "该手机号已经注册!");
            return ret;
        }

        // 登录密码校验
        String password = bean.getPassword();
        //密码解密
        password = RSAJSPUtil.rsaToPassword(password);
        if (Validator.isNull(password)) {
            //密码不能为空
            ret.put("status", "99");
            ret.put("statusDesc", "密码不能为空!");
            return ret;
        }

        if (password.length() < 6 || password.length() > 16) {
            ret.put("status", "99");
            ret.put("statusDesc", "密码长度6-16位!");
            return ret;
        }

        boolean hasNumber = false;

        for (int i = 0; i < password.length(); i++) {
            if (Validator.isNumber(password.substring(i, i + 1))) {
                hasNumber = true;
                break;
            }
        }
        if (!hasNumber) {
            ret.put("status", "99");
            ret.put("statusDesc", "密码必须包含数字!");
            return ret;
        }

        String regEx = "^[a-zA-Z0-9]+$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(password);
        if (!m.matches()) {
            //密码必须由数字和字母组成，如abc123
            ret.put("status", "99");
            ret.put("statusDesc", "密码必须由数字和字母组成，如abc123!");
            return ret;
        }

        //验证图片验证码
        JSONObject captchaCheckResult =this.checkcaptchajson(request, bean.getNewRegVerifyCode());
        if(!"000".equals(captchaCheckResult.get("status"))) {
            //不等于000 代表校验不通过
            return captchaCheckResult;
        }
        //验证短信验证码
        JSONObject verificationCodeCheckResult=this.validateVerificationCodeAction(bean, response);
        if(!"000".equals(verificationCodeCheckResult.get("status"))) {
            //不等于000 代表校验不通过
            return verificationCodeCheckResult;
        }

        String verificationType = PARAM_TPL_ZHUCE;
        // 参数含义?
        int cnt = registService.updateCheckMobileCode(mobile, bean.getVerificationCode(), verificationType,CLIENT_WECHAT, CKCODE_YIYAN, CKCODE_USED,true);
        if (cnt == 0) {
            ret.put("status", "99");
            ret.put("statusDesc", "验证码无效!");
            return ret;
        }
        //推荐人userId
        String refferUserId=bean.getRefferUserId();
        UserVO user = new UserVO();
        logger.info("utmId: {}", bean.getUtmId());
        logger.info("refferUserId: {}", refferUserId);
        logger.info("utmSource: {}", bean.getUtmSource());
        logger.info("verificationCode: {}", bean.getVerificationCode());

        user =  registService.insertUserActionUtm(mobile, password,bean.getVerificationCode(), refferUserId, CustomUtil.getIpAddr(request),
                CustomConstants.CLIENT_WECHAT,bean.getUtmId(),bean.getUtmSource());
        //int userId = registService.insertUserActionUtm(mobile, password,request.getParameter("verificationCode"), refferUserId, CustomUtil.getIpAddr(request),CustomConstants.CLIENT_WECHAT,request.getParameter("utmId"),request.getParameter("utmSource"));
        Integer userId = user.getUserId();
        try {
            if (userId != 0) {
                //user.setUserId(userId);
                //完成注册同时完成登录返回sign值 add by jijun 2018/03/30
                LoginResultBean resultOfLogin=(LoginResultBean) this.proceedLoginAction(request,mobile,password);
                if(Validator.isNull(resultOfLogin.getSign())) {
                    //sign为空时代表登录失败
                    ret.put("status", resultOfLogin.getStatus());
                    ret.put("statusDesc", resultOfLogin.getStatusDesc());
                    return ret;
                }else {
                    ret.put("sign",resultOfLogin.getSign());
                }

                String statusDesc = "注册成功";

                // add by zhangjinpeng 注册送888元新手红包 start
                String activityId = CustomConstants.REGIST_888_ACTIVITY_ID;
                // 活动有效期校验
                boolean isAvailable = registService.checkActivityIfAvailable(Integer.valueOf(activityId));
                if(isAvailable){
//                CommonParamBean paramBean = new CommonParamBean();
//                paramBean.setUserId(user.getUserId().toString());
//                paramBean.setSendFlg(11);
//                // 发放888元新手红包
//                CommonSoaUtils.sendUserCouponNoRet(paramBean);

                    // 发放注册888红包
                    try {
                        sendCoupon(user);
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("注册发放888红包失败");
                    }

                    AdsRequest adsRequest = new AdsRequest();
                    adsRequest.setCode("registpop");
                    adsRequest.setHost(systemConfig.fileDomainUrl);
                    adsRequest.setLimitStart(0);
                    adsRequest.setLimitEnd(1);

                    AppAdsCustomizeVO record = registService.searchBanner(adsRequest);
                    if(record != null){
                        // 注册成功发券提示
                        String operationUrl = "://jumpCouponsList/?"; //record.getUrl() + "?couponStatus=0&sign=" + sign + "&platform" + platform;
//                    ret.put("imageUrl", record.getImage());
//                    ret.put("imageUrlOperation", operationUrl);
                        BaseMapBean baseMapBean=new BaseMapBean();
                        baseMapBean.set("imageUrl", record.getImage());
                        baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
                        baseMapBean.set(CustomConstants.APP_STATUS_DESC, URLEncoder.encode(statusDesc, "UTF-8"));
                        baseMapBean.set("imageUrlOperation", operationUrl);
                        baseMapBean.setCallBackAction(CustomConstants.HOST+REGIST_RESULT_SUCCESS);

                        ret.put("status", "000");
                        ret.put("statusDesc", "注册成功");
                        ret.put("successUrl", baseMapBean.getUrl());
                        return ret;
                    }

                    // 发券成功
                    // 发送短信通知
                    user.setMobile(mobile);
                    sendSmsCoupon(user);
                }else {
//                ret.put("imageUrl", "");
//                ret.put("imageUrlOperation", "");
                    BaseMapBean baseMapBean=new BaseMapBean();
                    baseMapBean.set("imageUrl", "");
                    baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
                    baseMapBean.set(CustomConstants.APP_STATUS_DESC, URLEncoder.encode(statusDesc, "UTF-8"));
                    baseMapBean.set("imageUrlOperation", "");
                    baseMapBean.setCallBackAction(CustomConstants.HOST+REGIST_RESULT_SUCCESS);

                    ret.put("status", "000");
                    ret.put("statusDesc", "注册成功");
                    ret.put("successUrl",baseMapBean.getUrl());
                    return ret;
                }
                // add by zhangjinpeng 注册送888元新手红包 end
                BaseMapBean baseMapBean=new BaseMapBean();
                baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
                baseMapBean.set(CustomConstants.APP_STATUS_DESC, URLEncoder.encode(statusDesc, "UTF-8"));
                baseMapBean.setCallBackAction(CustomConstants.HOST+REGIST_RESULT_SUCCESS);

                ret.put("status", "000");
                ret.put("statusDesc", "注册成功");
                ret.put("successUrl",baseMapBean.getUrl());
                return ret;
            } else {
                //注册失败,参数异常
                ret.put("status", "99");
                ret.put("statusDesc", "注册失败,参数异常");
                ret.put("successUrl","");
                return ret;
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret.put("status", "99");
            ret.put("statusDesc", "注册发生错误,参数异常");
            ret.put("successUrl", "");
        }

        return ret;

    }

    /**
     * 验证验证码
     *
     * @param bean 请求参数
     * @param response
     * @return
     */
    @ApiOperation(value = "验证验证码",notes = "验证验证码")
    @PostMapping(value = "/validateVerificationCodeAction")
    public JSONObject validateVerificationCodeAction(RegistLandingPageCommitRequestBean bean, HttpServletResponse response) {

        JSONObject ret = new JSONObject();
        ret.put("request","/hyjf-wechat/userRegist/validateVerificationCodeAction");

        // 验证方式
        String verificationType = bean.getVerificationType();
        // 验证码
        String verificationCode = bean.getVerificationCode();
        // 手机号
        String mobile = bean.getMobile();

        if (Validator.isNull(verificationType)) {
            ret.put("status", "99");
            ret.put("statusDesc", "验证码类型不能为空");
            return ret;
        }
        if (Validator.isNull(verificationCode)) {
            ret.put("status", "99");
            ret.put("statusDesc", "验证码不能为空");
            return ret;
        }
        if (!(verificationType.equals(PARAM_TPL_ZHUCE) || verificationType.equals(PARAM_TPL_ZHAOHUIMIMA) || verificationType.equals(PARAM_TPL_BDYSJH) || verificationType.equals(PARAM_TPL_YZYSJH))) {
            ret.put("status", "99");
            ret.put("statusDesc", "无效的验证码类型");
            return ret;
        }

        // 业务逻辑
        try {
            if (Validator.isNull(mobile)) {
                ret.put("status", "99");
                ret.put("statusDesc", "手机号不能为空");
                return ret;
            }
            if (!Validator.isMobile(mobile)) {
                ret.put("status", "99");
                ret.put("statusDesc", "请输入您的真实手机号码");
                return ret;
            }
            // 参数含义?
            int cnt = registService.updateCheckMobileCode(mobile, verificationCode, verificationType,CustomConstants.CLIENT_WECHAT, CKCODE_NEW, CKCODE_YIYAN,true);

            if (cnt > 0) {
                ret.put("status", "000");
                ret.put("statusDesc", "验证验证码成功");
            } else {
                ret.put("status", "99");
                ret.put("statusDesc", "验证码输入错误");
            }

        } catch (Exception e) {
            ret.put("status", "99");
            ret.put("statusDesc", "验证验证码发生错误");
        }

        return ret;
    }

    /**
     * 检查图片验证码,返回json验证结果
     * @auth sunpeikai
     * @param
     * @return
     */
    private JSONObject checkcaptchajson(HttpServletRequest request,String newRegVerifyCode) {
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        JSONObject ret = new JSONObject();
        if(Validator.isNull(newRegVerifyCode)) {
            ret.put("status", "99");
            ret.put("statusDesc", "图形验证码为空!");
            return ret;
        }
        if (randomValidateCode.checkRandomCode(request, newRegVerifyCode)) {
            ret.put("status", "000");
            ret.put("statusDesc", "图形验证码验证成功");
            return ret;
        } else {
            ret.put("status", "99");
            ret.put("statusDesc", "图形验证码不正确!");
            return ret;
        }
    }

    /**
     * 发送短信(注册送188元新手红包)
     * @auth sunpeikai
     * @param userVO 用户信息
     * @return
     */
    private void sendSmsCoupon(UserVO userVO) {
        if (userVO == null || Validator.isNull(userVO.getUserId())) {
            return;
        }
        try{
            // 发送
            SmsMessage smsMessage = new SmsMessage(userVO.getUserId(), null, userVO.getMobile(), null, MessageConstant.SMS_SEND_FOR_MOBILE, null, CustomConstants.PARAM_TPL_TZJ_188HB,
                    CustomConstants.CHANNEL_TYPE_NORMAL);
            smsProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(smsMessage)));

        }catch (MQException e){
            e.printStackTrace();
            logger.error("发送短信(注册送188元新手红包)失败! userId=[{}]",userVO.getUserId());
        }

    }

    /**
     * 注册888红包发放
     * @auth sunpeikai
     * @param userVO 用户信息
     * @return
     */
    private void sendCoupon(UserVO userVO) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mqMsgId", GetCode.getRandomCode(10));
        params.put("userId", userVO.getUserId().toString());
        params.put("sendFlg", "11");

        String signValue = StringUtils.lowerCase(MD5.toMD5Code(systemConfig.couponAccesskey + userVO.getUserId().toString() + 11 + systemConfig.couponAccesskey));
        params.put("sign", signValue);

        try {
            couponProducer.messageSend(new MessageContent(MQConstant.GRANT_COUPON_TOPIC,
                    UUID.randomUUID().toString(), JSON.toJSONBytes(params)));
        } catch (MQException e) {
            e.printStackTrace();
            logger.error("注册888红包发放失败");
        }
    }

    /**
     * 完成登录操作
     *  add by jijun 2018/03/30
     */
    private BaseResultBean proceedLoginAction(HttpServletRequest request, String userName, String password) {
        logger.info("执行登录操作开始,手机号为：【"+userName+"】");
        LoginResultBean result = new LoginResultBean();
        if(StringUtils.isBlank(userName)||StringUtils.isBlank(password)){
            return result.setEnum(ResultEnum.PARAM);
        }
        int userId = registService.updateLoginInAction(userName, password, CustomUtil.getIpAddr(request));
        switch (userId) {
            case -1:
                result.setEnum(ResultEnum.ERROR_001);
                break;
            case -2:
                result.setEnum(ResultEnum.ERROR_002);
                break;
            case -3:
                result.setEnum(ResultEnum.ERROR_003);
                break;
            default:
                UserVO userVO = registService.getUsersById(userId);

                BankOpenAccountVO account = registService.getBankOpenAccount(userId);
                String accountId = null;
                if(account!=null&&StringUtils.isNoneBlank(account.getAccount())){
                    accountId = account.getAccount();
                    /*********** 登录时自动同步线下充值记录 start ***********/
                    if (userVO.getBankOpenAccount() == 1) {
                        CommonSoaUtils.synBalance(userVO.getUserId());
                    }
                    /*********** 登录时自动同步线下充值记录 end ***********/
                }
                String sign = SecretUtil.createToken(userId, userVO.getUsername(), accountId);

                try {
                    StringBuffer url = request.getRequestURL();
                    String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
                    String host = CustomConstants.WECHAT_HOST;
                    logger.info("登录url为===="+tempContextUrl);
                    logger.info("获取登录配置为==="+host);
                    logger.info("登录配置和url是否相等==="+(tempContextUrl.equals(host)));
                    String str[] = tempContextUrl.split(":");
                    String str1[] = host.split(":");
                    if(str.length > 1 && str1.length > 1){
                        if (str[1].equals(str1[1])) {
                            RedisUtils.del("loginFrom"+userId);
                            RedisUtils.set("loginFrom"+userId, "2", 1800);
                        }
                    }
                } catch (Exception e) {
                    logger.error("处理登陆来源异常！");
                }
                // 登录完成返回值
                result.setEnum(ResultEnum.SUCCESS);
                result.setSign(sign);
                break;
        }

        logger.info("执行登录操作结束,手机号为：【"+userName+"】");
        return result;
    }
}
