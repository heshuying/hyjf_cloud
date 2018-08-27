package com.hyjf.cs.message.handle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.cs.message.bean.mc.MessagePush;
import com.hyjf.cs.message.jpush.*;
import com.hyjf.cs.message.mongo.mc.MessagePushMsgDao;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hyjf.cs.message.bean.mc.MessagePushMsgHistory;
import com.hyjf.cs.message.client.AmConfigClient;
import com.hyjf.cs.message.client.AmUserClient;
import com.hyjf.cs.message.mongo.mc.MessagePushMsgHistoryDao;
import com.hyjf.am.vo.config.MessagePushTemplateVO;
import com.hyjf.am.vo.user.UserAliasVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.http.HtmlUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import org.springframework.stereotype.Component;

@Component
public class MsgPushHandle {
	private final static Logger logger = LoggerFactory.getLogger(MsgPushHandle.class);

	@Autowired
	private AmUserClient amUserClient;

	@Autowired
	private AmConfigClient amConfigClient;

	@Autowired
	private MessagePushMsgHistoryDao messagePushMsgHistoryDao;

	@Autowired
	private MessagePushMsgDao messagePushMsgDao;

	/**
	 * 
	 * 手动推送消息发送
	 *
	 * @param msgId
	 *            messagePushMsg 主键id
	 * @return 返回结果,0表示发送成功 -1标识失败
	 * @throws Exception
	 */
	public Integer sendMessages(Integer msgId) {

		MessagePush message = messagePushMsgDao.findById(msgId);

		List<MessagePushMsgHistory> histories = addMessageHistoryRecord(message);
		for (MessagePushMsgHistory history : histories) {
			// 发送
			try {
				send(history);
			} catch (Exception e) {
				logger.error("发送失败...", e);
				return -1;
			}
		}
		return 0;
	}

	/**
	 * 给指定用户usersId的用户推送消息
	 * 
	 * @param tplCode
	 * @param replaceStrs
	 * @param usersId
	 * @return
	 */
	public Integer sendMessages(String tplCode, Map<String, String> replaceStrs, Integer usersId) {
		if (usersId == null || usersId == 0) {
			return -1;
		}
		UserVO userVO = amUserClient.findUserById(usersId);
		if (userVO == null) {
			return -1;
		}

		UserInfoVO userInfoVO = amUserClient.findUsersInfoById(userVO.getUserId());
		// 为保护客户隐私，只显示客户姓氏，不显示客户全名。 胡宝志20160115
		replaceStrs.put("val_name", userInfoVO.getTruename().substring(0, 1));
		replaceStrs.put("val_sex", userInfoVO.getSex() == 1 ? "先生" : "女士");

		return sendMessages(tplCode, replaceStrs, userVO.getMobile());
	}

	/**
	 * 给指定电话号码的用户推送消息
	 *
	 * @param tplCode
	 *            模板Code
	 * @param replaceStrs
	 *            需要替换的字符串Map<需替换的字符串,替换值> 例:[value_title]:HDD151210404
	 * @param mobile
	 *            用户电话号码
	 * 
	 * @return 0 成功 -1电话号码无效 -2此用户无移动终端 -3表示没有查到模版 -4表示其他错误
	 * @throws Exception
	 */
	public Integer sendMessages(String tplCode, Map<String, String> replaceStrs, String mobile) {
		// 获取模板信息
		if (StringUtils.isEmpty(mobile)) {
			return -1;
		}
		// 获取模板
		MessagePushTemplateVO messagePushTemplate = null;
		try {
			messagePushTemplate = amConfigClient.findMessagePushTemplateByCode(tplCode);
		} catch (Exception e) {
			logger.error("未找到对应的模板:" + tplCode, e);
			return -3;
		}
		try {
			String messageStr = messagePushTemplate.getTemplateContent();
			if (Validator.isNotNull(messageStr)) {
				if (replaceStrs != null && replaceStrs.size() > 0) {
					for (String s : replaceStrs.keySet()) {
						messageStr = StringUtils.replace(messageStr, "[" + s + "]", replaceStrs.get(s));
					}
				}
			}
			// 推送消息
			MessagePushMsgHistory msgHistory = addMessageHistoryRecord(mobile, messageStr, messagePushTemplate);

			if (msgHistory != null) {
				send(msgHistory);
				return 0;
			}
		} catch (Exception e) {
			logger.error("消息推送失败...", e);
			return -4;
		}
		return 0;
	}

