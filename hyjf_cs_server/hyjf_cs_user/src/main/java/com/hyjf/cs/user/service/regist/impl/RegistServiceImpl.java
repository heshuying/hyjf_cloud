/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.regist.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.hyjf.am.resquest.user.RegisterUserRequest;
import com.hyjf.am.vo.market.AdsVO;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.jwt.JwtHelper;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.BaseDefine;
import com.hyjf.cs.user.client.AmMarketClient;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.mq.CouponProducer;
import com.hyjf.cs.user.mq.Producer;
import com.hyjf.cs.user.mq.SmsProducer;
import com.hyjf.cs.user.service.BaseUserServiceImpl;
import com.hyjf.cs.user.service.regist.RegistService;
import com.hyjf.cs.user.vo.RegisterRequest;
import com.hyjf.cs.user.vo.RegisterVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangqingqing
 * @version RegistServiceImpl, v0.1 2018/6/11 15:10
 */
@Service
public class RegistServiceImpl extends BaseUserServiceImpl implements RegistService{

    private static final Logger logger = LoggerFactory.getLogger(RegistServiceImpl.class);

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private CouponProducer couponProducer;

    @Autowired
    private SmsProducer smsProducer;



    @Autowired
    SystemConfig systemConfig;

    @Value("${hyjf.activity.888.id}")
    private Integer activityId;
    @Value("${file.domain.head.url}")
    private String fileHeadUrl;
    @Value("${file.upload.head.path}")
    private String fileHeadPath;

    /**
     * 1. 必要参数检查 2. 注册 3. 注册后处理
     * @param registerRequest
     * @param
     * @param
     * @return
     * @throws ReturnMessageException
     */
    @Override
    public WebViewUserVO register(RegisterRequest registerRequest, String ip)
            throws ReturnMessageException {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        BeanUtils.copyProperties(registerRequest, registerUserRequest);
        registerUserRequest.setLoginIp(ip);
        registerUserRequest.setInstCode(1);
        // 2.注册
        UserVO userVO = amUserClient.register(registerUserRequest);
        CheckUtil.check(userVO != null, MsgEnum.ERR_REGISTER);
        // 3.注册后处理
        return this.afterRegisterHandle(userVO);
    }

    /**
     * api注册
     * @param
     * @param ipAddr
     * @return
     */
    @Override
    public UserVO apiRegister(RegisterRequest registerRequest, String ipAddr) {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        BeanUtils.copyProperties(registerRequest, registerUserRequest);
        RegisterVO registerVO = new RegisterVO();
        BeanUtils.copyProperties(registerRequest, registerVO);
        registerUserRequest.setLoginIp(ipAddr);
        // 根据机构编号检索机构信息
        HjhInstConfigVO instConfig = this.amTradeClient.selectInstConfigByInstCode(registerRequest.getInstCode());
        // 机构编号
        CheckUtil.check(instConfig != null, MsgEnum.ERR_INSTCODE);
        // 验签
        CheckUtil.check(this.verifyRequestSign(registerVO, BaseDefine.METHOD_SERVER_REGISTER),MsgEnum.STATUS_CE000002);
        registerUserRequest.setInstCode(instConfig.getInstType());
        // 2.注册
        UserVO userVO = amUserClient.register(registerUserRequest);
        CheckUtil.check(userVO != null, MsgEnum.ERR_REGISTER);
        return userVO;
    }


