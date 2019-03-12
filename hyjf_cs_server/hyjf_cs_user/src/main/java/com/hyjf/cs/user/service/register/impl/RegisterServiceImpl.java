/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.register.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.am.resquest.user.RegisterUserRequest;
import com.hyjf.am.vo.market.ActivityListVO;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.BaseDefine;
import com.hyjf.cs.user.bean.UserRegisterRequestBean;
import com.hyjf.cs.user.client.AmMarketClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.ResultEnum;
import com.hyjf.cs.user.mq.base.CommonProducer;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.result.UserRegistResult;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.service.register.RegisterService;
import com.hyjf.cs.user.util.GetInfoByUserIp;
import com.hyjf.cs.user.vo.RegisterRequest;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangqingqing
 * @version RegistServiceImpl, v0.1 2018/6/11 15:10
 */
@Service
//@DefaultProperties(defaultFallback = "defaultFallback")
public class RegisterServiceImpl extends BaseUserServiceImpl implements RegisterService {

    private static final Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);

    @Autowired
    private AmUserClient amUserClient;
    @Autowired
    private SystemConfig systemConfig;
    @Autowired
    private AmMarketClient amMarketClient;
    @Autowired
    private CommonProducer commonProducer;

    /**
     * api注册参数校验
     *
     * @param
     * @param
     */
    @Override
    public void apiCheckParam(RegisterRequest registerRequest) {
        // 手机号
        String mobile = registerRequest.getMobile();
        // 机构编号
        String instCode = registerRequest.getInstCode();
        // 注册平台
        String platform = registerRequest.getPlatform();
        // 注册渠道
        String utmId = registerRequest.getUtmId();
        //推荐人
        String reffer = registerRequest.getReffer();
        //手机号未填写
        CheckUtil.check(StringUtils.isNotEmpty(mobile), MsgEnum.STATUS_ZC000001);
        // 机构编号
        CheckUtil.check(StringUtils.isNotEmpty(instCode), MsgEnum.STATUS_ZC000002);
        // 注册平台
        CheckUtil.check(StringUtils.isNotEmpty(platform), MsgEnum.STATUS_ZC000018);
        // 推广渠道
        CheckUtil.check(StringUtils.isNotEmpty(utmId), MsgEnum.STATUS_ZC000019);

        CheckUtil.check(Validator.isMobile(mobile), MsgEnum.STATUS_ZC000003);

        // 2018/6/23 原代码平台推荐人未作处理
        if (StringUtils.isNotEmpty(reffer)) {
             // CheckUtil.check(amUserClient.countUserByRecommendName(recommended) > 0, MsgEnum.ERR_OBJECT_INVALID,"推荐人");//无效的推荐人
        }
    }

    /**
     * 参数校验
     *
     * @param registerRequest
     */
    @Override
    public void checkParam(RegisterRequest registerRequest) {
        String mobile = registerRequest.getMobile();
        //手机号未填写
        CheckUtil.check(StringUtils.isNotEmpty(mobile), MsgEnum.ERR_OBJECT_BLANK, "手机号");
        CheckUtil.check(Validator.isMobile(mobile), MsgEnum.ERR_MOBILE_IS_NOT_REAL);
        CheckUtil.check(!existUser(mobile), MsgEnum.ERR_MOBILE_EXISTS);
        String smsCode = registerRequest.getVerificationCode();
        //验证码不能为空
        CheckUtil.check(StringUtils.isNotEmpty(smsCode), MsgEnum.ERR_OBJECT_REQUIRED, "验证码");
        String password = registerRequest.getPassword();
        //密码不能为空
        CheckUtil.check(StringUtils.isNotEmpty(password), MsgEnum.ERR_OBJECT_REQUIRED, "密码");
        CheckUtil.check(password.length() >= 8 && password.length() <= 16, MsgEnum.ERR_PASSWORD_LENGTH);
        boolean hasNumber = false;
        for (int i = 0; i < password.length(); i++) {
            if (Validator.isNumber(password.substring(i, i + 1))) {
                hasNumber = true;
                break;
            }
        }
//        CheckUtil.check(hasNumber, MsgEnum.ERR_PASSWORD_NO_NUMBER);
        String regEx = "^(?![0-9]+$)(?![a-zA-Z]+$)(?![\\`\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\-\\=\\{\\}\\|\\[\\]\\\\\\;\\'\\:\\\"\\,\\.\\/\\<\\>\\?]+$)[0-9A-Za-z\\`\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\-\\=\\{\\}\\|\\[\\]\\\\\\;\\'\\:\\\"\\,\\.\\/\\<\\>\\?]{8,16}$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(password);
        CheckUtil.check(m.matches(), MsgEnum.ERR_FMT_PASSWORD);
        String verificationType = CommonConstant.PARAM_TPL_ZHUCE;
        int cnt = amUserClient.checkMobileCode(mobile, smsCode, verificationType, registerRequest.getPlatform(),
                CommonConstant.CKCODE_YIYAN, CommonConstant.CKCODE_USED, true);
        CheckUtil.check(cnt != 0, MsgEnum.STATUS_ZC000015);
        String reffer = registerRequest.getReffer();
        if (StringUtils.isNotEmpty(reffer)) {
            //无效推荐人
            CheckUtil.check(amUserClient.countUserByRecommendName(reffer) > 0, MsgEnum.ERR_OBJECT_INVALID, "推荐人");
        }
    }

    /**
     * 参数校验
     *
     * @param registerRequest
     */
    @Override
    public JSONObject appCheckParam(RegisterRequest registerRequest) {
        JSONObject ret = new JSONObject();
        String mobile = registerRequest.getMobile();
        //手机号未填写
        if (StringUtils.isEmpty(mobile)) {
            ret.put(CustomConstants.APP_STATUS, 1);
            ret.put(CustomConstants.APP_STATUS_DESC, "手机号不能为空");
            return ret;
        }
        if (!Validator.isMobile(mobile)) {
            ret.put(CustomConstants.APP_STATUS, 1);
            ret.put(CustomConstants.APP_STATUS_DESC, "请填写您的真实手机号码");
            return ret;
        }
        if (existUser(mobile)) {
            ret.put(CustomConstants.APP_STATUS, 1);
            ret.put(CustomConstants.APP_STATUS_DESC, "手机号已存在");
            return ret;
        }
        String smsCode = registerRequest.getVerificationCode();
        //验证码不能为空
        if (StringUtils.isEmpty(smsCode)) {
            ret.put(CustomConstants.APP_STATUS, 1);
            ret.put(CustomConstants.APP_STATUS_DESC, "验证码不能为空");
            return ret;
        }
        String password = registerRequest.getPassword();
        //密码不能为空
        if (StringUtils.isEmpty(password)) {
            ret.put(CustomConstants.APP_STATUS, 1);
            ret.put(CustomConstants.APP_STATUS_DESC, "密码不能为空");
            return ret;
        }
        if (password.length() < 8 || password.length() > 16) {
            ret.put(CustomConstants.APP_STATUS, 1);
            ret.put(CustomConstants.APP_STATUS_DESC, "密码长度8-16位");
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
//            ret.put(CustomConstants.APP_STATUS, 1);
//            ret.put(CustomConstants.APP_STATUS_DESC, "密码必须包含数字");
//            return ret;
//        }
        String regEx = "^(?![0-9]+$)(?![a-zA-Z]+$)(?![\\`\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\-\\=\\{\\}\\|\\[\\]\\\\\\;\\'\\:\\\"\\,\\.\\/\\<\\>\\?]+$)[0-9A-Za-z\\`\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\-\\=\\{\\}\\|\\[\\]\\\\\\;\\'\\:\\\"\\,\\.\\/\\<\\>\\?]{8,16}$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(password);
        if (!m.matches()) {
            ret.put(CustomConstants.APP_STATUS, 1);
            ret.put(CustomConstants.APP_STATUS_DESC, "必须包含数字、字母、符号至少两种");
            return ret;
        }
        String reffer = registerRequest.getReffer();
        if (StringUtils.isNotEmpty(reffer)) {
            //无效推荐人
            if (amUserClient.countUserByRecommendName(reffer) <= 0) {
                ret.put(CustomConstants.APP_STATUS, 1);
                ret.put(CustomConstants.APP_STATUS_DESC, "推荐人无效");
                return ret;
            }
        }
        String verificationType = CommonConstant.PARAM_TPL_ZHUCE;
        int cnt = amUserClient.checkMobileCode(mobile, smsCode, verificationType, registerRequest.getPlatform(),
                CommonConstant.CKCODE_YIYAN, CommonConstant.CKCODE_USED, true);
        if (cnt == 0) {
            ret.put(CustomConstants.APP_STATUS, 1);
            ret.put(CustomConstants.APP_STATUS_DESC, "验证码错误");
            return ret;
        }
        return ret;
    }


    /**
     * 1. 必要参数检查 2. 注册 3. 注册后处理
     *
     * @param mobile
     * @param verificationCode
     * @param password
     * @param reffer
     * @param instCode
     * @param utmId
     * @param platform
     * @param ip
     * @param userType 0:普通用户;1:企业用户;
     * @return
     * @throws ReturnMessageException
     */
    @Override
    @HystrixCommand(commandKey="用户注册(三端)-register", fallbackMethod = "fallBackRegister",ignoreExceptions = CheckException.class,commandProperties = {
            //设置断路器生效
          @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),

          @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
            //一个统计窗口内熔断触发的最小个数3/10s
          @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
            //熔断5秒后去尝试请求
          @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
            //失败率达到30百分比后熔断
          @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30"),
          // 超时时间
          @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value = "50")})
    public WebViewUserVO register(String mobile, String verificationCode, String password, String reffer, String instCode, String utmId, String platform, String ip, Integer userType)
            throws ReturnMessageException {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest(mobile, verificationCode, password, reffer, instCode, utmId, platform, userType);
        registerUserRequest.setLoginIp(ip);

        //add by libin 用户注册时通过ip获得用户所在的省，市 start
        logger.info("获取到的用户ip为：" + ip);
        String info = GetInfoByUserIp.getInfoByUserIp(ip);
        if(info == null || StringUtils.isEmpty(info)){
        	logger.error("通过httpRequest获取的ip解析后未获取到省市信息！");
        	registerUserRequest.setProvince("");
        	registerUserRequest.setCity("");
        } else {
        	StringBuffer line = new StringBuffer(info);
        	int first_idx   = line.indexOf("|");
        	String country = line.substring(0, first_idx);//所属国家暂时不用先保留

        	line = new StringBuffer(line.substring(first_idx + 1) );
        	int second_idx   = line.indexOf("|");
        	String number = line.substring(0, second_idx);//所属数字暂时不用先保留

            line = new StringBuffer(line.substring(second_idx + 1) );
            int thrid_idx   = line.indexOf("|");
            String province = line.substring(0, thrid_idx);//省
            if(province != null && StringUtils.isNotEmpty(province)){
            	registerUserRequest.setProvince(province);
            } else {
            	registerUserRequest.setProvince("");
            }

            line = new StringBuffer(line.substring(thrid_idx + 1) );
            int fouth_idx   = line.indexOf("|");
            String city = line.substring(0, fouth_idx);//市
            if(city != null && StringUtils.isNotEmpty(city)){
            	registerUserRequest.setCity(city);
            } else {
            	registerUserRequest.setCity("");
            }
        }
        //add by libin 用户注册时通过ip获得用户所在的省，市 end

        // 2.注册
        UserVO userVO = amUserClient.register(registerUserRequest);
        logger.info("注册之后user值是否为空："+(userVO==null));
        CheckUtil.check(userVO != null, MsgEnum.ERR_USER_REGISTER);
        // 3.注册后处理
        return this.afterRegisterHandle(userVO,Integer.parseInt(platform));
    }

    /**
     * 默认fallback
     * @return
     */
    private WebViewUserVO fallBackRegister(String mobile, String verificationCode, String password, String reffer, String instCode, String utmId, String platform, String ip, Integer userType) {
        logger.info("==================已进入 用户注册(三端) fallBackRegister 方法================");
        return null;
    }

    /**
     * api注册
     *
     * @param
     * @param userRegisterRequestBean
     * @param ipAddr
     * @return
     */
    @Override
    @HystrixCommand(commandKey = "注册(api)-apiRegister",fallbackMethod = "fallBackApiRegister",ignoreExceptions = CheckException.class,commandProperties = {
            //设置断路器生效
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            //一个统计窗口内熔断触发的最小个数3/10s
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
            @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value = "50"),
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
            //熔断5秒后去尝试请求
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
            //失败率达到30百分比后熔断
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30")})
    public UserVO apiRegister(UserRegisterRequestBean userRegisterRequestBean, RegisterRequest registerRequest, String ipAddr) {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        BeanUtils.copyProperties(registerRequest, registerUserRequest);
        registerUserRequest.setLoginIp(ipAddr);
        // 根据机构编号检索机构信息
        HjhInstConfigVO instConfig = this.amTradeClient.selectInstConfigByInstCode(registerRequest.getInstCode());
        // 机构编号
        CheckUtil.check(instConfig != null, MsgEnum.STATUS_ZC000004);
        registerUserRequest.setInstType(instConfig.getInstType());
        // 验签
        CheckUtil.check(this.verifyRequestSign(userRegisterRequestBean, BaseDefine.METHOD_SERVER_REGISTER), MsgEnum.STATUS_CE000002);
        // 根据渠道号检索推广渠道是否存在
        UtmPlatVO utmPlat = this.amUserClient.selectUtmPlatByUtmId(registerRequest.getUtmId());
        CheckUtil.check(null != utmPlat, MsgEnum.STATUS_ZC000020);
        //密码
        registerUserRequest.setPassword(systemConfig.getApiPass());

        //add by libin 用户注册时通过ip获得用户所在的省，市 start
        logger.info("获取到的用户ip为：" + ipAddr);
        String info = GetInfoByUserIp.getInfoByUserIp(ipAddr);
        if(info == null || StringUtils.isEmpty(info)){
            logger.error("通过httpRequest获取的ip解析后未获取到省市信息！");
            registerUserRequest.setProvince("");
            registerUserRequest.setCity("");
        } else {
            StringBuffer line = new StringBuffer(info);
            int first_idx   = line.indexOf("|");
            String country = line.substring(0, first_idx);//所属国家暂时不用先保留

            line = new StringBuffer(line.substring(first_idx + 1) );
            int second_idx   = line.indexOf("|");
            String number = line.substring(0, second_idx);//所属数字暂时不用先保留

            line = new StringBuffer(line.substring(second_idx + 1) );
            int thrid_idx   = line.indexOf("|");
            String province = line.substring(0, thrid_idx);//省
            if(province != null && StringUtils.isNotEmpty(province)){
                registerUserRequest.setProvince(province);
            } else {
                registerUserRequest.setProvince("");
            }

            line = new StringBuffer(line.substring(thrid_idx + 1) );
            int fouth_idx   = line.indexOf("|");
            String city = line.substring(0, fouth_idx);//市
            if(city != null && StringUtils.isNotEmpty(city)){
                registerUserRequest.setCity(city);
            } else {
                registerUserRequest.setCity("");
            }
        }
        //add by libin 用户注册时通过ip获得用户所在的省，市 end
        // 2.注册
        UserVO userVO = amUserClient.register(registerUserRequest);
        CheckUtil.check(userVO != null, MsgEnum.ERR_USER_REGISTER);

        // 3. 注册成功用户保存账户表
        sendMqToSaveAccount(userVO.getUserId(), userVO.getUsername());
        return userVO;
    }

    public UserVO fallBackApiRegister(UserRegisterRequestBean userRegisterRequestBean, RegisterRequest registerRequest, String ipAddr) {
        logger.info("==================已进入 用户注册(api) fallBackApiRegister 方法================");
        return null;
    }

    @Override
    public UserVO surongRegister(RegisterRequest registerRequest, String ipAddr, String platform) {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        BeanUtils.copyProperties(registerRequest, registerUserRequest);
        registerUserRequest.setLoginIp(ipAddr);
        registerUserRequest.setInstCode("10000017");
        registerUserRequest.setPlatform(platform);
        UserVO userVO = amUserClient.surongRegister(registerUserRequest);
        CheckUtil.check(userVO != null, MsgEnum.ERR_USER_REGISTER);
        // 注册成功用户保存账户表
        sendMqToSaveAccount(userVO.getUserId(), userVO.getUsername());
        return userVO;
    }

    /**
     * 注册后处理: 1. 登录 2. 注册送188红包
     *
     * @param userVO
     */
    private WebViewUserVO afterRegisterHandle(UserVO userVO,int platform) {
        int userId = userVO.getUserId();
        // 1. 注册成功之后登录
        String token = generatorToken(userId, userVO.getUsername());
        WebViewUserVO webViewUserVO = this.assembleWebViewUserVO(userVO,platform);
        webViewUserVO.setToken(token);
        RedisUtils.setObjEx(RedisConstants.USERID_KEY + userId, webViewUserVO, 7 * 24 * 60 * 60);

        // 2. 注册成功用户保存账户表
        sendMqToSaveAccount(webViewUserVO.getUserId(), webViewUserVO.getUsername());

        // 3. 注册送188元新手红包
        // 活动有效期校验
        try {
            Integer activityId = systemConfig.getActivity888Id();
            logger.info("注册送188元新手红包:"+activityId);
            if (checkActivityIfAvailable(activityId)) {
                sendCoupon(userVO);
                webViewUserVO.setCouponSendCount(8);
            }
        } catch (Exception e) {
            logger.error("注册发放888红包失败...", e);
        }

        return webViewUserVO;
    }

    @Override
    public boolean checkActivityIfAvailable(Integer activityId) {
        if (activityId == null) {
            return false;
        }
        ActivityListVO activityListVO = amMarketClient.selectActivityList(activityId);
        if (activityListVO == null) {
            return false;
        }
        if (activityListVO.getTimeStart() > GetDate.getNowTime10()) {
            return false;
        }
        if( activityListVO.getTimeEnd() < GetDate.getNowTime10()) {
            return false;
        }

        return true;
    }

    @Override
    public int countUserByRecommendName(String reffer) {
        int cnt = amUserClient.countUserByRecommendName(reffer);
        return cnt;
    }

    @Override
    public AppAdsCustomizeVO searchBanner(AdsRequest adsRequest) {
        AppAdsCustomizeVO appAdsCustomizeVO = amMarketClient.searchBanner(adsRequest);
        if (null == appAdsCustomizeVO) {
            appAdsCustomizeVO = new AppAdsCustomizeVO();
        }
        return appAdsCustomizeVO;
    }

    @Override
    public UserRegistResult wechatCheckParam(String mobile, String password, String reffer, String verificationCode) {
        UserRegistResult vo = new UserRegistResult();
        if (StringUtils.isNotEmpty(reffer)) {
            //无效推荐人
            CheckUtil.check(amUserClient.countUserByRecommendName(reffer) > 0, MsgEnum.ERR_OBJECT_INVALID, "推荐人");
        }

        if (!Validator.isNull(reffer)) {
            int count = amUserClient.countUserByRecommendName(reffer);
            if (count == 0) {
                vo.setEnum(ResultEnum.ERROR_009);
                vo.setSuccessUrl("");
                return vo;
            }
        }

        // 检查参数正确性
        if (Validator.isNull(mobile) || Validator.isNull(verificationCode)) {
            vo.setEnum(ResultEnum.PARAM);
            vo.setSuccessUrl("");
            return vo;
        }
        // 业务逻辑
        try {

            logger.info("当前注册手机号: {}", mobile);
            if (Validator.isNull(mobile)) {
                vo.setEnum(ResultEnum.ERROR_010);
                vo.setSuccessUrl("");
                return vo;
            }
            if (Validator.isNull(verificationCode)) {
                vo.setEnum(ResultEnum.ERROR_011);
                vo.setSuccessUrl("");
                return vo;
            }
            if (Validator.isNull(password)) {
                vo.setEnum(ResultEnum.ERROR_012);
                vo.setSuccessUrl("");
                return vo;
            }

            if (password.length() < 8 || password.length() > 16) {
                vo.setEnum(ResultEnum.ERROR_013);
                vo.setSuccessUrl("");
                return vo;
            }

            boolean hasNumber = false;

            for (int i = 0; i < password.length(); i++) {
                if (Validator.isNumber(password.substring(i, i + 1))) {
                    hasNumber = true;
                    break;
                }
            }
//            if (!hasNumber) {
//                vo.setEnum(ResultEnum.ERROR_014);
//                vo.setSuccessUrl("");
//                return vo;
//            }

            String regEx = "^(?![0-9]+$)(?![a-zA-Z]+$)(?![\\`\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\-\\=\\{\\}\\|\\[\\]\\\\\\;\\'\\:\\\"\\,\\.\\/\\<\\>\\?]+$)[0-9A-Za-z\\`\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\-\\=\\{\\}\\|\\[\\]\\\\\\;\\'\\:\\\"\\,\\.\\/\\<\\>\\?]{8,16}$";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(password);
            if (!m.matches()) {
                vo.setEnum(ResultEnum.ERROR_015);
                vo.setSuccessUrl("");
                return vo;
            }
            if (!Validator.isMobile(mobile)) {
                vo.setEnum(ResultEnum.ERROR_016);
                vo.setSuccessUrl("");
                return vo;
            }

            if (existUser(mobile)) {
                vo.setEnum(ResultEnum.ERROR_017);
                vo.setSuccessUrl("");
                return vo;
            }

            String verificationType = CommonConstant.PARAM_TPL_ZHUCE;
            int cnt = amUserClient.checkMobileCode(mobile, verificationCode, verificationType, String.valueOf(ClientConstants.WECHAT_CLIENT),
                    CommonConstant.CKCODE_YIYAN, CommonConstant.CKCODE_USED, true);
            if (cnt == 0) {
                vo.setEnum(ResultEnum.ERROR_018);
                vo.setSuccessUrl("");
                return vo;
            }

        } catch (Exception e) {
            vo.setEnum(ResultEnum.PARAM);
            vo.setSuccessUrl("");
            return vo;
        }
        return vo;
    }

    /**
     * 登录操作
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    @Override
    public int updateLoginInAction(String userName, String password, String ipAddr) {
        String codeSalt = "";
        String passwordDb = "";
        Integer userId = null;

        UserVO userVO = amUserClient.findUserByUserNameOrMobile(userName);

        if (userVO == null) {
            return -1;
        } else {
            userId = userVO.getUserId();
            codeSalt = userVO.getSalt();
            passwordDb = userVO.getPassword();
            if (userVO.getStatus() == 1) {
                return -4;
            }
        }

        // 验证用的password
        password = MD5Utils.MD5(MD5Utils.MD5(password) + codeSalt);
        // 密码正确时
        if (Validator.isNotNull(userId) && Validator.isNotNull(password) && password.equals(passwordDb)) {
            // 更新登录信息
            amUserClient.updateLoginUser(userId, ipAddr);
            return userId;
        } else {
            return -3;
        }
    }

    @Override
    public String getAccountId(Integer userId) {
        AccountVO account = amTradeClient.getAccount(userId);
        if (null != account) {
            return account.getAccountId();
        }
        return null;
    }

    @Override
    public String getAutoInvesStatus(Integer userId) {
        HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthByUserId(userId);
        if (null != hjhUserAuth) {
            return String.valueOf(hjhUserAuth.getAutoInvesStatus());
        }
        return null;
    }

    @Override
    public void sendMqToSaveAppChannel(String version, WebViewUserVO webViewUserVO) {
        Integer sourceId = -1;
        if (StringUtils.isNotBlank(version)) {
            String[] shuzu = version.split("\\.");
            if (shuzu.length >= 4) {
                try {
                    sourceId = Integer.parseInt(shuzu[3]);
                } catch (Exception e) {
                }
                // 查询推广渠道
                    UtmPlatVO plat = amUserClient.selectUtmPlatByUtmId(sourceId.toString());

                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("sourceId",sourceId);
                    params.put("sourceName",plat!=null&&plat.getSourceName() != null ? plat.getSourceName() : "");
                    params.put("userId",webViewUserVO.getUserId());
                    params.put("userName",webViewUserVO.getUsername());
                    params.put("firstInvestTime",0);
                    params.put("investAmount",0.00);
                    params.put("registerTime",new Date());
                    params.put("cumulativeInvest",BigDecimal.ZERO);
                    try {
                        commonProducer.messageSend(new MessageContent(MQConstant.APP_CHANNEL_STATISTICS_DETAIL_TOPIC,
                                MQConstant.APP_CHANNEL_STATISTICS_DETAIL_SAVE_TAG, UUID.randomUUID().toString(), params));
                    } catch (MQException e) {
                        logger.error(e.getMessage());
                        logger.error("app注册推广保存用户数据！！！");
                    }
            }
        }
    }

    /**
     * 注册成功后,发送神策统计MQ
     *
     * @param sensorsDataBean
     */
    @Override
    public void sendSensorsDataMQ(SensorsDataBean sensorsDataBean) throws MQException {
        this.commonProducer.messageSendDelay(new MessageContent(MQConstant.SENSORSDATA_REGISTER_TOPIC,UUID.randomUUID().toString(), sensorsDataBean),2);
    }

    /**
     * 注册保存账户表
     *
     * @param userId
     * @throws MQException
     */
    private void sendMqToSaveAccount(int userId, String userName) {
        AccountVO account = new AccountVO();
        account.setUserId(userId);
        account.setUserName(userName);
        // 银行存管相关
        account.setPlanAccedeBalance(BigDecimal.ZERO);
        account.setPlanAccedeFrost(BigDecimal.ZERO);
        account.setBankBalance(BigDecimal.ZERO);
        account.setBankBalanceCash(BigDecimal.ZERO);
        account.setBankFrost(BigDecimal.ZERO);
        account.setBankFrostCash(BigDecimal.ZERO);
        account.setBankInterestSum(BigDecimal.ZERO);
        account.setBankInvestSum(BigDecimal.ZERO);
        account.setBankWaitCapital(BigDecimal.ZERO);
        account.setBankWaitInterest(BigDecimal.ZERO);
        account.setBankWaitRepay(BigDecimal.ZERO);
        account.setBankTotal(BigDecimal.ZERO);
        account.setBankAwaitCapital(BigDecimal.ZERO);
        account.setBankAwaitInterest(BigDecimal.ZERO);
        account.setBankAwait(BigDecimal.ZERO);
        account.setBankWaitRepayOrg(BigDecimal.ZERO);
        account.setBankAwaitOrg(BigDecimal.ZERO);
        // 汇付相关
        account.setTotal(BigDecimal.ZERO);
        account.setBalance(BigDecimal.ZERO);
        account.setBalanceCash(BigDecimal.ZERO);
        account.setBalanceFrost(BigDecimal.ZERO);
        account.setFrost(BigDecimal.ZERO);
        account.setAwait(BigDecimal.ZERO);
        account.setRepay(BigDecimal.ZERO);
        account.setPlanAccedeTotal(BigDecimal.ZERO);
        account.setPlanBalance(BigDecimal.ZERO);
        account.setPlanFrost(BigDecimal.ZERO);
        account.setPlanAccountWait(BigDecimal.ZERO);
        account.setPlanCapitalWait(BigDecimal.ZERO);
        account.setPlanInterestWait(BigDecimal.ZERO);
        account.setPlanRepayInterest(BigDecimal.ZERO);
        logger.info("注册插入account：{}", JSON.toJSONString(account));
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.ACCOUNT_TOPIC, UUID.randomUUID().toString(), account));
        } catch (MQException e) {
            logger.error("注册成功推送account——mq失败.... user_id is :{}", userId);
            throw new RuntimeException("注册成功推送account——mq失败...");
        }
    }

    /**
     * 发送消息
     *
     * @param userVO
     */
    private void sendCoupon(UserVO userVO) {
        int userId = userVO.getUserId();
        try {
            JSONObject params = new JSONObject();
            params.put("mqMsgId", GetCode.getRandomCode(10));
            params.put("userId", String.valueOf(userId));
            params.put("sendFlg", "11");
            commonProducer.messageSendDelay(new MessageContent(MQConstant.GRANT_COUPON_TOPIC,
                    UUID.randomUUID().toString(), params),1);
        } catch (Exception e) {
            logger.error("注册发放888红包失败...", e);
        }
        // 短信通知用户发券成功
        SmsMessage smsMessage = new SmsMessage(userId, null, userVO.getMobile(), null,
                MessageConstant.SMS_SEND_FOR_MOBILE, null, CustomConstants.PARAM_TPL_TZJ_188HB,
                CustomConstants.CHANNEL_TYPE_NORMAL);
        try {
            commonProducer.messageSend(
                    new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), smsMessage));
        } catch (MQException e) {
            logger.error("短信发送失败...", e);
        }
    }

    /**
     * 组装 userVO
     *
     * @param
     * @return
     */
    private WebViewUserVO assembleWebViewUserVO(UserVO paramUser,int platform) {

        // 此时userVO只包含传递参数数据，数据库初始化的数据(非空字段有默认值)并不包含，所以调用一次查询
        UserVO userVO = amUserClient.findUserById(paramUser.getUserId());
        if(userVO == null){
            userVO = new UserVO();
            BeanUtils.copyProperties(paramUser, userVO);
        }

        WebViewUserVO webViewUserVO = new WebViewUserVO();
        BeanUtils.copyProperties(userVO, webViewUserVO);
        UserInfoVO usersInfo = amUserClient.findUserInfoById(userVO.getUserId());
        if (usersInfo != null) {
            webViewUserVO.setSex(usersInfo.getSex());
            webViewUserVO.setTruename(usersInfo.getTruename());
            webViewUserVO.setIdcard(usersInfo.getIdcard());
            webViewUserVO.setRoleId(usersInfo.getRoleId() + "");
            webViewUserVO.setBorrowerType(usersInfo.getBorrowerType());
        }
        webViewUserVO.setIconUrl(this.assembleIconUrl(userVO,platform));
        webViewUserVO.setOpenAccount(false);
        webViewUserVO.setBankOpenAccount(false);
        if (null != userVO.getOpenAccount() && userVO.getOpenAccount() == 1) {
            AccountChinapnrVO chinapnr = amUserClient.getAccountChinapnr(userVO.getUserId());
            webViewUserVO.setOpenAccount(true);
            //咱们平台的汇付账号
            webViewUserVO.setChinapnrUsrid(chinapnr.getChinapnrUsrid());
            webViewUserVO.setChinapnrUsrcustid(chinapnr.getChinapnrUsrcustid());
        }
        if (null != userVO.getBankOpenAccount() && userVO.getBankOpenAccount() == 1) {
            List<BankCardVO> bankCardVOList = amUserClient.getTiedCardForBank(userVO.getUserId());
            if (null != bankCardVOList && bankCardVOList.size() > 0) {
                BankCardVO bankCardVO = bankCardVOList.get(0);
                webViewUserVO.setBankOpenAccount(true);
                webViewUserVO.setBankAccount(bankCardVO.getCardNo());
            }
        }
        UsersContactVO usersContactVO = amUserClient.selectUserContact(userVO.getUserId());
        webViewUserVO.setUsersContact(usersContactVO);
        userVO.setOpenAccount(null);
        userVO.setBankOpenAccount(null);
        return webViewUserVO;
    }

    /**
     * 组装图像url
     *
     * @param userVO
     * @return
     */
    private String assembleIconUrl(UserVO userVO,int platform) {
        String iconUrl = userVO.getIconUrl();
        if (StringUtils.isNotBlank(iconUrl)) {
            String imghost = UploadFileUtils.getDoPath(systemConfig.getFileDomainUrl());
            imghost = imghost.substring(0, imghost.length() - 1);
            String fileUploadTempPath = UploadFileUtils.getDoPath(systemConfig.getFileUpload(ClientConstants.APP_CLIENT));
            return imghost + fileUploadTempPath + iconUrl;
        }
        return "";
    }


}
