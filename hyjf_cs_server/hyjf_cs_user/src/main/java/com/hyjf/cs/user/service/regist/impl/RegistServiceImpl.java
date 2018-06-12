/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.regist.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.hyjf.am.resquest.user.RegisterUserRequest;
import com.hyjf.am.vo.market.ActivityListVO;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUser;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.jwt.JwtHelper;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.client.AmMarketClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.RegisterError;
import com.hyjf.cs.user.mq.CouponProducer;
import com.hyjf.cs.user.mq.MailProducer;
import com.hyjf.cs.user.mq.Producer;
import com.hyjf.cs.user.mq.SmsProducer;
import com.hyjf.cs.user.service.regist.RegistService;
import com.hyjf.cs.user.vo.RegisterVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author zhangqingqing
 * @version RegistServiceImpl, v0.1 2018/6/11 15:10
 */
@Service
public class RegistServiceImpl implements RegistService{

    private static final Logger logger = LoggerFactory.getLogger(RegistServiceImpl.class);

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private CouponProducer couponProducer;

    @Autowired
    private SmsProducer smsProducer;

    @Autowired
    private MailProducer mailProducer;

    @Autowired
    SystemConfig systemConfig;
    @Value("${hyjf.activity.regist.tzj.id}")
    private String activityIdTzj;
    @Value("${hyjf.activity.888.id}")
    private String activityId;

    /**
     * 1. 必要参数检查 2. 注册 3. 注册后处理
     * @param registerVO
     * @param
     * @param
     * @return
     * @throws ReturnMessageException
     */
    @Override
    public UserVO register(RegisterVO registerVO, String ip)
            throws ReturnMessageException {
        // 1. 参数检查
        this.registerCheckParam(registerVO);
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        BeanUtils.copyProperties(registerVO, registerUserRequest);
        registerUserRequest.setLoginIp(ip);
        registerUserRequest.setInstCode(1);
        // 2.注册
        UserVO userVO = amUserClient.register(registerUserRequest);
        if (userVO == null) {
            throw new ReturnMessageException(RegisterError.REGISTER_ERROR);
        }

        // 3.注册后处理
        this.afterRegisterHandle(userVO);

        return userVO;
    }

    /**
     * api注册
     * @param registerVO
     * @param ipAddr
     * @return
     */
    @Override
    public UserVO apiRegister(@Valid RegisterVO registerVO, String ipAddr) {
        // 1. 参数检查
        this.registerCheckParam(registerVO);
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        BeanUtils.copyProperties(registerVO, registerUserRequest);
        registerUserRequest.setLoginIp(ipAddr);
        // 根据机构编号检索机构信息
        HjhInstConfigVO instConfig = this.amUserClient.selectInstConfigByInstCode(registerVO.getInstCode());
        // 机构编号
        if (instConfig == null) {
            throw new ReturnMessageException(RegisterError.INSTCODE_ERROR);

        }
        // TODO: 2018/5/28 验签
        registerUserRequest.setInstCode(instConfig.getInstType());
        // 2.注册
        UserVO userVO = amUserClient.register(registerUserRequest);
        if (userVO == null) {
            throw new ReturnMessageException(RegisterError.REGISTER_ERROR);
        }
        return userVO;
    }


    /**
     * 注册参数校验
     *
     * @param registerVO
     */
    private void registerCheckParam(RegisterVO registerVO) {
        if (registerVO == null) {
            throw new ReturnMessageException(RegisterError.REGISTER_ERROR);
        }

        String mobile = registerVO.getMobilephone();
        if (StringUtils.isEmpty(mobile)) {
            throw new ReturnMessageException(RegisterError.MOBILE_IS_NOT_NULL_ERROR);
        }

        String smsCode = registerVO.getSmsCode();
        if (StringUtils.isEmpty(smsCode)) {
            throw new ReturnMessageException(RegisterError.SMSCODE_IS_NOT_NULL_ERROR);
        }

        String password = registerVO.getPassword();
        if (StringUtils.isEmpty(password)) {
            throw new ReturnMessageException(RegisterError.PASSWORD_IS_NOT_NULL_ERROR);
        }

        if (!Validator.isMobile(mobile)) {
            throw new ReturnMessageException(RegisterError.MOBILE_IS_NOT_REAL_ERROR);
        } else {
            if (existUser(mobile)) {
                throw new ReturnMessageException(RegisterError.MOBILE_EXISTS_ERROR);
            }
        }
        if (password.length() < 6 || password.length() > 16) {
            throw new ReturnMessageException(RegisterError.PASSWORD_LENGTH_ERROR);
        }

        boolean hasNumber = false;
        for (int i = 0; i < password.length(); i++) {
            if (Validator.isNumber(password.substring(i, i + 1))) {
                hasNumber = true;
                break;
            }
        }
        if (!hasNumber) {
            throw new ReturnMessageException(RegisterError.PASSWORD_NO_NUMBER_ERROR);
        }
        String regEx = "^[a-zA-Z0-9]+$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(password);
        if (!m.matches()) {
            throw new ReturnMessageException(RegisterError.PASSWORD_FORMAT_ERROR);
        }
		String verificationType = CommonConstant.PARAM_TPL_ZHUCE;
        int cnt = amUserClient.checkMobileCode(mobile, smsCode, verificationType, CommonConstant.CLIENT_PC,
                CommonConstant.CKCODE_YIYAN, CommonConstant.CKCODE_USED);
        if (cnt == 0) {
            throw new ReturnMessageException(RegisterError.SMSCODE_INVALID_ERROR);
        }
        String reffer = registerVO.getReffer();
        if (isNotBlank(reffer) && amUserClient.countUserByRecommendName(reffer) <= 0) {
            throw new ReturnMessageException(RegisterError.REFFER_INVALID_ERROR);
        }
    }

