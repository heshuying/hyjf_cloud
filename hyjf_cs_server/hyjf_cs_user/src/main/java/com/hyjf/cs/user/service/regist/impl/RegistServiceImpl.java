/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.regist.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.resquest.user.RegisterUserRequest;
import com.hyjf.am.vo.market.AdsVO;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.*;
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
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.wechat.regist.UserRegistResultVO;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.mq.producer.AccountProducer;
import com.hyjf.cs.user.mq.producer.CouponProducer;
import com.hyjf.cs.user.mq.producer.SmsProducer;
import com.hyjf.cs.user.service.BaseUserServiceImpl;
import com.hyjf.cs.user.service.regist.RegistService;
import com.hyjf.cs.user.util.ResultEnum;
import com.hyjf.cs.user.vo.RegisterRequest;
import com.hyjf.cs.user.vo.RegisterVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangqingqing
 * @version RegistServiceImpl, v0.1 2018/6/11 15:10
 */
@Service
public class RegistServiceImpl extends BaseUserServiceImpl implements RegistService {

    private static final Logger logger = LoggerFactory.getLogger(RegistServiceImpl.class);

    @Autowired
    private AmUserClient amUserClient;
    @Autowired
    private AccountProducer accountProducer;
    @Autowired
    private CouponProducer couponProducer;
    @Autowired
    private SmsProducer smsProducer;
    @Autowired
    private SystemConfig systemConfig;
    @Autowired
    private AmMarketClient amMarketClient;

    @Value("${file.domain.head.url}")
    private String fileHeadUrl;
    @Value("${file.upload.head.path}")
    private String fileHeadPath;


