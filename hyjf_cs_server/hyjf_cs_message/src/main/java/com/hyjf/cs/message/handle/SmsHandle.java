package com.hyjf.cs.message.handle;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.hyjf.cs.message.bean.SmsLog;
import com.hyjf.cs.message.client.AmConfigClient;
import com.hyjf.cs.message.client.AmUserClient;
import com.hyjf.cs.message.mongo.SmsLogDao;
import com.hyjf.am.vo.config.SmsNoticeConfigVO;
import com.hyjf.am.vo.config.SmsTemplateVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.http.HttpDeal;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.lang3.StringUtils;
import com.hyjf.common.util.CustomConstants;

import cn.emay.sdk.client.api.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SmsHandle {
	private static final String TITLE = "【汇盈金服】";

	private static final String SUFFIX = " 回复TD退订";

	/** 软件序列号 */
	private static final String SOFT_SERIALNO = "9SDK-EMY-0999-JBVLP";

	/** 自定义关键字(key值) */
	private static final String KEY = "286141";

	/** 软件序列号 */
	private static final String SOFT_SERIALNO_MAKETING = "6SDK-EMY-6688-JCQTL";

	/** 自定义关键字(key值) */
	private static final String KEY_MAKETING = "756526";

	private static final String URL = "http://sh999.eucp.b2m.cn:8080/sdkproxy/sendsms.action";

	private static final String URL_MAKETING = "http://sdk4rptws.eucp.b2m.cn:8080/sdkproxy/sendsms.action";

	/**
	 * http请求参数集合
	 */
	private static Map<String, String> parmMap = null;

	/**
	 * http请求参数集合
	 */
	private static Map<String, String> parmMapMaketing = null;

	/**
	 * Client,单例
	 */
	private static Client client = null;

	@Autowired
	private SmsLogDao smsLogDao;
	@Autowired
	private AmUserClient amUserClient;
	@Autowired
	private AmConfigClient amConfigClient;
	private static Logger logger = LoggerFactory.getLogger(SmsHandle.class);

	private SmsHandle() {
	}

	/**
	 * 获取Client
	 *
	 * @return
	 * @throws Exception
	 */
	public Client getClient() throws Exception {
		if (client == null) {
			syncInit();
		}
		return client;
	}

	private synchronized void syncInit() {
		if (client == null) {
			try {
				client = new Client(SOFT_SERIALNO, KEY);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取参数集合
	 *
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getParmMap(String channelType) throws Exception {
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
				parmMap.put("cdkey", SOFT_SERIALNO);
				parmMap.put("password", KEY);
				parmMapMaketing = new HashMap<String, String>();
				parmMapMaketing.put("cdkey", SOFT_SERIALNO_MAKETING);
				parmMapMaketing.put("password", KEY_MAKETING);
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
	 * @throws Exception
	 */
	public Integer sendMessage(String mobile, String messageStr, String type, String sender, String channelType)
			throws Exception {
		String url = URL;
		Map<String, String> parmMap = getParmMap(channelType);
		parmMap.put("phone", mobile);
		if (CustomConstants.CHANNEL_TYPE_MARKETING.equals(channelType)) {
			parmMap.put("message", SmsHandle.TITLE + messageStr + SmsHandle.SUFFIX);
			url = URL_MAKETING;
		} else {
			parmMap.put("message", SmsHandle.TITLE + messageStr);
		}
		XStream xStream = new XStream();
		xStream.alias("response", SmsResponse.class);
		int status = ((SmsResponse) xStream.fromXML(HttpDeal.post(url, parmMap).trim())).getError();

		SmsLog smsLog = new SmsLog();
		smsLog.setType(type);
		smsLog.setContent(messageStr);// 短信内容
		smsLog.setPosttime(GetDate.getNowTime10());
		smsLog.setMobile(mobile);
		if (StringUtils.isEmpty(sender)) {
			smsLog.setSender(SmsHandle.TITLE);
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
				SmsNoticeConfigVO smsNoticeConfig = amConfigClient.findSmsNoticeByCode(tplCode);
				String mobile = smsNoticeConfig.getValue();
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
	public Integer sendMessages(String tplCode, Map<String, String> replaceStrs, String sender,
			String channelType) {
		int status = -1;
		try {
			// 获取模板信息
			try {
				SmsNoticeConfigVO smsNoticeConfig = amConfigClient.findSmsNoticeByCode(tplCode);
				String mobile = smsNoticeConfig.getValue();
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
	public Integer sendMessages(Integer userId, String tplCode, Map<String, String> replaceStrs,
			String channelType) {
		int status = -1;
		try {
			if (Validator.isNull(userId)) {
				throw new Exception("用户ID不可为空");
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
				throw new Exception("用户电话为空");
			}
			if (!Validator.isPhoneNumber(mobile)) {
				throw new Exception("用户电话号码格式不正确");
			}

			// 为保护客户隐私，只显示客户姓氏，不显示客户全名。 胡宝志20160115
			replaceStrs.put("val_name", user.getTruename().substring(0, 1));
			replaceStrs.put("val_sex", user.getSex() == 1 ? "先生" : "女士");

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
	public Integer sendMessages(String mobile, String tplCode, Map<String, String> replaceStrs,
			String channelType) {
		int status = -1;
		try {
			SmsTemplateVO smsTemplate = amConfigClient.findSmsTemplateByCode(tplCode);
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
	private class SmsResponse implements Serializable {

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