	//
	/**
	 * 添加发送记录（模板消息）
	 *
	 * @param mobile
	 *            手机号
	 * @param message
	 *            发送内容
	 * @param messagePushTemplate
	 *            模板
	 */
	public MessagePushMsgHistory addMessageHistoryRecord(String mobile, String message,
			MessagePushTemplateVO messagePushTemplate) {

		UserAliasVO userAliasVO = amUserClient.findAliasByMobile(mobile);
		// 存储历史记录
		if (userAliasVO != null) {
			int msgId = 0;
			MessagePushMsgHistory history = new MessagePushMsgHistory();
			history.setCreateTime(GetDate.getNowTime10() + "");
			history.setCreateUserId(null);
			history.setCreateUserName("系统自动触发");
			history.setLastupdateTime(GetDate.getNowTime10() + "");
			history.setLastupdateUserId(null);
			history.setLastupdateUserName("系统自动触发");
			history.setMsgAction(messagePushTemplate.getTemplateAction());
			history.setMsgActionUrl(messagePushTemplate.getTemplateActionUrl());
			history.setMsgCode(messagePushTemplate.getTemplateCode()); // 消息编码为模板编码
			history.setMsgTitle(messagePushTemplate.getTemplateTitle());
			history.setMsgContent(message);
			history.setMsgDestination(mobile);
			history.setMsgDestinationType(CustomConstants.MSG_PUSH_DESTINATION_TYPE_1);
			history.setMsgFirstreadPlat(null);
			history.setMsgImageUrl(messagePushTemplate.getTemplateImageUrl());
			history.setMsgJpushId(null);
			history.setMsgJpushProId(null);
			history.setMsgReadCountAndroid(0);
			history.setMsgReadCountIos(0);
			history.setMsgRemark("");
			history.setMsgSendStatus(CustomConstants.MSG_PUSH_SEND_STATUS_0);
			history.setMsgTerminal(messagePushTemplate.getTemplateTerminal());
			history.setMsgUserId(null);
			history.setSendTime(null);
			history.setTagCode(messagePushTemplate.getTagCode());
			history.setTagId(Integer.valueOf(messagePushTemplate.getTagId()));
			if (userAliasVO.getClient().equals(CustomConstants.CLIENT_ANDROID)) {
				history.setMsgDestinationCountAndroid(1);// 安卓目标推送数
				history.setMsgDestinationCountIos(0);// IOS目标推送数
			} else if (userAliasVO.getClient().equals(CustomConstants.CLIENT_IOS)) {
				history.setMsgDestinationCountAndroid(0);// 安卓目标推送数
				history.setMsgDestinationCountIos(1);// IOS目标推送数
			}
			messagePushMsgHistoryDao.save(history);

			return history;
		} else {
			logger.error("推送失败，未找到目标用户.....");
			throw new RuntimeException("推送失败，未找到目标用户.....");
		}
	}