    /**
     * 注册参数校验
     *
     * @param
     */
    @Override
    public void registerCheckParam(int client, RegisterRequest registerRequest) {
        if(ClientConstants.API_CLIENT == client){
            // 机构编号
            String instCode = registerRequest.getInstCode();
            // 注册平台
            String platform = registerRequest.getPlatform();
            // 注册渠道
            String utmId = registerRequest.getUtmId();
            // 机构编号
            CheckUtil.check(StringUtils.isNotEmpty(instCode), MsgEnum.ERR_INSTCODE);
            // 注册平台
            CheckUtil.check(StringUtils.isNotEmpty(platform), MsgEnum.ERR_PLATEFORM);
            // 推广渠道
            CheckUtil.check(StringUtils.isNotEmpty(utmId), MsgEnum.ERR_UTMID);
            CheckUtil.check(registerRequest != null, MsgEnum.ERR_REGISTER);
        }
        String mobile = registerRequest.getMobilephone();
        CheckUtil.check(StringUtils.isNotEmpty(mobile), MsgEnum.ERR_MOBILE_IS_NOT_NULL);
        String smsCode = registerRequest.getSmsCode();
        CheckUtil.check(StringUtils.isNotEmpty(smsCode), MsgEnum.ERR_SMSCODE_IS_NOT_NULL);
        String password = registerRequest.getPassword();
        CheckUtil.check(StringUtils.isNotEmpty(password), MsgEnum.ERR_PASSWORD_IS_NOT_NULL);
        CheckUtil.check(Validator.isMobile(mobile), MsgEnum.ERR_MOBILE_IS_NOT_REAL);
        CheckUtil.check(!existUser(mobile), MsgEnum.ERR_MOBILE_EXISTS);
        CheckUtil.check(password.length() >= 6 && password.length() <= 16, MsgEnum.ERR_PASSWORD_LENGTH);
        boolean hasNumber = false;
        for (int i = 0; i < password.length(); i++) {
            if (Validator.isNumber(password.substring(i, i + 1))) {
                hasNumber = true;
                break;
            }
        }
        CheckUtil.check(hasNumber, MsgEnum.ERR_PASSWORD_NO_NUMBER);
        String regEx = "^[a-zA-Z0-9]+$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(password);
        CheckUtil.check(m.matches(), MsgEnum.ERR_PASSWORD_FORMAT);
		String verificationType = CommonConstant.PARAM_TPL_ZHUCE;
      /*  int cnt = amUserClient.checkMobileCode(mobile, smsCode, verificationType, CommonConstant.CLIENT_PC,
                CommonConstant.CKCODE_YIYAN, CommonConstant.CKCODE_USED);
        CheckUtil.check(cnt != 0, MsgEnum.ERR_SMSCODE_INVALID);*/
        String reffer = registerRequest.getReffer();
        if(StringUtils.isNotEmpty(reffer)){
            CheckUtil.check(amUserClient.countUserByRecommendName(reffer) > 0, MsgEnum.ERR_REFFER_INVALID);
        }

    }

    /**
     * 注册后处理: 1. 登录 2. 注册送188红包
     *
     * @param userVO
     */
    private WebViewUserVO afterRegisterHandle(UserVO userVO) {

        int userId = userVO.getUserId();

        // 1. 注册成功之后登录
        String token = generatorToken(userId, userVO.getUsername());
        WebViewUserVO webViewUserVO = this.assembleWebViewUserVO(userVO);
        webViewUserVO.setToken(token);
        RedisUtils.setObjEx(RedisKey.USER_TOKEN_REDIS + token, webViewUserVO, 7 * 24 * 60 * 60);

        // 2. 注册送188元新手红包
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

        return webViewUserVO;
    }


    private WebViewUserVO assembleWebViewUserVO(UserVO userVO) {
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
        webViewUserVO.setIconurl(this.assembleIconUrl(userVO));

        // todo 银行 汇付开户账号 紧急联系人 usersContract
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
    private String assembleIconUrl(UserVO userVO) {
        String iconUrl = ""; // todo getUrlcron
        if (StringUtils.isNotBlank(iconUrl)) {
            String imghost = UploadFileUtils.getDoPath(fileHeadUrl);
            imghost = imghost.substring(0, imghost.length() - 1);
            String fileUploadTempPath = UploadFileUtils.getDoPath(fileHeadPath);
            return imghost + fileUploadTempPath + iconUrl;
        }
        return iconUrl;
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
    public boolean checkActivityIfAvailable(Integer activityId) {
        if (activityId == null) {
            return false;
        }

        AdsVO adsVO = amMarketClient.findAdsById(activityId);

        if (adsVO == null) {
            return false;
        }
        if (adsVO.getTimeStart() > GetDate.getNowTime10()) {
            return false;
        }
        if (adsVO.getTimeEnd() < GetDate.getNowTime10()) {
            return false;
        }

        return true;
    }

    /**
     * 检查短信验证码searchStatus:查询的短信状态,updateStatus:查询结束后的短信状态
     * @param mobile
     * @param verificationCode
     * @param verificationType
     * @param platform
     * @param searchStatus
     * @param updateStatus
     * @return
     */
    @Override
    public int updateCheckMobileCode(String mobile, String verificationCode, String verificationType, String platform,
                                     Integer searchStatus, Integer updateStatus) {
        int cnt = amUserClient.checkMobileCode( mobile,  verificationCode,  verificationType,  platform, searchStatus,  updateStatus);
        return cnt;
    }

   @Override
   public int countUserByRecommendName(String reffer){
        int cnt = amUserClient.countUserByRecommendName(reffer);
        return cnt;
    }

}
