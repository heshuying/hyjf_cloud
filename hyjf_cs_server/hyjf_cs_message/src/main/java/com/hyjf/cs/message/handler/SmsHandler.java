package com.hyjf.cs.message.handler;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.config.SmsNoticeConfigRequest;
import com.hyjf.am.resquest.config.SmsTemplateRequest;
import com.hyjf.am.vo.config.SmsNoticeConfigVO;
import com.hyjf.am.vo.config.SmsTemplateVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.http.HttpDeal;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.message.bean.mc.SmsLog;
import com.hyjf.cs.message.client.AmConfigClient;
import com.hyjf.cs.message.client.AmUserClient;
import com.hyjf.cs.message.config.PropertiesConfig;
import com.hyjf.cs.message.config.properties.SmsProperties;
import com.hyjf.cs.message.mongo.mc.SmsLogDao;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.HttpHostConnectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component
public class SmsHandler {
	private static Logger logger = LoggerFactory.getLogger(SmsHandler.class);
	/**
	 * http请求参数集合
	 */
	private Map<String, String> paramMap = null;

	/**
	 * http请求参数集合
	 */
	private Map<String, String> paramMapMaketing = null;

	@Autowired
	private SmsLogDao smsLogDao;
	@Autowired
	private AmUserClient amUserClient;
	@Autowired
	private AmConfigClient amConfigClient;
	@Autowired
	private SmsProperties smsProperties;

	/**
	 * 获取参数集合
	 *
	 * @return
	 * @throws Exception
	 */
	private Map<String, String> getParamMap(String channelType) throws Exception {
		if (paramMap == null || paramMapMaketing == null) {
			syncInitParmMap();
		}
		if (CustomConstants.CHANNEL_TYPE_NORMAL.equals(channelType)) {
			return paramMap;
		} else {
			return paramMapMaketing;
		}
	}

