package com.hyjf.cs.message.handle;

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
import com.hyjf.cs.message.mongo.mc.SmsLogDao;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component
public class SmsHandle {
	private static Logger logger = LoggerFactory.getLogger(SmsHandle.class);
	/**
	 * http请求参数集合
	 */
	private Map<String, String> parmMap = null;

	/**
	 * http请求参数集合
	 */
	private Map<String, String> parmMapMaketing = null;

	@Autowired
	private SmsLogDao smsLogDao;
	@Autowired
	private AmUserClient amUserClient;
	@Autowired
	private AmConfigClient amConfigClient;

	@Value("${sms.send.title}")
	private String title;
	@Value("${sms.send.suffix}")
	private String suffix;

	@Value("${sms.send.url}")
	private String url;
	@Value("${sms.send.softSerialNo}")
	private String softSerialNo;
	@Value("${sms.send.key}")
	private String key;

	@Value("${sms.send.market.url}")
	private String marketUrl;
	@Value("${sms.send.market.softSerialNo}")
	private String marketSoftSerialNo;
	@Value("${sms.send.market.key}")
	private String marketKey;

	@Value(("${hyjf.env.test}"))
	private boolean envTest;

	/**
	 * 获取参数集合
	 *
	 * @return
	 * @throws Exception
	 */
	private Map<String, String> getParmMap(String channelType) throws Exception {
		if (parmMap == null || parmMapMaketing == null) {
			syncInitParmMap();
		}
		if (CustomConstants.CHANNEL_TYPE_NORMAL.equals(channelType)) {
			return parmMap;
		} else {
			return parmMapMaketing;
		}
	}

	private synchronized void syncInitParmMap() {
		if (parmMap == null || parmMapMaketing == null) {
			try {
				parmMap = new HashMap<String, String>();
				parmMap.put("cdkey", softSerialNo);
				parmMap.put("password", key);
				parmMapMaketing = new HashMap<String, String>();
				parmMapMaketing.put("cdkey", marketSoftSerialNo);
				parmMapMaketing.put("password", marketKey);
			} catch (Exception e) {
				e.printStackTrace();
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
		String smsSendUrl = url;
		Map<String, String> parmMap = getParmMap(channelType);
		parmMap.put("phone", mobile);
		if (CustomConstants.CHANNEL_TYPE_MARKETING.equals(channelType)) {
			parmMap.put("message", title + messageStr + suffix);
			smsSendUrl = marketUrl;
		} else {
			parmMap.put("message", title + messageStr);
		}

		String result = null;
		int status = 0;
		if (!envTest) {
			result = HttpDeal.post(smsSendUrl, parmMap).trim();
			logger.info("短信发送结果: {}", result);
			if (StringUtils.isBlank(result)) {
				logger.error("调用短信平台失败...parmMap is：{}", JSONObject.toJSONString(parmMap));
				return 0;
			}
			XStream xStream = new XStream();
			xStream.alias("response", SmsResponse.class);
			status = ((SmsResponse) xStream.fromXML(result)).getError();
		} else {
			status = 1;
		}

		SmsLog smsLog = new SmsLog();
		smsLog.setType(type);
		smsLog.setContent(messageStr);// 短信内容
		smsLog.setPosttime(GetDate.getNowTime10());
		smsLog.setMobile(mobile);
		if (StringUtils.isEmpty(sender)) {
			smsLog.setSender(title);
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
					logger.error("无可用通知配置模板,status:[{}],name:[{}]",request.getStatus(),tplCode);
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
					logger.error("无可用通知配置模板,status:[{}],name:[{}]",request.getStatus(),tplCode);
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
				//下面这个抛出异常的，可以等上线再开启
				//throw new Exception("用户ID不可为空");
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
				logger.error("用户电话为空");
				//下面这个抛出异常的，可以等上线再开启
				//throw new Exception("用户电话为空");
				return status;
			}
			if (!Validator.isPhoneNumber(mobile)) {
				logger.error("用户电话号码格式不正确");
				//下面这个抛出异常的，可以等上线再开启
				//throw new Exception("用户电话号码格式不正确");
				return status;
			}

			UserInfoVO userInfoVO = amUserClient.findUsersInfoById(userId);
			// 为保护客户隐私，只显示客户姓氏，不显示客户全名。 胡宝志20160115
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
				logger.error("无可用短信模板,查询条件为status:[1,开启状态的模板],TPLCode:[{}]",tplCode);
				//下面这个抛出异常的，可以等上线再开启
				//throw new RuntimeException("无可用短信模板...");
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
