package com.hyjf.am.message.processer.impl;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.Validator;

import cn.emay.sdk.client.api.Client;

public class SmsUtil {
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

	private SmsUtil() {
	}

	/**
	 * 获取Client
	 *
	 * @return
	 * @throws Exception
	 */
	public static Client getClient() throws Exception {
		if (client == null) {
			syncInit();
		}
		return client;
	}

	private static synchronized void syncInit() {
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
	public static Map<String, String> getParmMap(String channelType) throws Exception {
		if (parmMap == null || parmMapMaketing == null) {
			syncInitParmMap();
		}
		if (CustomConstants.CHANNEL_TYPE_NORMAL.equals(channelType)) {
			return parmMap;
		} else {
			return parmMapMaketing;
		}
	}

	private static synchronized void syncInitParmMap() {
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
	protected static Integer sendMessage(String mobile, String messageStr, String type, String sender,
			String channelType) throws Exception {
		// String url = URL;
		// Map<String, String> parmMap = getParmMap(channelType);
		// parmMap.put("phone", mobile);
		// if (CustomConstants.CHANNEL_TYPE_MARKETING.equals(channelType)) {
		// parmMap.put("message", SmsUtil.TITLE + messageStr + SmsUtil.SUFFIX);
		// url = URL_MAKETING;
		// } else {
		// parmMap.put("message", SmsUtil.TITLE + messageStr);
		// }
		// XStream xStream = new XStream();
		// xStream.alias("response", SmsResponse.class);
		// int status = ((SmsResponse) xStream.fromXML(HttpDeal.post(url,
		// parmMap).trim())).getError();
		//
		// SmsLogWithBLOBs smsLog = new SmsLogWithBLOBs();
		// smsLog.setType(type);
		// smsLog.setContent(messageStr);// 短信内容
		// smsLog.setPosttime(GetDate.getNowTime10());
		// smsLog.setMobile(mobile);
		// if (StringUtils.isEmpty(sender)) {
		// smsLog.setSender(SmsUtil.TITLE);
		// } else {
		// smsLog.setSender(sender);
		// }
		// smsLog.setStatus(status);
		// // 入库
		// addMessage(smsLog);
		// return status;
		return 0;
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
	protected static Integer sendMessages(String tplCode, Map<String, String> replaceStrs, String channelType) {
		int status = -1;
		// try {
		// // 获取模板信息
		// try {
		// SmsNoticeConfigWithBLOBs smsNoticeConfig = getNoticeConfig(tplCode);
		// String mobile = smsNoticeConfig.getValue();
		// String messageStr = smsNoticeConfig.getContent();
		// if (Validator.isNotNull(messageStr)) {
		// if (replaceStrs != null && replaceStrs.size() > 0) {
		// for (String s : replaceStrs.keySet()) {
		// messageStr = StringUtils.replace(messageStr, "[" + s + "]",
		// replaceStrs.get(s));
		// }
		// }
		// }
		//
		// // 发送短信
		// status = sendMessage(mobile, messageStr, smsNoticeConfig.getTitle(),
		// null, channelType);
		// } catch (Exception e) {
		// LogUtil.errorLog(SmsUtil.class.toString(), "sendMessages", "短信发送失败",
		// e);
		// }
		// } catch (Exception e) {
		// LogUtil.errorLog(SmsUtil.class.toString(), "sendMessages", "短信发送失败",
		// e);
		// }
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
	protected static Integer sendMessages(String tplCode, Map<String, String> replaceStrs, String sender,
			String channelType) {
		// int status = -1;
		// try {
		// // 获取模板信息
		// try {
		// SmsNoticeConfigWithBLOBs smsNoticeConfig = getNoticeConfig(tplCode);
		// String mobile = smsNoticeConfig.getValue();
		// String messageStr = smsNoticeConfig.getContent();
		// if (Validator.isNotNull(messageStr)) {
		// if (replaceStrs != null && replaceStrs.size() > 0) {
		// for (String s : replaceStrs.keySet()) {
		// messageStr = StringUtils.replace(messageStr, "[" + s + "]",
		// replaceStrs.get(s));
		// }
		// }
		// }
		//
		// // 发送短信
		// status = sendMessage(mobile, messageStr, smsNoticeConfig.getTitle(),
		// sender, channelType);
		// } catch (Exception e) {
		// LogUtil.errorLog(SmsUtil.class.toString(), "sendMessages", "短信发送失败",
		// e);
		// }
		// } catch (Exception e) {
		// LogUtil.errorLog(SmsUtil.class.toString(), "sendMessages", "短信发送失败",
		// e);
		// }
		// return status;
		return 0;
	}

	/**
	 * 根据用户ID和模版号给某用户发短信
	 *
	 * @param mobil
	 *            电话号码,多个电话号码用逗号分隔
	 * @param tplCode
	 *            模板Code
	 * @param replaceStrs
	 *            需要替换的字符串Map<需替换的字符串,替换值> 例:[value_name]:陈真
	 * @return
	 * @throws Exception
	 */
	protected static Integer sendMessages(Integer userId, String tplCode, Map<String, String> replaceStrs,
			String channelType) {
		// int status = -1;
		// try {
		// if (Validator.isNull(userId)) {
		// throw new Exception("用户ID不可为空");
		// }
		//
		// UsersMapper usersMapper =
		// SpringContextHolder.getBean(UsersMapper.class);
		// UsersInfoMapper usersInfoMapper =
		// SpringContextHolder.getBean(UsersInfoMapper.class);
		//
		// // 取得用户信息
		// UsersExample usersExample = new UsersExample();
		// usersExample.createCriteria().andUserIdEqualTo(userId);
		// Users user = usersMapper.selectByExample(usersExample).get(0);
		// if (tplCode != null &&
		// tplCode.equals(CustomConstants.PARAM_TPL_CHONGZHI_SUCCESS)) {
		// if (user.getRechargeSms() != null && user.getRechargeSms() != 0) {
		// return 0;
		// }
		// }
		// if (tplCode != null &&
		// tplCode.equals(CustomConstants.PARAM_TPL_TIXIAN_SUCCESS)) {
		// if (user.getWithdrawSms() != null && user.getWithdrawSms() != 0) {
		// return 0;
		// }
		// }
		// if (tplCode != null &&
		// tplCode.equals(CustomConstants.PARAM_TPL_TOUZI_SUCCESS)) {
		// if (user.getInvestSms() != null && user.getInvestSms() != 0) {
		// return 0;
		// }
		// }
		// if (tplCode != null &&
		// tplCode.equals(CustomConstants.PARAM_TPL_SHOUDAOHUANKUAN)) {
		// if (user.getRecieveSms() != null && user.getRecieveSms() != 0) {
		// return 0;
		// }
		// }
		// String mobile = user.getMobile();
		// if (StringUtils.isEmpty(mobile)) {
		// throw new Exception("用户电话为空");
		// }
		// if (!Validator.isPhoneNumber(mobile)) {
		// throw new Exception("用户电话号码格式不正确");
		// }
		//
		// // 取得用户详细信息
		// UsersInfoExample userInfoExample = new UsersInfoExample();
		// userInfoExample.createCriteria().andUserIdEqualTo(userId);
		// UsersInfo userInfo =
		// usersInfoMapper.selectByExample(userInfoExample).get(0);
		//
		// // replaceStrs.put("val_name", userInfo.getTruename());
		// // 为保护客户隐私，只显示客户姓氏，不显示客户全名。 胡宝志20160115
		// replaceStrs.put("val_name", userInfo.getTruename().substring(0, 1));
		// replaceStrs.put("val_sex", userInfo.getSex() == 1 ? "先生" : "女士");
		//
		// status = sendMessages(mobile, tplCode, replaceStrs, channelType);
		// } catch (Exception e) {
		// LogUtil.errorLog(SmsUtil.class.toString(), "sendMessages", "短信发送失败",
		// e);
		// }
		// return status;
		return 0;
	}

	/**
	 * 根据电话号码和模版号给某电话发短信
	 *
	 * @param mobil
	 *            电话号码,多个电话号码用逗号分隔
	 * @param tpl_code
	 *            tpl_code
	 * @param replaceStrs
	 *            需要替换的字符串Map<需替换的字符串,替换值> 例:[value_name]:陈真
	 * @return
	 * @throws Exception
	 */
	protected static Integer sendMessages(String mobile, String tplCode, Map<String, String> replaceStrs,
			String channelType) {
		int status = -1;
//		try {
//			SmsTemplate smsTemplate = getSmsTemplate(tplCode);
//			String messageStr = smsTemplate.getTplContent();
//			if (Validator.isNotNull(messageStr)) {
//				if (replaceStrs != null && replaceStrs.size() > 0) {
//					for (String s : replaceStrs.keySet()) {
//						messageStr = StringUtils.replace(messageStr, "[" + s + "]", replaceStrs.get(s));
//					}
//				}
//			}
//			// 发送短信
//			status = sendMessage(mobile, messageStr, smsTemplate.getTplName(), null, channelType);
//		} catch (Exception e) {
//			LogUtil.errorLog(SmsUtil.class.toString(), "sendMessages", "短信发送失败", e);
//		}
		return status;
	}

	/**
	 * 添加短信记录表
	 *
	 * @param content
	 *            短信内容
	 * @param mobile
	 *            手机号码
	 * @param remark
	 *            发送备注，如 注册填写“注册”
	 * @param status
	 *            发送状态，第三方返回状态
	 * @return
	 */

	// private static boolean addMessage(SmsLogWithBLOBs smsLog) {
	// SmsLogMapper smsLogMapper =
	// SpringContextHolder.getBean(SmsLogMapper.class);
	// smsLogMapper.insertSelective(smsLog);
	// return false;
	// }

	/**
	 * 获取SmsTemplate模版
	 * 
	 * @param tplCode
	 * @return
	 * @throws Exception
	 */
	// public static SmsTemplate getSmsTemplate(String tplCode) throws Exception
	// {
	// SmsTemplateMapper smsTemplateMapper =
	// SpringContextHolder.getBean(SmsTemplateMapper.class);
	// if (Validator.isNull(tplCode)) {
	// throw new Exception("模板标示不可为空");
	// }
	// SmsTemplateExample example = new SmsTemplateExample();
	// example.createCriteria().andTplCodeEqualTo(tplCode.toString()).andStatusEqualTo(1);
	// List<SmsTemplate> smsTemplateList =
	// smsTemplateMapper.selectByExampleWithBLOBs(example);
	// if (smsTemplateList == null || smsTemplateList.size() == 0) {
	// throw new Exception("根据模板标示没有查到对应模版,或者模版已关闭,模版代码:" + tplCode);
	// }
	// SmsTemplate smsTemplate = smsTemplateList.get(0);
	// return smsTemplate;
	// }

	/**
	 * 获取SmsNoticeConfig模版
	 * 
	 * @param tplCode
	 * @return
	 * @throws Exception
	 */
	// public static SmsNoticeConfigWithBLOBs getNoticeConfig(String tplCode)
	// throws Exception {
	// SmsNoticeConfigMapper smsNoticeConfigMapper =
	// SpringContextHolder.getBean(SmsNoticeConfigMapper.class);
	// if (Validator.isNull(tplCode)) {
	// throw new Exception("模板标示不可为空");
	// }
	// SmsNoticeConfigExample example = new SmsNoticeConfigExample();
	// example.createCriteria().andNameEqualTo(tplCode.toString()).andStatusEqualTo(1);
	// List<SmsNoticeConfigWithBLOBs> smsTemplateList =
	// smsNoticeConfigMapper.selectByExampleWithBLOBs(example);
	// if (smsTemplateList == null || smsTemplateList.size() == 0) {
	// throw new Exception("根据模板标示没有查到对应模版,或者模版已关闭,模版代码:" + tplCode);
	// }
	// SmsNoticeConfigWithBLOBs smsTemplate = smsTemplateList.get(0);
	// return smsTemplate;
	// }
}
