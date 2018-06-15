package com.hyjf.cs.user.service.smscode.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.vo.config.SmsConfigVO;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUser;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.enums.utils.MsgEnum;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.mq.Producer;
import com.hyjf.cs.user.mq.SmsProducer;
import com.hyjf.cs.user.service.smscode.SmsCodeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiasq
 * @version SmsCodeServiceImpl, v0.1 2018/4/25 9:02
 */
@Service
public class SmsCodeServiceImpl implements SmsCodeService {
	private static final Logger logger = LoggerFactory.getLogger(SmsCodeServiceImpl.class);

	@Autowired
	private AmUserClient amUserClient;
	@Autowired
	private SmsProducer smsProducer;
	@Autowired
	private AmConfigClient amConfigClient;


	/**
	 * 1. 验证码发送前校验 2. 生成验证码 3. 保存验证码 4. 发送短信
	 *
	 * @param validCodeType
	 * @param mobile
	 * @param token
	 * @param ip
	 * @throws MQException
	 */
	@Override
	public void sendSmsCode(String validCodeType, String mobile, String token, String ip) throws MQException {

		this.sendSmsCodeCheckParam(validCodeType, mobile, token, ip);

		// 生成验证码
		String checkCode = GetCode.getRandomSMSCode(6);
		logger.info("手机号: {}, 短信验证码: {}", mobile, checkCode);
		Map<String, String> param = new HashMap<String, String>();
		param.put("val_code", checkCode);

		// 保存短信验证码
		amUserClient.saveSmsCode(mobile, checkCode, validCodeType, CommonConstant.CKCODE_NEW, CommonConstant.CLIENT_PC);

		SmsMessage smsMessage = new SmsMessage(null, param, mobile, null, MessageConstant.SMSSENDFORMOBILE, null,
				validCodeType, CustomConstants.CHANNEL_TYPE_NORMAL);

		// 发送
		smsProducer.messageSend(new Producer.MassageContent(MQConstant.SMS_CODE_TOPIC, JSON.toJSONBytes(smsMessage)));
	}

	/**
	 * 参数检查
	 * 
	 * @param validCodeType
	 * @param mobile
	 * @param token
	 * @param ip
	 */
	private void sendSmsCodeCheckParam(String validCodeType, String mobile, String token, String ip) {

		List<String> codeTypes = Arrays.asList(CommonConstant.PARAM_TPL_ZHUCE, CommonConstant.PARAM_TPL_ZHAOHUIMIMA,
				CommonConstant.PARAM_TPL_YZYSJH, CommonConstant.PARAM_TPL_BDYSJH);
		CheckUtil.check(Validator.isNotNull(validCodeType) && codeTypes.contains(validCodeType), MsgEnum.CODETYPE_INVALID_ERROR);
		CheckUtil.check(Validator.isNotNull(mobile) && Validator.isMobile(mobile), MsgEnum.MOBILE_FORMAT_ERROR);
		if (validCodeType.equals(CommonConstant.PARAM_TPL_ZHUCE)) {
			// 注册时要判断不能重复
			CheckUtil.check(!existUser(mobile), MsgEnum.MOBILE_EXISTS_ERROR);
		}
		if(validCodeType.equals(CommonConstant.PARAM_TPL_YZYSJH)||validCodeType.equals(CommonConstant.PARAM_TPL_BDYSJH)){
			if (StringUtils.isNotEmpty(token)) {
				WebViewUser webViewUser = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUser.class);
				CheckUtil.check(webViewUser != null, MsgEnum.USER_NOT_EXISTS_ERROR);
				// 验证原手机号校验
				if (validCodeType.equals(CommonConstant.PARAM_TPL_YZYSJH)) {
					CheckUtil.check(StringUtils.isNotBlank(webViewUser.getMobile()), MsgEnum.USER_NOT_EXISTS_ERROR);
					CheckUtil.check(webViewUser.getMobile().equals(mobile), MsgEnum.MOBILE_NEED_SAME_ERROR);
				}
				// 绑定新手机号校验
				if (validCodeType.equals(CommonConstant.PARAM_TPL_BDYSJH)) {
					CheckUtil.check(!webViewUser.getMobile().equals(mobile), MsgEnum.MOBILE_MODIFY_ERROR);
					CheckUtil.check(!existUser(mobile), MsgEnum.MOBILE_EXISTS_ERROR);
				}
			}
		}