	/**
	 * 添加发送记录（手动添加消息）
	 *
	 * @param message
	 *            消息
	 */
	public List<MessagePushMsgHistory> addMessageHistoryRecord(MessagePush message) {
		List<MessagePushMsgHistory> histories = new ArrayList<MessagePushMsgHistory>();
		if (message.getMsgDestinationType().intValue() == CustomConstants.MSG_PUSH_DESTINATION_TYPE_0) {
			// 发给所有人
			MessagePushMsgHistory history = new MessagePushMsgHistory();
			history.setCreateTime(message.getCreateTime() + "");
			history.setCreateUserId(message.getCreateUserId());
			history.setCreateUserName(message.getCreateUserName());
			history.setLastupdateTime(GetDate.getNowTime10() + "");
			history.setLastupdateUserId(message.getCreateUserId());
			history.setLastupdateUserName(message.getCreateUserName());
			history.setMsgAction(message.getMsgAction());
			history.setMsgActionUrl(message.getMsgActionUrl());
			history.setMsgCode(message.getMsgCode());
			history.setMsgContent(message.getMsgContent());
			history.setMsgDestination(message.getMsgDestination());
			history.setMsgDestinationType(message.getMsgDestinationType());
			history.setMsgFirstreadPlat(null);
			history.setMsgImageUrl(message.getMsgImageUrl());
			history.setMsgJpushId(null);
			history.setMsgJpushProId(null);
			history.setMsgReadCountAndroid(0);
			history.setMsgReadCountIos(0);
			history.setMsgRemark("");
			history.setMsgSendStatus(CustomConstants.MSG_PUSH_SEND_STATUS_0);
			history.setMsgTerminal(message.getMsgTerminal());
			history.setMsgTitle(message.getMsgTitle());
			history.setMsgUserId(null);
			history.setSendTime(null);
			history.setTagCode(message.getTagCode());
			history.setTagId(Integer.valueOf(message.getTagId()));
			history.setMsgDestinationCountAndroid(amUserClient.countAliasByClient(CustomConstants.CLIENT_ANDROID));// 安卓目标推送数
			history.setMsgDestinationCountIos(amUserClient.countAliasByClient(CustomConstants.CLIENT_IOS));// IOS目标推送数
			// 插入数据库
			messagePushMsgHistoryDao.insert(history);
			histories.add(history);
		}
		if (message.getMsgDestinationType().intValue() == CustomConstants.MSG_PUSH_DESTINATION_TYPE_1) {
			// 发给固定人群
			String[] mobiles = message.getMsgDestination().split(",");
			// 获取设备唯一编码
			if (mobiles != null && mobiles.length != 0) {
				List<UserAliasVO> msgPushCommonList = amUserClient.findAliasesByMobiles(Arrays.asList(mobiles));
				// 存储历史记录
				if (msgPushCommonList != null && msgPushCommonList.size() != 0) {
					for (int i = 0; i < msgPushCommonList.size(); i++) {
						MessagePushMsgHistory history = new MessagePushMsgHistory();
						history.setCreateTime(message.getCreateTime() + "");
						history.setCreateUserId(message.getCreateUserId());
						history.setCreateUserName(message.getCreateUserName());
						history.setLastupdateTime(GetDate.getNowTime10() + "");
						history.setLastupdateUserId(message.getCreateUserId());
						history.setLastupdateUserName(message.getCreateUserName());
						history.setMsgAction(message.getMsgAction());
						history.setMsgActionUrl(message.getMsgActionUrl());
						history.setMsgCode(message.getMsgCode());
						history.setMsgContent(message.getMsgContent());
						history.setMsgDestination(msgPushCommonList.get(i).getMobile());
						history.setMsgDestinationType(message.getMsgDestinationType());
						history.setMsgFirstreadPlat(null);
						history.setMsgImageUrl(message.getMsgImageUrl());
						history.setMsgJpushId(null);
						history.setMsgJpushProId(null);
						history.setMsgReadCountAndroid(0);
						history.setMsgReadCountIos(0);
						history.setMsgRemark("");
						history.setMsgSendStatus(CustomConstants.MSG_PUSH_SEND_STATUS_0);
						history.setMsgTerminal(message.getMsgTerminal());
						history.setMsgTitle(message.getMsgTitle());
						history.setMsgUserId(null);
						history.setSendTime(null);
						history.setTagCode(message.getTagCode());
						history.setTagId(Integer.valueOf(message.getTagId()));
						if (msgPushCommonList.get(i).getClient().equals(CustomConstants.CLIENT_ANDROID)) {
							history.setMsgDestinationCountAndroid(1);// 安卓目标推送数
							history.setMsgDestinationCountIos(0);// IOS目标推送数
						} else if (msgPushCommonList.get(i).getClient().equals(CustomConstants.CLIENT_IOS)) {
							history.setMsgDestinationCountAndroid(0);// 安卓目标推送数
							history.setMsgDestinationCountIos(1);// IOS目标推送数
						}
						// 插入数据库
						messagePushMsgHistoryDao.insert(history);
						histories.add(history);
					}
				}
			}
		}
		message.setMsgSendStatus(CustomConstants.MSG_PUSH_MSG_STATUS_1);
		message.setSendTime(GetDate.getNowTime10());
		messagePushMsgDao.save(message);
		return histories;
	}