    /**
     * 注册后处理: 1. 登录 2. 判断投之家着陆页送券 3. 注册送188红包
     *
     * @param userVO
     */
    private void afterRegisterHandle(UserVO userVO) {
        int userId = userVO.getUserId();

        // 1. 注册成功之后登录
        String token = generatorToken(userId, userVO.getUsername());
        WebViewUser webViewUser = new WebViewUser();
        BeanUtils.copyProperties(userVO,webViewUser);
        webViewUser.setToken(token);
        userVO.setToken(token);
        RedisUtils.setObjEx(RedisKey.USER_TOKEN_REDIS + token, webViewUser, 7 * 24 * 60 * 60);

        // 2. 投之家用户注册送券活动
        // 活动有效期校验
        if (!checkActivityIfAvailable(activityIdTzj)) {
            // 投之家用户额外发两张加息券
            if ("touzhijia".equals(userVO.getReferrerUserName())) {
                // 发放两张加息券
                JSONObject json = new JSONObject();
                json.put("userId", userId);
                json.put("couponSource", 2);
                json.put("couponCode", "PJ2958703");
                json.put("sendCount", 2);
                json.put("activityId", Integer.parseInt(activityIdTzj));
                json.put("remark", "投之家用户注册送加息券");
                json.put("sendFlg", 0);
                try {
                    couponProducer.messageSend(new Producer.MassageContent(MQConstant.REGISTER_COUPON_TOPIC,
                            MQConstant.TZJ_REGISTER_INTEREST_TAG, JSON.toJSONBytes(json)));
                } catch (MQException e) {
                    logger.error("投之家用户注册送券失败....userId is :" + userId, e);
                }
            }
        }

        // 3. 注册送188元新手红包
        if (!checkActivityIfAvailable(activityId)) {
            try {
                JSONObject params = new JSONObject();
                params.put("mqMsgId", GetCode.getRandomCode(10));
                params.put("userId", String.valueOf(userId));
                params.put("sendFlg", "11");
                couponProducer.messageSend(new Producer.MassageContent(MQConstant.REGISTER_COUPON_TOPIC,
                        MQConstant.REGISTER_COUPON_TAG, JSON.toJSONBytes(params)));
            } catch (Exception e) {
                logger.error("注册发放888红包失败...", e);
            }

            // 短信通知用户发券成功
            SmsMessage smsMessage = new SmsMessage(userId, null, userVO.getMobile(), null,
                    MessageConstant.SMSSENDFORMOBILE, null, CustomConstants.PARAM_TPL_TZJ_188HB,
                    CustomConstants.CHANNEL_TYPE_NORMAL);
            try {
                smsProducer.messageSend(
                        new Producer.MassageContent(MQConstant.SMS_CODE_TOPIC, JSON.toJSONBytes(smsMessage)));
            } catch (MQException e) {
                logger.error("短信发送失败...", e);
            }
        }
    }

    /**
     * jwt生成token
     *
     * @param userId
     * @param username
     * @return
     */
    private String generatorToken(int userId, String username) {
        Map map = ImmutableMap.of("userId", String.valueOf(userId), "username", username, "ts",
                String.valueOf(Instant.now().getEpochSecond()));
        String token = JwtHelper.genToken(map);
        return token;
    }

    @Override
    public boolean existUser(String mobile) {
        UserVO userVO = amUserClient.findUserByMobile(mobile);
        return userVO == null ? false : true;
    }

    @Autowired
    private AmMarketClient amMarketClient;

    @Override
    public boolean checkActivityIfAvailable(String activityId) {
        if (activityId == null) {
            return false;
        }

        ActivityListVO activityVO = amMarketClient.findActivityById(new Integer(activityId));

        if (activityVO == null) {
            return false;
        }
        if (activityVO.getTimeStart() > GetDate.getNowTime10()) {
            return false;
        }
        if (activityVO.getTimeEnd() < GetDate.getNowTime10()) {
            return false;
        }

        return true;
    }
}