		// 判断发送间隔时间
		String intervalTime = RedisUtils.get(mobile + ":" + validCodeType + ":IntervalTime");
		logger.info(mobile + ":" + validCodeType + "----------IntervalTime-----------" + intervalTime);
		CheckUtil.check(StringUtils.isBlank(intervalTime), MsgEnum.SEND_SMSCODE_TOO_FAST_ERROR);
		String ipCount = RedisUtils.get(ip + ":MaxIpCount");
		if (StringUtils.isBlank(ipCount) || !Validator.isNumber(ipCount)) {
			ipCount = "0";
			RedisUtils.set(ip + ":MaxIpCount", "0");
		}
		logger.info(mobile + "------ip---" + ip + "----------MaxIpCount-----------" + ipCount);
		SmsConfigVO smsConfig = amConfigClient.findSmsConfig();
		CheckUtil.check(smsConfig != null, MsgEnum.FIND_SMSCONFIG_ERROR);
		if (Integer.valueOf(ipCount) >= smsConfig.getMaxIpCount()) {
			CheckUtil.check(Integer.valueOf(ipCount).equals(smsConfig.getMaxIpCount()), MsgEnum.SEND_SMSCODE_TOO_MANNY_ERROR);
				try {
					// 发送短信通知
					Map<String, String> replaceStrs = new HashMap<String, String>();
					replaceStrs.put("var_phonenu", mobile);
					replaceStrs.put("val_reason", "IP访问次数超限");
					SmsMessage smsMessage = new SmsMessage(null, replaceStrs, null, null,
							MessageConstant.SMSSENDFORMANAGER, null, CustomConstants.PARAM_TPL_DUANXINCHAOXIAN,
							CustomConstants.CHANNEL_TYPE_NORMAL);
					try {
						smsProducer.messageSend(
								new Producer.MassageContent(MQConstant.SMS_CODE_TOPIC, JSON.toJSONBytes(smsMessage)));
					} catch (MQException e) {
						logger.error("短信发送失败...", e);
					}
				} catch (Exception e) {
					CheckUtil.check(false, MsgEnum.IP_VISIT_TOO_MANNY_ERROR);
				}
				RedisUtils.set(ip + ":MaxIpCount", (Integer.valueOf(ipCount) + 1) + "", 24 * 60 * 60);
		}
		// 判断最大发送数max_phone_count
		String count = RedisUtils.get(mobile + ":MaxPhoneCount");
		if (StringUtils.isBlank(count) || !Validator.isNumber(count)) {
			count = "0";
			RedisUtils.set(mobile + ":MaxPhoneCount", "0");
		}
		logger.info(mobile + "----------MaxPhoneCount-----------" + count);
		if (Integer.valueOf(count) >= smsConfig.getMaxPhoneCount()) {
			CheckUtil.check(Integer.valueOf(count).equals(smsConfig.getMaxPhoneCount()), MsgEnum.SEND_SMSCODE_TOO_MANNY_ERROR);
				try {
					// 发送短信通知
					Map<String, String> replaceStrs = new HashMap<String, String>();
					replaceStrs.put("var_phonenu", mobile);
					replaceStrs.put("val_reason", "手机验证码发送次数超限");
					SmsMessage smsMessage = new SmsMessage(null, replaceStrs, null, null,
							MessageConstant.SMSSENDFORMANAGER, null, CustomConstants.PARAM_TPL_DUANXINCHAOXIAN,
							CustomConstants.CHANNEL_TYPE_NORMAL);
					try {
						smsProducer.messageSend(
								new Producer.MassageContent(MQConstant.SMS_CODE_TOPIC, JSON.toJSONBytes(smsMessage)));
					} catch (MQException e) {
						logger.error("短信发送失败...", e);
					}
				} catch (Exception e) {
					CheckUtil.check(false, MsgEnum.SEND_SMSCODE_TOO_MANNY_ERROR);
				}
				RedisUtils.set(mobile + ":MaxPhoneCount", (Integer.valueOf(count) + 1) + "", 24 * 60 * 60);
		}

		// 发送checkCode最大时间间隔，默认60秒
		RedisUtils.set(mobile + ":" + validCodeType + ":IntervalTime", mobile,
				smsConfig.getMaxIntervalTime() == null ? 60 : smsConfig.getMaxIntervalTime());
	}

	@Override
	public boolean existUser(String mobile) {
		UserVO userVO = amUserClient.findUserByMobile(mobile);
		return userVO == null ? false : true;
	}


}