	private synchronized void syncInitParmMap() {
		if (paramMap == null || paramMapMaketing == null) {
			try {
				paramMap = new HashMap<>();
				paramMap.put("cdkey", smsProperties.getSoftSerialNo());
				paramMap.put("password", smsProperties.getKey());
				paramMapMaketing = new HashMap<>();
				paramMapMaketing.put("cdkey", smsProperties.getMarketSoftSerialNo());
				paramMapMaketing.put("password", smsProperties.getMarketKey());
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}

	/**
	 * 
	 * 根据电话号码,消息内容发送消息
	 *
	 * @param mobile
	 *            电话号码,多个号码拿逗号分隔
	 * @param messageStr
	 *            发送的消息内容
	 * @param type
	 *            短信发送类型
	 * @param sender
	 *            发送者,默认为【汇盈金服】
	 * @return 返回结果,0表示发送成功
	 * @throws Exception   AAAAAAAAAAAAAAA
	 */
	public Integer sendMessage(String mobile, String messageStr, String type, String sender, String channelType)
			throws Exception {
		String smsSendUrl = smsProperties.getUrl();
		Map<String, String> paramMap = getParamMap(channelType);
		paramMap.put("phone", mobile);
		if (CustomConstants.CHANNEL_TYPE_MARKETING.equals(channelType)) {
			paramMap.put("message", smsProperties.getTitle() + messageStr + smsProperties.getSuffix());
			smsSendUrl = smsProperties.getMarketUrl();
		} else {
			paramMap.put("message", smsProperties.getTitle() + messageStr);
		}

		String result = null;
		int status = 0;
		if (!PropertiesConfig.isPassSend(mobile)) {
			logger.info("测试环境非白名单内不发送短信, mobile is : {}", mobile);
			status = 1;
		} else {
			try{
				result = HttpDeal.post(smsSendUrl, paramMap).trim();
			} catch (Exception e){
				if(e instanceof HttpHostConnectException){
					logger.warn("短信运营商未配置白名单....");
				}
				logger.error("捕捉短信发送异常， 避免影响记录保存...", e);
			}

			logger.info("短信发送结果: {}", result);
			if (StringUtils.isBlank(result)) {
				logger.error("调用短信平台失败...parmMap is：{}", JSONObject.toJSONString(paramMap));
				return 0;
			}
			XStream xStream = new XStream();
			xStream.alias("response", SmsResponse.class);
			status = ((SmsResponse) xStream.fromXML(result)).getError();
		}

		SmsLog smsLog = new SmsLog();
		smsLog.setType(type);
		smsLog.setContent(messageStr);// 短信内容
		smsLog.setPosttime(GetDate.getNowTime10());
		smsLog.setMobile(mobile);
		if (StringUtils.isEmpty(sender)) {
			smsLog.setSender(smsProperties.getTitle());
		} else {
			smsLog.setSender(sender);
		}
		smsLog.setStatus(status);
		// 入库
		smsLogDao.save(smsLog);
		return status;
	}

	/**
	 *
	 * 根据电话号码,消息内容发送消息
	 *
	 * @param mobile
	 *            电话号码,多个号码拿逗号分隔
	 * @param messageStr
	 *            发送的消息内容
	 * @param type
	 *            短信发送类型
	 * @param sender
	 *            发送者,默认为【汇盈金服】
	 * @return 返回结果,0表示发送成功
	 * @throws Exception   AAAAAAAAAAAAAAA
	 */
	public Integer sendMessage(String mobile, String messageStr, String type, String sender, String channelType, Integer isDisplay)
			throws Exception {
		String smsSendUrl = smsProperties.getUrl();
		Map<String, String> paramMap = getParamMap(channelType);
		paramMap.put("phone", mobile);
		if (CustomConstants.CHANNEL_TYPE_MARKETING.equals(channelType)) {
			paramMap.put("message", smsProperties.getTitle() + messageStr + smsProperties.getSuffix());
			smsSendUrl = smsProperties.getMarketUrl();
		} else {
			paramMap.put("message", smsProperties.getTitle() + messageStr);
		}

		String result = null;
		int status = 0;
		if (!PropertiesConfig.isPassSend(mobile)) {
			logger.info("测试环境非白名单内不发送短信, mobile is : {}", mobile);
			status = 1;
		} else {
			result = HttpDeal.post(smsSendUrl, paramMap).trim();
			logger.info("短信发送结果: {}", result);
			if (StringUtils.isBlank(result)) {
				logger.error("调用短信平台失败...parmMap is：{}", JSONObject.toJSONString(paramMap));
				return 0;
			}
			XStream xStream = new XStream();
			xStream.alias("response", SmsResponse.class);
			status = ((SmsResponse) xStream.fromXML(result)).getError();
		}

		SmsLog smsLog = new SmsLog();
		smsLog.setType(type);
		smsLog.setContent(messageStr);// 短信内容
		smsLog.setPosttime(GetDate.getNowTime10());
		smsLog.setMobile(mobile);
		smsLog.setIsDisplay(isDisplay);
		if (StringUtils.isEmpty(sender)) {
			smsLog.setSender(smsProperties.getTitle());
		} else {
			smsLog.setSender(sender);
		}
		smsLog.setStatus(status);
		// 入库
		smsLogDao.save(smsLog);
		return status;
	}

	/**
	 * 通知配置,根据模版给指定管理员手机号发送消息（满标，标到期等）
	 *
	 * @param tplCode
	 *            模板Code
	 * @param replaceStrs
	 *            需要替换的字符串Map<需替换的字符串,替换值> 例:[value_title]:HDD151210404
	 * @return
	 * @throws Exception
	 */
	public Integer sendMessages(String tplCode, Map<String, String> replaceStrs, String channelType) {
		int status = -1;
		try {
			// 获取模板信息
			try {
				SmsNoticeConfigRequest request = new SmsNoticeConfigRequest();
				// 只查询开启状态
				request.setStatus(1);
				request.setName(tplCode);
				SmsNoticeConfigVO smsNoticeConfig = amConfigClient.findSmsNotice(request);
				if (smsNoticeConfig == null) {
					logger.warn("无可用通知配置模板,status:[{}],name:[{}]",request.getStatus(),tplCode);
					//下面这个抛出异常的，可以等上线再开启
					//throw new RuntimeException("无可用通知配置模板");
					return status;
				}
				String mobile = smsNoticeConfig.getConfigValue();
				String messageStr = smsNoticeConfig.getContent();
				if (Validator.isNotNull(messageStr)) {
					if (replaceStrs != null && replaceStrs.size() > 0) {
						for (String s : replaceStrs.keySet()) {
							messageStr = StringUtils.replace(messageStr, "[" + s + "]", replaceStrs.get(s));
						}
					}
				}
				// 发送短信
				status = sendMessage(mobile, messageStr, smsNoticeConfig.getTitle(), null, channelType);
			} catch (Exception e) {
				logger.error("短信发送失败...", e);

			}
		} catch (Exception e) {
			logger.error("短信发送失败...", e);
		}
		return status;
	}

	/**
	 * 通知配置,根据模版给指定管理员手机号发送消息（满标，标到期等）
	 *
	 * @param tplCode
	 *            模板Code
	 * @param replaceStrs
	 *            需要替换的字符串Map<需替换的字符串,替换值> 例:[value_title]:HDD151210404
	 * @return
	 * @throws Exception
	 */
	public Integer sendMessages(String tplCode, Map<String, String> replaceStrs, String sender, String channelType) {
		int status = -1;
		try {
			// 获取模板信息
			try {
				SmsNoticeConfigRequest request = new SmsNoticeConfigRequest();
				// 只查询开启状态
				request.setStatus(1);
				request.setName(tplCode);
				SmsNoticeConfigVO smsNoticeConfig = amConfigClient.findSmsNotice(request);
				if (smsNoticeConfig == null) {
					logger.warn("无可用通知配置模板,status:[{}],name:[{}]",request.getStatus(),tplCode);
					//下面这个抛出异常的，可以等上线再开启
					//throw new RuntimeException("无可用通知配置模板");
					return status;
				}
				String mobile = smsNoticeConfig.getConfigValue();
				String messageStr = smsNoticeConfig.getContent();
				if (Validator.isNotNull(messageStr)) {
					if (replaceStrs != null && replaceStrs.size() > 0) {
						for (String s : replaceStrs.keySet()) {
							messageStr = StringUtils.replace(messageStr, "[" + s + "]", replaceStrs.get(s));
						}
					}
				}

				// 发送短信
				status = sendMessage(mobile, messageStr, smsNoticeConfig.getTitle(), sender, channelType);
			} catch (Exception e) {
				logger.error("短信发送失败...", e);

			}
		} catch (Exception e) {
			logger.error("短信发送失败...", e);
		}
		return status;
	}

	/**
	 * 根据用户ID和模版号给某用户发短信
	 *
	 * @param userId
	 *            电话号码,多个电话号码用逗号分隔
	 * @param tplCode
	 *            模板Code
	 * @param replaceStrs
	 *            需要替换的字符串Map<需替换的字符串,替换值> 例:[value_name]:陈真
	 * @return
	 * @throws Exception
	 */
	public Integer sendMessages(Integer userId, String tplCode, Map<String, String> replaceStrs, String channelType) {
		int status = -1;
		try {
			if (Validator.isNull(userId)) {
				logger.error("用户ID不可为空");
				return status;
			}

			UserVO user = amUserClient.findUserById(userId);
			if (tplCode != null && tplCode.equals(CustomConstants.PARAM_TPL_CHONGZHI_SUCCESS)) {
				if (user.getRechargeSms() != null && user.getRechargeSms() != 0) {
					return 0;
				}
			}
			if (tplCode != null && tplCode.equals(CustomConstants.PARAM_TPL_TIXIAN_SUCCESS)) {
				if (user.getWithdrawSms() != null && user.getWithdrawSms() != 0) {
					return 0;
				}
			}
			if (tplCode != null && tplCode.equals(CustomConstants.PARAM_TPL_TOUZI_SUCCESS)) {
				if (user.getInvestSms() != null && user.getInvestSms() != 0) {
					return 0;
				}
			}
			if (tplCode != null && tplCode.equals(CustomConstants.PARAM_TPL_SHOUDAOHUANKUAN)) {
				if (user.getRecieveSms() != null && user.getRecieveSms() != 0) {
					return 0;
				}
			}
			String mobile = user.getMobile();
			if (StringUtils.isEmpty(mobile)) {
				logger.error("手机号不能为空， userId is : {}", userId);
				return status;
			}
			if (!Validator.isPhoneNumber(mobile)) {
				logger.error("用户电话号码格式不正确");
				return status;
			}

			UserInfoVO userInfoVO = amUserClient.findUsersInfoById(userId);
			// 为保护客户隐私，只显示客户姓氏，不显示客户全名。
			replaceStrs.put("val_name", userInfoVO.getTruename().substring(0, 1));
			replaceStrs.put("val_sex", userInfoVO.getSex() == 1 ? "先生" : "女士");

			status = sendMessages(mobile, tplCode, replaceStrs, channelType);
		} catch (Exception e) {
			logger.error("短信发送失败...", e);
			return -1;
		}
		return status;
	}

	/**
	 * 根据电话号码和模版号给某电话发短信
	 *
	 * @param mobile
	 *            电话号码,多个电话号码用逗号分隔
	 * @param tplCode
	 *            tpl_code
	 * @param replaceStrs
	 *            需要替换的字符串Map<需替换的字符串,替换值> 例:[value_name]:陈真
	 * @return
	 * @throws Exception
	 */
	public Integer sendMessages(String mobile, String tplCode, Map<String, String> replaceStrs, String channelType) {
		int status = -1;
		try {
			SmsTemplateRequest request = new SmsTemplateRequest();
			// 只查询开启状态模板
			request.setStatus(1);
			request.setTplCode(tplCode);
			SmsTemplateVO smsTemplate = amConfigClient.findSmsTemplate(request);
			if (smsTemplate == null) {
				logger.warn("无可用短信模板,查询条件为status:[1,开启状态的模板],TPLCode:[{}]",tplCode);
				return status;
			}
			String messageStr = smsTemplate.getTplContent();
			if (Validator.isNotNull(messageStr)) {
				if (replaceStrs != null && replaceStrs.size() > 0) {
					for (String s : replaceStrs.keySet()) {
						messageStr = StringUtils.replace(messageStr, "[" + s + "]", replaceStrs.get(s));
					}
				}
			}
			// 发送短信
			status = sendMessage(mobile, messageStr, smsTemplate.getTplName(), null, channelType);
		} catch (Exception e) {
			logger.error("短信发送失败...", e);
		}
		return status;
	}

	/**
	 * 短信调用返回
	 */
	private static class SmsResponse implements Serializable {

		/**
		 * 此处为属性说明
		 */
		private static final long serialVersionUID = -3195210420206026269L;

		public Integer getError() {
			return error;
		}

		public void setError(Integer error) {
			this.error = error;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		private Integer error;

		private String message;

	}
}