    /**
     * api注册参数校验
     *
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
        CheckUtil.check(!existUser(mobile), MsgEnum.STATUS_ZC000005);
        // TODO: 2018/6/23 原代码平台推荐人未作处理
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
        CheckUtil.check(m.matches(), MsgEnum.ERR_FMT_PASSWORD);
        String verificationType = CommonConstant.PARAM_TPL_ZHUCE;
        int cnt = amUserClient.checkMobileCode(mobile, smsCode, verificationType, registerRequest.getPlatform(),
                CommonConstant.CKCODE_YIYAN, CommonConstant.CKCODE_USED);
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
        if(StringUtils.isEmpty(mobile)){
            ret.put(CustomConstants.APP_STATUS, 1);
            ret.put(CustomConstants.APP_STATUS_DESC, "手机号不能为空");
            return ret;
        }
        if(!Validator.isMobile(mobile)){
            ret.put(CustomConstants.APP_STATUS, 1);
            ret.put(CustomConstants.APP_STATUS_DESC, "请填写您的真实手机号码");
            return ret;
        }
        if(existUser(mobile)){
            ret.put(CustomConstants.APP_STATUS, 1);
            ret.put(CustomConstants.APP_STATUS_DESC, "手机号已存在");
            return ret;
        }
        String smsCode = registerRequest.getVerificationCode();
        //验证码不能为空
        if(StringUtils.isEmpty(smsCode)){
            ret.put(CustomConstants.APP_STATUS, 1);
            ret.put(CustomConstants.APP_STATUS_DESC, "验证码不能为空");
            return ret;
        }
        String password = registerRequest.getPassword();
        //密码不能为空
        if(StringUtils.isEmpty(password)){
            ret.put(CustomConstants.APP_STATUS, 1);
            ret.put(CustomConstants.APP_STATUS_DESC, "密码不能为空");
            return ret;
        }
        if(password.length() < 6 || password.length() > 16){
            ret.put(CustomConstants.APP_STATUS, 1);
            ret.put(CustomConstants.APP_STATUS_DESC, "密码长度6-16位");
            return ret;
        }
        boolean hasNumber = false;
        for (int i = 0; i < password.length(); i++) {
            if (Validator.isNumber(password.substring(i, i + 1))) {
                hasNumber = true;
                break;
            }
        }
        if(!hasNumber){
            ret.put(CustomConstants.APP_STATUS, 1);
            ret.put(CustomConstants.APP_STATUS_DESC, "密码必须包含数字");
            return ret;
        }
        String regEx = "^[a-zA-Z0-9]+$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(password);
        if(!m.matches()){
            ret.put(CustomConstants.APP_STATUS, 1);
            ret.put(CustomConstants.APP_STATUS_DESC, "密码必须由数字和字母组成，如abc123");
            return ret;
        }
        String verificationType = CommonConstant.PARAM_TPL_ZHUCE;
        int cnt = amUserClient.checkMobileCode(mobile, smsCode, verificationType, registerRequest.getPlatform(),
                CommonConstant.CKCODE_YIYAN, CommonConstant.CKCODE_USED);
        if(cnt == 0){
            ret.put(CustomConstants.APP_STATUS, 1);
            ret.put(CustomConstants.APP_STATUS_DESC, "验证码错误");
            return ret;
        }
        String reffer = registerRequest.getReffer();
        if (StringUtils.isNotEmpty(reffer)) {
            //无效推荐人
            if(amUserClient.countUserByRecommendName(reffer) <= 0){
                ret.put(CustomConstants.APP_STATUS, 1);
                ret.put(CustomConstants.APP_STATUS_DESC, "推荐人无效");
                return ret;
            }
        }
        return ret;
    }


    /**
     * 1. 必要参数检查 2. 注册 3. 注册后处理
     *
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
        registerUserRequest.setInstCode(CommonConstant.HYJF_INST_CODE);
        // 2.注册
        UserVO userVO = amUserClient.register(registerUserRequest);
        CheckUtil.check(userVO != null, MsgEnum.ERR_USER_REGISTER);
        // 3.注册后处理
        return this.afterRegisterHandle(userVO);
    }

    /**
     * api注册
     *
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
        //HjhInstConfigVO instConfig = this.amTradeClient.selectInstConfigByInstCode(registerRequest.getInstCode());
        // 机构编号
        //CheckUtil.check(instConfig != null, MsgEnum.STATUS_ZC000004);
        registerUserRequest.setInstCode(registerRequest.getInstCode());
        // 验签
        CheckUtil.check(this.verifyRequestSign(registerVO, BaseDefine.METHOD_SERVER_REGISTER), MsgEnum.STATUS_CE000002);
        // 根据渠道号检索推广渠道是否存在
        UtmPlatVO utmPlat = this.amUserClient.selectUtmPlatByUtmId(registerRequest.getUtmId());
        CheckUtil.check(null != utmPlat, MsgEnum.STATUS_ZC000020);
        // 2.注册
        UserVO userVO = amUserClient.register(registerUserRequest);
        CheckUtil.check(userVO != null, MsgEnum.ERR_USER_REGISTER);

        // 3. 注册成功用户保存账户表
        sendMqToSaveAccount(userVO.getUserId());
        return userVO;
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
        sendMqToSaveAccount(userVO.getUserId());
        return userVO;
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

        // 2. 注册成功用户保存账户表
        sendMqToSaveAccount(webViewUserVO.getUserId());

        // 3. 注册送188元新手红包
        // 活动有效期校验
        try {
            Integer activityId = systemConfig.getActivity888Id();
            if (!checkActivityIfAvailable(activityId)) {
                sendCoupon(userVO);
            }
        }catch (Exception e){
            logger.error("注册发放888红包失败...", e);
        }

        return webViewUserVO;
    }

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

    @Override
    public int countUserByRecommendName(String reffer) {
        int cnt = amUserClient.countUserByRecommendName(reffer);
        return cnt;
    }

    @Override
    public AppAdsCustomizeVO searchBanner(AdsRequest adsRequest) {
        AppAdsCustomizeVO appAdsCustomizeVO =  amMarketClient.searchBanner(adsRequest);
        if (null==appAdsCustomizeVO){
            appAdsCustomizeVO = new AppAdsCustomizeVO();
        }
        return appAdsCustomizeVO;
    }

    @Override
    public UserRegistResultVO wechatCheckParam(String mobile, String password, String reffer, String verificationCode) {
        UserRegistResultVO vo = new UserRegistResultVO();
        if (StringUtils.isNotEmpty(reffer)) {
            //无效推荐人
            CheckUtil.check(amUserClient.countUserByRecommendName(reffer) > 0, MsgEnum.ERR_OBJECT_INVALID, "推荐人");
        }
        {
            if (!Validator.isNull(reffer)) {
                int count = amUserClient.countUserByRecommendName(reffer);
                if (count == 0) {
                    vo.setEnum(ResultEnum.ERROR_009);
                    vo.setSuccessUrl("");
                    return vo;
                }
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

            if (password.length() < 6 || password.length() > 16) {
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
            if (!hasNumber) {
                vo.setEnum(ResultEnum.ERROR_014);
                vo.setSuccessUrl("");
                return vo;
            }

            String regEx = "^[a-zA-Z0-9]+$";
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
            {
                if (existUser(mobile)) {
                    vo.setEnum(ResultEnum.ERROR_017);
                    vo.setSuccessUrl("");
                    return vo;
                }
            }
            {
                String verificationType = CommonConstant.PARAM_TPL_ZHUCE;
                int cnt = amUserClient.checkMobileCode(mobile, verificationCode, verificationType, String.valueOf(ClientConstants.WECHAT_CLIENT),
                        CommonConstant.CKCODE_YIYAN, CommonConstant.CKCODE_USED);
                if (cnt == 0) {
                    vo.setEnum(ResultEnum.ERROR_018);
                    vo.setSuccessUrl("");
                    return vo;
                }
            }
        }catch (Exception e){
            vo.setEnum(ResultEnum.PARAM);
            vo.setSuccessUrl("");
            return vo;
        }
        return vo;
    }

    /**
     * 注册保存账户表
     *
     * @param userId
     * @throws MQException
     */
    private void sendMqToSaveAccount(int userId) {
        AccountVO account = new AccountVO();
        account.setUserId(userId);
        // 银行存管相关
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
        account.setIncome(BigDecimal.ZERO);
        account.setExpend(BigDecimal.ZERO);
        account.setBalance(BigDecimal.ZERO);
        account.setBalanceCash(BigDecimal.ZERO);
        account.setBalanceFrost(BigDecimal.ZERO);
        account.setFrost(BigDecimal.ZERO);
        account.setAwait(BigDecimal.ZERO);
        account.setRepay(BigDecimal.ZERO);
        account.setFrostCash(BigDecimal.ZERO);
        account.setRecMoney(BigDecimal.ZERO);
        account.setFee(BigDecimal.ZERO);
        account.setInMoney(BigDecimal.ZERO);
        account.setInMoneyFlag(0);
        account.setPlanAccedeTotal(BigDecimal.ZERO);
        account.setPlanBalance(BigDecimal.ZERO);
        account.setPlanFrost(BigDecimal.ZERO);
        account.setPlanAccountWait(BigDecimal.ZERO);
        account.setPlanCapitalWait(BigDecimal.ZERO);
        account.setPlanInterestWait(BigDecimal.ZERO);
        account.setPlanRepayInterest(BigDecimal.ZERO);
        account.setVersion(BigDecimal.ZERO);
        logger.info("注册插入account：{}", JSON.toJSONString(account));
        try {
            logger.info("发送短信开始");
            accountProducer.messageSend(new MessageContent(MQConstant.ACCOUNT_TOPIC, UUID.randomUUID().toString(),JSON.toJSONBytes(account)));
            logger.info("发送短信结束");
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
            couponProducer.messageSend(new MessageContent(MQConstant.REGISTER_COUPON_TOPIC,
                    MQConstant.REGISTER_COUPON_TAG, UUID.randomUUID().toString(), JSON.toJSONBytes(params)));
        } catch (Exception e) {
            logger.error("注册发放888红包失败...", e);
        }
        // 短信通知用户发券成功
        SmsMessage smsMessage = new SmsMessage(userId, null, userVO.getMobile(), null,
                MessageConstant.SMS_SEND_FOR_MOBILE, null, CustomConstants.PARAM_TPL_TZJ_188HB,
                CustomConstants.CHANNEL_TYPE_NORMAL);
        try {
            smsProducer.messageSend(
                    new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(smsMessage)));
        } catch (MQException e) {
            logger.error("短信发送失败...", e);
        }
    }

    /**
     * 组装 userVO
     * @param userVO
     * @return
     */
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
        webViewUserVO.setIconUrl(this.assembleIconUrl(userVO));
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
            List<BankCardVO> bankCardVOList = amUserClient.getBankOpenAccountById(userVO.getUserId());
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
    private String assembleIconUrl(UserVO userVO) {
        String iconUrl = userVO.getIconUrl();
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
}