	/**
	 * 极光推送及更新发送状态
	 *
	 * @param msg
	 */
	public void send(MessagePushMsgHistory msg) throws Exception {
		logger.info("开始推送: msg_id is :{}, msg_content is:{}", msg.getId(), msg.getMsgContent());
		String msgId = ""; // 极光返回id
		String msgProId = "";// 新极光返回id
		String msgZNBID = "";// 周年版返回id
		String msgYXBID = "";// 悦享版返回id
		String msgZZBID = "";// 至尊版返回id
		String msgZYBID = "";// 专业版返回id
		String msgTESTID = "";// 测试版返回id
		String errorMsg = "";// 错误消息
		Integer userId = null; // 用户id
		String pcode = "";// 包区分 39新极光 79老极光
		String client = "";// 客户端
		// 发送实体
		PushPayload payload = null;
		if (msg == null) {
			return;
		}
		// 判断客户端
		String clientStr = msg.getMsgTerminal();
		// 判断是否发送所有人 0发送所有人 1个人
		if (msg.getMsgDestinationType() == CustomConstants.MSG_PUSH_DESTINATION_TYPE_0) {

			if (CustomConstants.CLIENT_ANDROID.equals(clientStr)) {// 单独发送安卓客户端
				payload = JPush.buildPushObject_android_tag_alertWithTitle(
						HtmlUtil.getTextFromHtml(msg.getMsgContent()), msg.getId(), msg.getMsgAction(),
						msg.getMsgActionUrl());
			} else if (CustomConstants.CLIENT_IOS.equals(clientStr)) {// 单发ios客户端
				payload = JPush.buildPushObject_ios_tag_alert(HtmlUtil.getTextFromHtml(msg.getMsgContent()),
						msg.getId(), msg.getMsgAction(), msg.getMsgActionUrl());
			} else {// 所有客户端
				payload = JPush.buildPushObject_android_and_ios(HtmlUtil.getTextFromHtml(msg.getMsgContent()),
						msg.getId(), msg.getMsgAction(), msg.getMsgActionUrl());
			}
			// 个人用户推送
		} else if (msg.getMsgDestinationType() == CustomConstants.MSG_PUSH_DESTINATION_TYPE_1) {
			UserAliasVO commonBean = amUserClient.findAliasByMobile(msg.getMsgDestination());
			if (commonBean != null) {
				userId = commonBean.getUserId();
				pcode = commonBean.getPackageCode();
				client = commonBean.getClient();
				if (StringUtils.isEmpty(commonBean.getAlias())) {
					errorMsg = "该用户设备不存在";
				} else {
					payload = JPush.buildPushObject_all_alias_alert(HtmlUtil.getTextFromHtml(msg.getMsgContent()),
							msg.getId(), msg.getMsgAction(), msg.getMsgActionUrl(), commonBean.getAlias());
				}
			} else {
				errorMsg = "该用户设备不存在";
			}
		}
		// 调用结果
		PushResult result = null;
		try {
			/**
			 * 1、发送所有人，判断是否为ios客户端，如果是两个极光发送 2、发送个人，判断该用户是哪个客户端和是否为 39 或 79 3、39为新极光推送
			 */
			if (payload != null) {
				logger.info("payload: " + payload);
				if (msg.getMsgDestinationType() == CustomConstants.MSG_PUSH_DESTINATION_TYPE_0) {
					if (clientStr.contains(CustomConstants.CLIENT_IOS)) {
						if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_39)) {
							result = JPushPro.getClientInstance().sendPush(payload);
							msgProId = String.valueOf(result.msg_id);
						} else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_YXB)) {
							result = JPushYXB.getClientInstance().sendPush(payload);
							msgYXBID = String.valueOf(result.msg_id);
						} else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZNB)) {
							result = JPushZNB.getClientInstance().sendPush(payload);
							msgZNBID = String.valueOf(result.msg_id);
						} else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZYB)) {
							result = JPushZYB.getClientInstance().sendPush(payload);
							msgZYBID = String.valueOf(result.msg_id);
						} else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZZB)) {
							result = JPushZZB.getClientInstance().sendPush(payload);
							msgZZBID = String.valueOf(result.msg_id);
						} else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_TEST)) {
							result = JPushTEST.getClientInstance().sendPush(payload);
							msgTESTID = String.valueOf(result.msg_id);
						}
					}
					result = JPush.getClientInstance().sendPush(payload);
					msgId = String.valueOf(result.msg_id);
				} else if (msg.getMsgDestinationType() == CustomConstants.MSG_PUSH_DESTINATION_TYPE_1) {
					if (CustomConstants.CLIENT_ANDROID.equals(client)) {
						result = JPush.getClientInstance().sendPush(payload);
						msgId = String.valueOf(result.msg_id);
					} else if (CustomConstants.CLIENT_IOS.equals(client)) {
						if (StringUtils.isNotEmpty(pcode)) {
							logger.info("pcode: " + pcode);
							if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_39)) {

								result = JPushPro.getClientInstance().sendPush(payload);
								msgProId = String.valueOf(result.msg_id);
							} else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_YXB)) {
								result = JPushYXB.getClientInstance().sendPush(payload);
								msgYXBID = String.valueOf(result.msg_id);
							} else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZNB)) {
								result = JPushZNB.getClientInstance().sendPush(payload);
								msgZNBID = String.valueOf(result.msg_id);
							} else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZYB)) {
								result = JPushZYB.getClientInstance().sendPush(payload);
								msgZYBID = String.valueOf(result.msg_id);
							} else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZZB)) {
								result = JPushZZB.getClientInstance().sendPush(payload);
								msgZZBID = String.valueOf(result.msg_id);
							} else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_TEST)) {
								result = JPushTEST.getClientInstance().sendPush(payload);
								msgTESTID = String.valueOf(result.msg_id);
							} else {
								result = JPush.getClientInstance().sendPush(payload);
								msgId = String.valueOf(result.msg_id);
							}
						}
					}
				}
			} else {
				errorMsg = "发送失败，用户不存在";
			}
		} catch (APIConnectionException e) {
			errorMsg = "调用极光接口异常,连接超时";
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
			switch (e.getErrorCode()) {
			case 1000:
				errorMsg = "极光系统内部错误";
				break;
			case 1001:
				errorMsg = "不支持 Get方法";
				break;
			case 1002:
				errorMsg = "缺少了必须的参数";
				break;
			case 1003:
				errorMsg = "参数值不合法";
				break;
			case 1004:
				errorMsg = "验证失败";
				break;
			case 1005:
				errorMsg = "消息体太大";
				break;
			case 1008:
				errorMsg = "app_key参数非法";
				break;
			case 1011:
				errorMsg = "没有满足条件的推送目标，用户不存在";
				break;
			case 1020:
				errorMsg = "只支持 HTTPS请求";
				break;
			case 1030:
				errorMsg = "内部服务超时";
				break;
			default:
				errorMsg = "调用极光发生未知错误";
				break;
			}
		}
		// 成功
		if (StringUtils.isNotEmpty(msgId) || StringUtils.isNotEmpty(msgProId) || StringUtils.isNotEmpty(msgZNBID)
				|| StringUtils.isNotEmpty(msgYXBID) || StringUtils.isNotEmpty(msgZYBID)
				|| StringUtils.isNotEmpty(msgZZBID) || StringUtils.isNotEmpty(msgTESTID)) {
			msg.setSendTime(GetDate.getNowTime10() + "");
			msg.setMsgSendStatus(CustomConstants.MSG_PUSH_SEND_STATUS_1);
			msg.setMsgJpushId(msgId);
			msg.setMsgJpushProId(msgProId);
			msg.setMsgJpushYxbId(msgYXBID);
			msg.setMsgJpushZnbId(msgZNBID);
			msg.setMsgJpushZybId(msgZYBID);
			msg.setMsgJpushZzbId(msgZZBID);
			msg.setMsgJpushTestId(msgTESTID);
			logger.info("发送消息成功：msgId: " + msgId);
			logger.info("发送消息成功：msgProId: " + msgProId);
			logger.info("发送消息成功：msgYXBID: " + msgYXBID);
			logger.info("发送消息成功：msgZNBID: " + msgZNBID);
			logger.info("发送消息成功：msgZYBID: " + msgZYBID);
			logger.info("发送消息成功：msgZZBID: " + msgZZBID);
			logger.info("发送消息成功：msgTESTID: " + msgTESTID);
		} else {
			msg.setSendTime(GetDate.getNowTime10() + "");
			msg.setMsgSendStatus(CustomConstants.MSG_PUSH_SEND_STATUS_2);
			msg.setMsgRemark(errorMsg);
			logger.info("发送消息失败：" + errorMsg);
		}
		// 更新userid
		if (userId != null) {
			msg.setMsgUserId(userId);
		}
		messagePushMsgHistoryDao.save(msg);
	}

}
