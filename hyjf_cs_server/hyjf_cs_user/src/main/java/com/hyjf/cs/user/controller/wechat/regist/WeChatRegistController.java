/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.regist;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Strings;
import com.hyjf.am.bean.app.BaseResultBeanFrontEnd;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.am.vo.admin.TemplateDisposeVO;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.am.vo.wbs.WbsRegisterMqVO;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.common.util.RandomValidateCode;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.BaseMapBean;
import com.hyjf.cs.user.bean.RegistLandingPageCommitRequestBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.mq.base.CommonProducer;
import com.hyjf.cs.user.result.UserRegistResult;
import com.hyjf.cs.user.service.login.LoginService;
import com.hyjf.cs.user.service.register.RegisterService;
import com.hyjf.cs.user.util.RSAJSPUtil;
import com.hyjf.cs.user.vo.RegisterRequest;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
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

    @Autowired
    private RegisterService registService;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private CommonProducer commonProducer;

    @Autowired
    LoginService loginService;

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
     * @param
     * @return
     * @throws UnsupportedEncodingException
     */
    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping(value = "/registAction.do")
    public UserRegistResult registAction(@RequestHeader(value = "wjtClient",required = false) String wjtClient, HttpServletRequest request) throws UnsupportedEncodingException {
        UserRegistResult ret = new UserRegistResult();
        ret.setRequest("/registAction.do");
        // 手机号
        String mobile = request.getParameter("mobile");
        // 验证码
        String verificationCode = request.getParameter("verificationCode");
        // 登录密码
        String password = request.getParameter("password");
        // 神策数据统计追加 add by liuyang 20180725 start
        // 神策数据统计的预置属性
        String presetProps = getStringFromStream(request);
        // 神策数据统计追加 add by liuyang 20180725 end

        // 合规改造 add by huanghui 20181220 start
        /**
         * 当前注册用的类型
         * 1:普通用户;
         * 2:企业用户;
         * 根据前端传值来判定, 如果不传或者传值其他值 默认为普通用户
         */
        Integer userType = Integer.valueOf(request.getParameter("userType"));

        // 合规改造 add by huanghui 20181220 end

        //密码解密
        password = RSAJSPUtil.rsaToPassword(password);
        // 推荐人
        String reffer = request.getParameter("reffer");

        // 温金投注册  新增字段  是否温金投注册
        String isWjt = request.getParameter("isWjt");

        logger.info("当前注册手机号: {}", mobile);
        RegisterRequest register = new RegisterRequest();
        register.setMobile(mobile);
        register.setPassword(password);
        register.setReffer(reffer);
        register.setVerificationCode(verificationCode);
        ret = registService.wechatCheckParam(mobile,password,reffer,verificationCode);
        if(null!=ret.getStatus()&&!ret.getStatus().equals("000")){
            return ret;
        }
        if(wjtClient!=null){
            logger.info("温金投用户注册开始");
            isWjt="1";
        }
        WebViewUserVO webViewUserVO = registService.register(register.getMobile(),
                register.getVerificationCode(), register.getPassword(),
                register.getReffer(), CommonConstant.HYJF_INST_CODE, register.getUtmId(), String.valueOf(ClientConstants.WECHAT_CLIENT), GetCilentIP.getIpAddr(request), userType,isWjt);
        //注册成功重新登录
        UserVO user = loginService.getUser(webViewUserVO.getUsername());
        WebViewUserVO userVO = loginService.login(webViewUserVO.getUsername(), password, GetCilentIP.getIpAddr(request), BankCallConstant.CHANNEL_WEI,user);
         if(null!=userVO){
             ret.setSign(userVO.getToken());
         }else {
             ret.setStatus("99");
             ret.setStatusDesc("登陆失败");
             ret.setSuccessUrl("");
             return ret;
         }

         // 注册成功后,将userId返回前端
         ret.setUserId(userVO.getUserId());
        // add by liuyang 神策数据统计追加 20181029 start
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
        // add by liuyang 神策数据统计追加 20181029 end
        String statusDesc = "注册成功";
        if (registService.checkActivityIfAvailable(systemConfig.getActivity888Id())) {
            BaseMapBean baseMapBean=new BaseMapBean();
            baseMapBean.set("imageUrl", "");
            baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
            baseMapBean.set(CustomConstants.APP_STATUS_DESC, URLEncoder.encode(statusDesc, "UTF-8"));
            baseMapBean.set("imageUrlOperation", "");
            baseMapBean.setCallBackAction(systemConfig.getAppServerHost()+"/user/regist/result/success");
            ret.setStatus("000");
            ret.setStatusDesc(statusDesc);
            ret.setSuccessUrl(baseMapBean.getUrl());
            return ret;
        }else {
            AdsRequest adsRequest = new AdsRequest();
            adsRequest.setLimitStart(0);
            adsRequest.setLimitEnd(1);
            adsRequest.setHost(systemConfig.getFileDomainUrl());
            adsRequest.setCode("registpop");
            AppAdsCustomizeVO record = registService.searchBanner(adsRequest);
            // 注册成功发券提示
            String operationUrl = "://jumpCouponsList/?";
            BaseMapBean baseMapBean = new BaseMapBean();
            baseMapBean.set("imageUrl", record.getImage());
            baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
            baseMapBean.set(CustomConstants.APP_STATUS_DESC, URLEncoder.encode(statusDesc, "UTF-8"));
            baseMapBean.set("imageUrlOperation", operationUrl);
            baseMapBean.setCallBackAction(systemConfig.getAppServerHost()+"/user/regist/result/success");
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

    /**
     * 着陆页获取图形验证码
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "获取图形验证码",notes = "获取图形验证码")
    @GetMapping(value = "/getcaptcha")
    public void randomCode(HttpServletRequest request, HttpServletResponse response) {
        logger.info("enter the randomCode");
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        try {
            randomValidateCode.getRandcode(request, response);// 输出图片方法
        } catch (Exception e) {
            logger.error(e.getMessage());
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
    @PostMapping(value = "/registLandingPageCommitAction")
    public JSONObject registLandingPageCommitAction(HttpServletRequest request, HttpServletResponse response, RegistLandingPageCommitRequestBean bean) {

        JSONObject ret = new JSONObject();
        // 手机号
        String mobile = bean.getMobile();
        // 神策数据统计追加 add by liuyang 20181105 start
        // 神策数据统计的预置属性
        String presetProps = getStringFromStream(request);
        // 神策数据统计追加 add by liuyang 20181105 end

        // 合规改造 add by huanghui 20181220 start
        // 当前注册用的类型:0:普通用户;1:企业用户;Warning:着陆页只有普通用户注册.没有企业注册
        Integer userType = 0;
        // 合规改造 add by huanghui 20181220 end

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

        if (password.length() < 8 || password.length() > 16) {
            ret.put("status", "99");
            ret.put("statusDesc", "密码长度8-16位!");
            return ret;
        }

        boolean hasNumber = false;

        for (int i = 0; i < password.length(); i++) {
            if (Validator.isNumber(password.substring(i, i + 1))) {
                hasNumber = true;
                break;
            }
        }
//        if (!hasNumber) {
//            ret.put("status", "99");
//            ret.put("statusDesc", "密码必须包含数字!");
//            return ret;
//        }

        String regEx = "^(?![0-9]+$)(?![a-zA-Z]+$)(?![\\`\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\-\\=\\{\\}\\|\\[\\]\\\\\\;\\'\\:\\\"\\,\\.\\/\\<\\>\\?]+$)[0-9A-Za-z\\`\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\-\\=\\{\\}\\|\\[\\]\\\\\\;\\'\\:\\\"\\,\\.\\/\\<\\>\\?]{8,16}$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(password);
        if (!m.matches()) {
            //密码必须由数字和字母组成，如abc123
            ret.put("status", "99");
            ret.put("statusDesc", "必须包含数字、字母、符号至少两种!");
            return ret;
        }

        //验证图片验证码
        // 微信 着陆页使用无感知图形验证码验证码, 屏蔽原有的图形验证码 ! --  add by huanghui
//        JSONObject captchaCheckResult =this.checkcaptchajson(request, bean.getNewRegVerifyCode());
//        if(!"000".equals(captchaCheckResult.get("status"))) {
//            //不等于000 代表校验不通过
//            return captchaCheckResult;
//        }
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
        logger.info("utmId: {}", bean.getUtmId());
        logger.info("refferUserId: {}", refferUserId);
        logger.info("utmSource: {}", bean.getUtmSource());
        logger.info("verificationCode: {}", bean.getVerificationCode());

        registService.checkReffer(refferUserId);
        // 着陆页配置代码修改 20190621 add by nxl start
        // 着陆页id
        String landingId = bean.getLandingId();
        logger.info("landingId: {}", bean.getLandingId());
        if(StringUtils.isNotBlank(landingId)&&StringUtils.isNotBlank(bean.getUtmId())){
            Integer intLandingId = Integer.parseInt(landingId);
            // 根据着陆页id获取推荐渠道id
            TemplateDisposeVO templateDisposeVO = registService.selectTemplateDisposeById(intLandingId);
            if(null==templateDisposeVO){
                logger.info("------根据着陆页id："+landingId+" 未能查找到该着陆页配置信息!------");
                ret.put("status", "99");
                ret.put("statusDesc", "未能查找到着陆页配置信息!");
                return ret;
            }
            String templateLanding = templateDisposeVO.getUtmId().toString();
            if(!bean.getUtmId().equals(templateLanding)){
                logger.info("------渠道id传入参数为："+bean.getUtmId()+" 与着陆页配置渠道id"+templateLanding+"不相同!------");
                ret.put("status", "99");
                ret.put("statusDesc", "渠道编码与着陆页配置渠道不相同!");
                return ret;
            }
        }
        // 着陆页配置代码修改  20190621 add by nxl  end
       /* user =  registService.insertUserActionUtm(mobile, password,bean.getVerificationCode(), refferUserId, CustomUtil.getIpAddr(request),
                CustomConstants.CLIENT_WECHAT,bean.getUtmId(),bean.getUtmSource());*/
        WebViewUserVO webViewUserVO = registService.register(mobile,bean.getVerificationCode(), password,refferUserId, CommonConstant.HYJF_INST_CODE,bean.getUtmId(), String.valueOf(ClientConstants.WECHAT_CLIENT),GetCilentIP.getIpAddr(request), userType,null);
        UserVO user = loginService.getUser(webViewUserVO.getUsername());
        WebViewUserVO userVO = loginService.login(webViewUserVO.getUsername(), password, GetCilentIP.getIpAddr(request), BankCallConstant.CHANNEL_WEI,user);
        if(null!=userVO){
            ret.put("sign",userVO.getToken());
        }else {
            ret.put("status", "99");
            ret.put("statusDesc","登陆失败");
            ret.put("successUrl","");
            return ret;
        }
        Integer userId = webViewUserVO.getUserId();
        try {
            if (userId != 0) {
                // 神策数据统计 add by liuyang 20180725 start
                // 注册成功后将用户ID返给前端
                ret.put("userId",String.valueOf(userId));
                if (StringUtils.isNotBlank(presetProps)) {
                    logger.info("注册时预置属性:presetProps" + presetProps);
                    try {
                        SensorsDataBean sensorsDataBean = new SensorsDataBean();
                        // 将json串转换成Bean
                        Map<String, Object> sensorsDataMap = JSONObject.parseObject(presetProps, new TypeReference<Map<String, Object>>() {
                        });
                        sensorsDataBean.setPresetProps(sensorsDataMap);
                        sensorsDataBean.setUserId(userId);
                        // 发送神策数据统计MQ E
                        this.registService.sendSensorsDataMQ(sensorsDataBean);
                        // add by liuyang 神策数据统计追加 登录成功后 将用户ID返回前端 20180717 end
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                }

                // add by cuigq wbs客户信息回调 20190417 start
                if(!(Strings.isNullOrEmpty(bean.getUtmId()) || Strings.isNullOrEmpty(bean.getCustomerId()))){

                    WbsRegisterMqVO wbsRegisterMqVO=new WbsRegisterMqVO();
                    wbsRegisterMqVO.setUtmId(bean.getUtmId());
                    wbsRegisterMqVO.setCustomerId(bean.getCustomerId());
                    wbsRegisterMqVO.setAssetCustomerId(String.valueOf(webViewUserVO.getUserId()));

                    try {
                        registService.sendWbsMQ(wbsRegisterMqVO);
                    } catch (MQException e) {
                        logger.info("用户【{}】注册后，发达MQ到Wbs失败！",webViewUserVO.getUsername());
                        logger.error(e.getMessage(),e);
                    }
                }
                // add by cuigq wbs客户信息回调 20190417 end

                String statusDesc = "注册成功";
                if (registService.checkActivityIfAvailable(systemConfig.getActivity888Id())) {
                    BaseMapBean baseMapBean=new BaseMapBean();
                    baseMapBean.set("imageUrl", "");
                    baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
                    baseMapBean.set(CustomConstants.APP_STATUS_DESC, URLEncoder.encode(statusDesc, "UTF-8"));
                    baseMapBean.set("imageUrlOperation", "");
                    baseMapBean.setCallBackAction(systemConfig.getAppServerHost()+"/user/regist/result/success");
                    ret.put("status", "000");
                    ret.put("statusDesc", statusDesc);
                    ret.put("successUrl",baseMapBean.getUrl());
                    return ret;
                }else {
                    AdsRequest adsRequest = new AdsRequest();
                    adsRequest.setLimitStart(0);
                    adsRequest.setLimitEnd(1);
                    adsRequest.setHost(systemConfig.getFileDomainUrl());
                    adsRequest.setCode("registpop");
                    AppAdsCustomizeVO record = registService.searchBanner(adsRequest);
                    // 注册成功发券提示
                    String operationUrl = "://jumpCouponsList/?";
                    BaseMapBean baseMapBean = new BaseMapBean();
                    baseMapBean.set("imageUrl", record.getImage());
                    baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
                    baseMapBean.set(CustomConstants.APP_STATUS_DESC, URLEncoder.encode(statusDesc, "UTF-8"));
                    baseMapBean.set("imageUrlOperation", operationUrl);
                    baseMapBean.setCallBackAction(systemConfig.getAppServerHost()+"/user/regist/result/success");
                    ret.put("status", "000");
                    ret.put("statusDesc",statusDesc);
                    ret.put("successUrl",baseMapBean.getUrl());
                    return ret;
                }
            } else {
                //注册失败,参数异常
                ret.put("status", "99");
                ret.put("statusDesc", "注册失败,参数异常");
                ret.put("successUrl","");
                return ret;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
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
    
    /**
     * 点击下一步验证
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "-移动端着陆页配置",notes = "移动端着陆页配置")
    @PostMapping(value = "/templateDispose.do")
    public JSONObject templateDispose(HttpServletRequest request, HttpServletResponse response) {
        JSONObject ret = new JSONObject();

        ret.put("status", "000");
        ret.put("statusDesc", "成功");

        String templateId = request.getParameter("templateId");
        if (Validator.isNull(templateId)) {
            ret.put("status", "99");
            ret.put("statusDesc", "templateId不能为空");
            return ret;
        }
        JSONObject data = new JSONObject();
        TemplateDisposeVO rt = registService.getTemplateDispose(templateId);
        if (rt==null) {
            ret.put("status", "99");
            ret.put("statusDesc", "id无效");
            return ret;
        }
        data.put("landingId", rt.getId());
        data.put("title",rt.getTempTitle());
        data.put("name",rt.getUtmName() );
        data.put("utmId", rt.getUtmId());
        data.put("btnText", rt.getButtonText());
        data.put("topImg",rt.getTopImg());
        data.put("bottomImg", rt.getBottomImg());
        data.put("midColor", rt.getBackgroundColor());
        data.put("mainColor",rt.getDominantColor());
        data.put("subColor",rt.getSecondaryColor());
        data.put("alertTitle", rt.getLayerName());
        data.put("alertImg", rt.getLayerImg());
        data.put("alertOpen", rt.getIsJumt());
        data.put("alertUrl", rt.getJumtUrl());
        ret.put("data", data);
        return ret;

    }
}
