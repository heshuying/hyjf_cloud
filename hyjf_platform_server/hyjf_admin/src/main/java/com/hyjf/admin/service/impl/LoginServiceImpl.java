package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.mq.base.CommonProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.LoginService;
import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.response.config.SmsConfigResponse;
import com.hyjf.am.resquest.admin.SmsConfigRequest;
import com.hyjf.am.resquest.config.AdminSystemRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.TreeVO;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class LoginServiceImpl  implements LoginService {

	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	private AmConfigClient amConfigClient;
	@Autowired
	private AmUserClient amUserClient;

	@Autowired
	private CommonProducer commonProducer;
	@Value("${hyjf.env.test}")
	private boolean hyjfEnvTest;
	
	@Override
	public List<AdminSystemVO> getUserPermission(String userName) {
		
		return amConfigClient.getUserPermission(userName);
	}

	@Override
	public AdminSystemResponse getUserInfo(AdminSystemRequest adminSystemRequest) {

		return amConfigClient.getUserInfo(adminSystemRequest);
	}

	@Override
	public List<TreeVO> selectLeftMenuTree2(String id) {
		return amConfigClient.selectLeftMenuTree2(id);
	}

	@Override
	public AdminSystemResponse updatePasswordAction(AdminSystemRequest map) {
		return amConfigClient.updatePasswordAction(map);
	}

	/**
	 * 根据手机号查询用户
	 *
	 * @param adminSystemRequest
	 * @return
	 */
	@Override
	public AdminSystemResponse getUserInfoByMobile(AdminSystemRequest adminSystemRequest) {
		return amConfigClient.getUserInfoByMobile(adminSystemRequest);
	}

	/**
	 * 检查是否能够发送短信
	 *
	 * @param verificationType
	 * @param mobile
	 * @param id
	 * @param ip
	 * @return
	 */
	@Override
	public String adminSendSmsCodeCheckParam(String verificationType, String mobile, String id, String ip) {
		// 判断发送间隔时间
		String intervalTime = RedisUtils.get(mobile + ":" + verificationType + ":IntervalTime");
		if (StringUtils.isNotBlank(intervalTime)){
			return "请求验证码操作过快";
		}
		String ipCount = RedisUtils.get(RedisConstants.CACHE_MAX_IP_COUNT+ip);
		if (StringUtils.isBlank(ipCount) || !Validator.isNumber(ipCount)) {
			ipCount = "0";
			RedisUtils.set(RedisConstants.CACHE_MAX_IP_COUNT+ip, "0", 24 * 60 * 60);
		}
		logger.info(mobile + "------ip---" + ip + "----------MaxIpCount-----------" + ipCount);
		SmsConfigResponse smsConfig = amConfigClient.initSmsConfig(new SmsConfigRequest());
		if (smsConfig == null || smsConfig.getResult() == null){
			return "获取短信配置失败";
		}
		if (Integer.valueOf(ipCount) >= smsConfig.getResult().getMaxIpCount()) {
			if (!Integer.valueOf(ipCount).equals(smsConfig.getResult().getMaxIpCount())){
				return "该设备短信请求次数超限，请明日再试";
			}
			try {
				// 发送短信通知
				Map<String, String> replaceStrs = new HashMap<String, String>();
				replaceStrs.put("var_phonenu", mobile);
				replaceStrs.put("val_reason", "IP访问次数超限");
				SmsMessage smsMessage = new SmsMessage(null, replaceStrs, null, null,
						MessageConstant.SMS_SEND_FOR_MANAGER, null, CustomConstants.PARAM_TPL_DUANXINCHAOXIAN,
						CustomConstants.CHANNEL_TYPE_NORMAL);
				try {
					commonProducer.messageSend(
							new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), smsMessage));
				} catch (MQException e) {
					logger.error("短信发送失败...", e);
				}
			} catch (Exception e) {
				return "IP访问次数超限";
			}
			RedisUtils.set(RedisConstants.CACHE_MAX_IP_COUNT + ip, (Integer.valueOf(ipCount) + 1) + "", 24 * 60 * 60);
		}
		// 判断最大发送数max_phone_count
		String count = RedisUtils.get(RedisConstants.CACHE_MAX_PHONE_COUNT+mobile);
		if (StringUtils.isBlank(count) || !Validator.isNumber(count)) {
			count = "0";
			RedisUtils.set(RedisConstants.CACHE_MAX_PHONE_COUNT+mobile, "0",24 * 60 * 60);
		}
		logger.info(mobile + "----------MaxPhoneCount-----------" + count);
		if (Integer.valueOf(count) >= smsConfig.getResult().getMaxPhoneCount()) {
			if (!Integer.valueOf(count).equals(smsConfig.getResult().getMaxPhoneCount())){
				return "该设备短信请求次数超限，请明日再试";
			}
			try {
				// 发送短信通知
				Map<String, String> replaceStrs = new HashMap<String, String>();
				replaceStrs.put("var_phonenu", mobile);
				replaceStrs.put("val_reason", "手机验证码发送次数超限");
				SmsMessage smsMessage = new SmsMessage(null, replaceStrs, null, null,
						MessageConstant.SMS_SEND_FOR_MANAGER, null, CustomConstants.PARAM_TPL_DUANXINCHAOXIAN,
						CustomConstants.CHANNEL_TYPE_NORMAL);
				try {
					commonProducer.messageSend(
							new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), smsMessage));
				} catch (MQException e) {
					logger.error("短信发送失败...", e);
				}
			} catch (Exception e) {

				return "该设备短信请求次数超限，请明日再试";
			}
			RedisUtils.set(RedisConstants.CACHE_MAX_PHONE_COUNT+mobile, (Integer.valueOf(count) + 1) + "", 24 * 60 * 60);
		}

		// 发送checkCode最大时间间隔，默认60秒
		RedisUtils.set(mobile + ":" + verificationType + ":IntervalTime", mobile,
				smsConfig.getResult().getMaxIntervalTime() == null ? 60 : smsConfig.getResult().getMaxIntervalTime());
		return null;
	}

	/**
	 * 发送短信
	 *
	 * @param verificationType
	 * @param mobile
	 * @param ipAddr
	 */
	@Override
	public void sendSmsCode(String verificationType, String mobile,  String ipAddr) {
		/// 生成验证码
		String checkCode = GetCode.getRandomSMSCode(6);

		// 测试环境不进行验证
		if (hyjfEnvTest) {
			// 测试环境验证码111111
			checkCode = "111111";
		}

		logger.info("手机号: {}, 短信验证码: {}", mobile, checkCode);
		Map<String, String> param = new HashMap<String, String>();
		param.put("val_code", checkCode);

		// 保存短信验证码
		amUserClient.saveSmsCode(mobile, checkCode, verificationType, CommonConstant.CKCODE_NEW, 0);

		SmsMessage smsMessage = new SmsMessage(null, param, mobile, null, MessageConstant.SMS_SEND_FOR_MOBILE, null,
				verificationType, CustomConstants.CHANNEL_TYPE_NORMAL);

		// 发送
		try {
			commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), smsMessage));
		} catch (MQException e) {
			logger.error("发送短信失败，",e);
		}
		// 累加IP次数
		String currentMaxIpCount = RedisUtils.get(RedisConstants.CACHE_MAX_IP_COUNT+ipAddr);
		if (StringUtils.isBlank(currentMaxIpCount)) {
			currentMaxIpCount = "0";
		}
		// 累加手机次数
		String currentMaxPhoneCount = RedisUtils.get(RedisConstants.CACHE_MAX_PHONE_COUNT+mobile);
		if (StringUtils.isBlank(currentMaxPhoneCount)) {
			currentMaxPhoneCount = "0";
		}
		RedisUtils.set(RedisConstants.CACHE_MAX_IP_COUNT+ipAddr, (Integer.valueOf(currentMaxIpCount) + 1) + "", 24 * 60 * 60);
		RedisUtils.set(RedisConstants.CACHE_MAX_PHONE_COUNT+mobile, (Integer.valueOf(currentMaxPhoneCount) + 1) + "", 24 * 60 * 60);
	}

	/**
	 * 检查短信验证码searchStatus:查询的短信状态,updateStatus:查询结束后的短信状态
	 * @param mobile
	 * @param verificationCode
	 * @param verificationType
	 * @param platform
	 * @param searchStatus
	 * @param updateStatus
	 * @param isUpdate
	 * @return
	 */
	@Override
	public int updateCheckMobileCode(String mobile, String verificationCode, String verificationType, String platform,
									 Integer searchStatus, Integer updateStatus,boolean isUpdate) {
		int cnt = amUserClient.checkMobileCode( mobile,  verificationCode,  verificationType,  platform, searchStatus,  updateStatus,isUpdate);
		return cnt;
	}
}
