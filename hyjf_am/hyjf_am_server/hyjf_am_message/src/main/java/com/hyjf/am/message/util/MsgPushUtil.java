package com.hyjf.am.message.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsgPushUtil {
	private final static Logger logger = LoggerFactory.getLogger(MsgPushUtil.class);
	/**
	 * 通过手机获取设备唯一编码
	 */
	//private static MsgPushCommonCustomizeMapper msgPushCommonCustomizeMapper = SpringContextHolder.getBean(MsgPushCommonCustomizeMapper.class);
	/**
	 * 消息 Mapper
	 */
	//private static MessagePushMsgMapper messagePushMsgMapper = SpringContextHolder.getBean(MessagePushMsgMapper.class);
	/**
	 * 发送历史Mapper
	 */
	//private static MessagePushMsgHistoryMapper messagePushMsgHistoryMapper = SpringContextHolder.getBean(MessagePushMsgHistoryMapper.class);

	/**
	 * 设备唯一编码 Mapper
	 */
	//private static MobileCodeMapper mobileCodeMapper = SpringContextHolder.getBean(MobileCodeMapper.class);

	/**
	 * 
	 * 手动推送消息发送
	 *
	 * @param msgId
	 *            messagePushMsg 主键id
	 * @return 返回结果,0表示发送成功 -1标识失败
	 * @throws Exception
	 */
	public static Integer sendMessages(Integer msgId) {

//		MessagePushMsg message = messagePushMsgMapper.selectByPrimaryKey(msgId);
//
//		List<Integer> msgIdList = addMessageHistoryRecord(message);
//		for (int i = 0; i < msgIdList.size(); i++) {
//			if (msgIdList.get(i) > 0) {
//				MessagePushMsgHistory msgHistory = messagePushMsgHistoryMapper.selectByPrimaryKey(msgIdList.get(i));
//				// 发送
//				try {
//					send(msgHistory);
//				} catch (Exception e) {
//					return -1;
//				}
//			}
//		}
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
	public static Integer sendMessages(String tplCode, Map<String, String> replaceStrs, Integer usersId) {
//		if (usersId == null || usersId == 0) {
//			return -1;
//		}
//		UsersExample example = new UsersExample();
//		example.createCriteria().andUserIdEqualTo(usersId);
//		UsersMapper usersMapper = SpringContextHolder.getBean(UsersMapper.class);
//		List<Users> list = usersMapper.selectByExample(example);
//		if (list == null || list.size() != 1 || StringUtils.isEmpty(list.get(0).getMobile())) {
//			return -1;
//		}
//		return sendMessages(tplCode, replaceStrs, list.get(0).getMobile());
		return 0;
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
	public static Integer sendMessages(String tplCode, Map<String, String> replaceStrs, String mobile) {
//		// 获取模板信息
//		if (StringUtils.isEmpty(mobile)) {
//			return -1;
//		}
//		// 检验用户是否有app
//
//		MsgPushCommonCustomize commonBean = msgPushCommonCustomizeMapper.getMobileCodeByNumber(mobile);
//		if (commonBean == null) {
//		    logger.info("");
//		    LogUtil.infoLog(MsgPushUtil.class.getName(), "未查询到用户app登陆信息："+mobile);
//			return -2;
//		}
//		if (StringUtils.isEmpty(commonBean.getMobileCode())) {
//		    LogUtil.infoLog(MsgPushUtil.class.getName(), "未查询到用户app登陆唯一手机编号："+mobile);
//			return -2;
//		}
//		// 获取模板
//		MessagePushTemplate messagePushTemplate = null;
//		try {
//			messagePushTemplate = getNoticeConfig(tplCode);
//		} catch (Exception e) {
//		    LogUtil.infoLog(MsgPushUtil.class.getName(), "未找到对应的模板："+tplCode);
//			return -3;
//		}
//		try {
//			String messageStr = messagePushTemplate.getTemplateContent();
//			if (Validator.isNotNull(messageStr)) {
//				if (replaceStrs != null && replaceStrs.size() > 0) {
//					for (String s : replaceStrs.keySet()) {
//						messageStr = StringUtils.replace(messageStr, "[" + s + "]", replaceStrs.get(s));
//					}
//				}
//			}
//			// 推送消息
//			int msgHisId = addMessageHistoryRecord(mobile, messageStr, messagePushTemplate);
//			if (msgHisId > 0) {
//				MessagePushMsgHistory msgHistory = messagePushMsgHistoryMapper.selectByPrimaryKey(msgHisId);
//				// 发送
//				send(msgHistory);
//			}
//			return 0;
//		} catch (Exception e) {
//			LogUtil.errorLog(MsgPushUtil.class.toString(), "sendMessages", "消息推送失败", e);
//			return -4;
//		}
		return 0;
	}

	/**
	 * 获取MessagePushTemplate模版
	 * 
	 * @param tplCode
	 * @return
	 * @throws Exception
	 */
//	public static MessagePushTemplate getNoticeConfig(String tplCode) throws Exception {
////		MessagePushTemplateMapper messagePushTemplateMapper = SpringContextHolder.getBean(MessagePushTemplateMapper.class);
////		if (Validator.isNull(tplCode)) {
////			throw new Exception("模板标示不可为空");
////		}
////		MessagePushTemplateExample example = new MessagePushTemplateExample();
////		example.createCriteria().andTemplateCodeEqualTo(tplCode).andStatusEqualTo(CustomConstants.MSG_PUSH_STATUS_1);
////		List<MessagePushTemplate> messagePushTemplateList = messagePushTemplateMapper.selectByExample(example);
////		if (messagePushTemplateList == null || messagePushTemplateList.size() == 0) {
////			throw new Exception("根据模板标示没有查到对应的消息推送模版,或者模版已关闭,模版代码:" + tplCode);
////		}
////		MessagePushTemplate smsTemplate = messagePushTemplateList.get(0);
////		return smsTemplate;
//	}

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
//	public static int addMessageHistoryRecord(String mobile, String message, MessagePushTemplate messagePushTemplate) {
//
//		MsgPushCommonCustomize msgPushCommon = msgPushCommonCustomizeMapper.getMobileCodeByNumber(mobile);
//		// 存储历史记录
//		if (msgPushCommon != null) {
//			int msgId = 0;
//			MessagePushMsgHistory history = new MessagePushMsgHistory();
//			history.setCreateTime(GetDate.getNowTime10());
//			history.setCreateUserId(null);
//			history.setCreateUserName("系统自动触发");
//			history.setLastupdateTime(GetDate.getNowTime10());
//			history.setLastupdateUserId(null);
//			history.setLastupdateUserName("系统自动触发");
//			history.setMsgAction(messagePushTemplate.getTemplateAction());
//			history.setMsgActionUrl(messagePushTemplate.getTemplateActionUrl());
//			history.setMsgCode(messagePushTemplate.getTemplateCode()); // 消息编码为模板编码
//			history.setMsgTitle(messagePushTemplate.getTemplateTitle());
//			history.setMsgContent(message);
//			history.setMsgDestination(mobile);
//			history.setMsgDestinationType(CustomConstants.MSG_PUSH_DESTINATION_TYPE_1);
//			history.setMsgFirstreadPlat(null);
//			history.setMsgImageUrl(messagePushTemplate.getTemplateImageUrl());
//			history.setMsgJpushId(null);
//			history.setMsgJpushProId(null);
//			history.setMsgReadCountAndroid(0);
//			history.setMsgReadCountIos(0);
//			history.setMsgRemark("");
//			history.setMsgSendStatus(CustomConstants.MSG_PUSH_SEND_STATUS_0);
//			history.setMsgTerminal(messagePushTemplate.getTemplateTerminal());
//			history.setMsgUserId(null);
//			history.setSendTime(null);
//			history.setTagCode(messagePushTemplate.getTagCode());
//			history.setTagId(messagePushTemplate.getTagId());
//			if (msgPushCommon.getClient().equals(CustomConstants.CLIENT_ANDROID)) {
//				history.setMsgDestinationCountAndroid(1);// 安卓目标推送数
//				history.setMsgDestinationCountIos(0);// IOS目标推送数
//			} else if (msgPushCommon.getClient().equals(CustomConstants.CLIENT_IOS)) {
//				history.setMsgDestinationCountAndroid(0);// 安卓目标推送数
//				history.setMsgDestinationCountIos(1);// IOS目标推送数
//			}
//			msgId = messagePushMsgHistoryMapper.insertSelective(history);
//			if (msgId > 0) {
//				return history.getId();
//			}
//			return -1;
//		} else {
//			return -2;
//		}
//	}
//
//	/**
//	 * 添加发送记录（手动添加消息）
//	 *
//	 * @param messagePushMsg
//	 *            消息
//	 */
//	public static List<Integer> addMessageHistoryRecord(MessagePushMsg message) {
//		List<Integer> msgIds = new ArrayList<Integer>();
//		if (message.getMsgDestinationType().intValue() == CustomConstants.MSG_PUSH_DESTINATION_TYPE_0) {
//			// 发给所有人
//			MessagePushMsgHistory history = new MessagePushMsgHistory();
//			history.setCreateTime(message.getCreateTime());
//			history.setCreateUserId(message.getCreateUserId());
//			history.setCreateUserName(message.getCreateUserName());
//			history.setLastupdateTime(GetDate.getNowTime10());
//			history.setLastupdateUserId(message.getCreateUserId());
//			history.setLastupdateUserName(message.getCreateUserName());
//			history.setMsgAction(message.getMsgAction());
//			history.setMsgActionUrl(message.getMsgActionUrl());
//			history.setMsgCode(message.getMsgCode());
//			history.setMsgContent(message.getMsgContent());
//			history.setMsgDestination(message.getMsgDestination());
//			history.setMsgDestinationType(message.getMsgDestinationType());
//			history.setMsgFirstreadPlat(null);
//			history.setMsgImageUrl(message.getMsgImageUrl());
//			history.setMsgJpushId(null);
//			history.setMsgJpushProId(null);
//			history.setMsgReadCountAndroid(0);
//			history.setMsgReadCountIos(0);
//			history.setMsgRemark("");
//			history.setMsgSendStatus(CustomConstants.MSG_PUSH_SEND_STATUS_0);
//			history.setMsgTerminal(message.getMsgTerminal());
//			history.setMsgTitle(message.getMsgTitle());
//			history.setMsgUserId(null);
//			history.setSendTime(null);
//			history.setTagCode(message.getTagCode());
//			history.setTagId(message.getTagId());
//			MobileCodeExample example2 = new MobileCodeExample();
//			example2.createCriteria().andClientEqualTo(CustomConstants.CLIENT_ANDROID);
//			int size2 = mobileCodeMapper.countByExample(example2);
//			MobileCodeExample example3 = new MobileCodeExample();
//			example3.createCriteria().andClientEqualTo(CustomConstants.CLIENT_IOS);
//			int size3 = mobileCodeMapper.countByExample(example3);
//			history.setMsgDestinationCountAndroid(size2);// 安卓目标推送数
//			history.setMsgDestinationCountIos(size3);// IOS目标推送数
//			// 插入数据库
//			int msgid = messagePushMsgHistoryMapper.insertSelective(history);
//			if (msgid > 0) {
//				msgIds.add(history.getId());
//			}
//		}
//		if (message.getMsgDestinationType().intValue() == CustomConstants.MSG_PUSH_DESTINATION_TYPE_1) {
//			// 发给固定人群
//			String[] mobiles = message.getMsgDestination().split(",");
//			// 获取设备唯一编码
//			if (mobiles != null && mobiles.length != 0) {
//				MsgPushCommonCustomize msgPushCommonCustomize = new MsgPushCommonCustomize();
//				msgPushCommonCustomize.setMobiles(mobiles);
//				List<MsgPushCommonCustomize> msgPushCommonList = msgPushCommonCustomizeMapper.getMobileCodeByMobiles(msgPushCommonCustomize);
//				// 存储历史记录
//				if (msgPushCommonList != null && msgPushCommonList.size() != 0) {
//					for (int i = 0; i < msgPushCommonList.size(); i++) {
//						MessagePushMsgHistory history = new MessagePushMsgHistory();
//						history.setCreateTime(message.getCreateTime());
//						history.setCreateUserId(message.getCreateUserId());
//						history.setCreateUserName(message.getCreateUserName());
//						history.setLastupdateTime(GetDate.getNowTime10());
//						history.setLastupdateUserId(message.getCreateUserId());
//						history.setLastupdateUserName(message.getCreateUserName());
//						history.setMsgAction(message.getMsgAction());
//						history.setMsgActionUrl(message.getMsgActionUrl());
//						history.setMsgCode(message.getMsgCode());
//						history.setMsgContent(message.getMsgContent());
//						history.setMsgDestination(msgPushCommonList.get(i).getMobile());
//						history.setMsgDestinationType(message.getMsgDestinationType());
//						history.setMsgFirstreadPlat(null);
//						history.setMsgImageUrl(message.getMsgImageUrl());
//						history.setMsgJpushId(null);
//						history.setMsgJpushProId(null);
//						history.setMsgReadCountAndroid(0);
//						history.setMsgReadCountIos(0);
//						history.setMsgRemark("");
//						history.setMsgSendStatus(CustomConstants.MSG_PUSH_SEND_STATUS_0);
//						history.setMsgTerminal(message.getMsgTerminal());
//						history.setMsgTitle(message.getMsgTitle());
//						history.setMsgUserId(null);
//						history.setSendTime(null);
//						history.setTagCode(message.getTagCode());
//						history.setTagId(message.getTagId());
//						if (msgPushCommonList.get(i).getClient().equals(CustomConstants.CLIENT_ANDROID)) {
//							history.setMsgDestinationCountAndroid(1);// 安卓目标推送数
//							history.setMsgDestinationCountIos(0);// IOS目标推送数
//						} else if (msgPushCommonList.get(i).getClient().equals(CustomConstants.CLIENT_IOS)) {
//							history.setMsgDestinationCountAndroid(0);// 安卓目标推送数
//							history.setMsgDestinationCountIos(1);// IOS目标推送数
//						}
//						int msgid = messagePushMsgHistoryMapper.insertSelective(history);
//						if (msgid > 0) {
//							msgIds.add(history.getId());
//						}
//					}
//				}
//			}
//		}
//		message.setMsgSendStatus(CustomConstants.MSG_PUSH_MSG_STATUS_1);
//		message.setSendTime(GetDate.getNowTime10());
//		messagePushMsgMapper.updateByPrimaryKeyWithBLOBs(message);
//		return msgIds;
//	}
//
//	/**
//	 * 极光推送及更新发送状态
//	 *
//	 * @param messagePushMsgHistory
//	 */
//	public static void send(MessagePushMsgHistory msg) throws Exception {
//		logger.info("开始推送: msg_id is :{}, msg_content is:{}", msg.getId(), msg.getMsgContent());
//		String msgId = ""; // 极光返回id
//		String msgProId = "";// 新极光返回id
//		String msgZNBID = "";// 周年版返回id
//		String msgYXBID = "";// 悦享版返回id
//		String msgZZBID = "";// 至尊版返回id
//		String msgZYBID = "";// 专业版返回id
//		String msgTESTID = "";// 测试版返回id
//		String errorMsg = "";// 错误消息
//		Integer userId = null; // 用户id
//		String pcode = "";// 包区分 39新极光 79老极光
//		String client = "";//客户端
//		// 发送实体
//		PushPayload payload = null;
//		if (msg == null) {
//			return;
//		}
//		// 判断客户端
//		String clientStr = msg.getMsgTerminal();
//		// 判断是否发送所有人 0发送所有人 1个人
//		if (msg.getMsgDestinationType() == CustomConstants.MSG_PUSH_DESTINATION_TYPE_0) {
//
//			if (CustomConstants.CLIENT_ANDROID.equals(clientStr)) {// 单独发送安卓客户端
//				payload = JPush.buildPushObject_android_tag_alertWithTitle(HtmlUtil.getTextFromHtml(msg.getMsgContent()), msg.getId(), msg.getMsgAction(), msg.getMsgActionUrl());
//			} else if (CustomConstants.CLIENT_IOS.equals(clientStr)) {// 单发ios客户端
//				payload = JPush.buildPushObject_ios_tag_alert(HtmlUtil.getTextFromHtml(msg.getMsgContent()), msg.getId(), msg.getMsgAction(), msg.getMsgActionUrl());
//			} else {// 所有客户端
//				payload = JPush.buildPushObject_android_and_ios(HtmlUtil.getTextFromHtml(msg.getMsgContent()), msg.getId(), msg.getMsgAction(), msg.getMsgActionUrl());
//			}
//			// 个人用户推送
//		} else if (msg.getMsgDestinationType() == CustomConstants.MSG_PUSH_DESTINATION_TYPE_1) {
//			MsgPushCommonCustomize commonBean = msgPushCommonCustomizeMapper.getMobileCodeByNumber(msg.getMsgDestination());
//			if (commonBean != null) {
//				userId = commonBean.getUserId();
//				pcode = commonBean.getPackageCode();
//				client = commonBean.getClient();
//				if (StringUtils.isEmpty(commonBean.getMobileCode())) {
//					errorMsg = "该用户设备不存在";
//				} else {
//					payload = JPush.buildPushObject_all_alias_alert(HtmlUtil.getTextFromHtml(msg.getMsgContent()), msg.getId(), msg.getMsgAction(), msg.getMsgActionUrl(), commonBean.getMobileCode());
//				}
//			} else {
//				errorMsg = "该用户设备不存在";
//			}
//		}
//		// 调用结果
//		PushResult result = null;
//		try {
//			/**
//			 *  1、发送所有人，判断是否为ios客户端，如果是两个极光发送
//			 *  2、发送个人，判断该用户是哪个客户端和是否为 39 或 79
//			 * 	3、39为新极光推送
//			 */
//			if (payload != null) {
//			    logger.info("payload: " + payload);
//				if (msg.getMsgDestinationType() == CustomConstants.MSG_PUSH_DESTINATION_TYPE_0) {
//					if (clientStr.contains(CustomConstants.CLIENT_IOS)) {
//						if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_39)) {
//                            result = JPushPro.getClientInstance().sendPush(payload);
//                            msgProId = String.valueOf(result.msg_id);
//                        }else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_YXB)) {
//                            result = JPushYXB.getClientInstance().sendPush(payload);
//                            msgYXBID = String.valueOf(result.msg_id);
//                        }else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZNB)) {
//                            result = JPushZNB.getClientInstance().sendPush(payload);
//                            msgZNBID = String.valueOf(result.msg_id);
//                        }else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZYB)) {
//                            result = JPushZYB.getClientInstance().sendPush(payload);
//                            msgZYBID = String.valueOf(result.msg_id);
//                        }else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZZB)) {
//                            result = JPushZZB.getClientInstance().sendPush(payload);
//                            msgZZBID = String.valueOf(result.msg_id);
//                        }else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_TEST)) {
//                            result = JPushTEST.getClientInstance().sendPush(payload);
//                            msgTESTID = String.valueOf(result.msg_id);
//                        }
//					}
//					result = JPush.getClientInstance().sendPush(payload);
//					msgId = String.valueOf(result.msg_id);
//				} else if (msg.getMsgDestinationType() == CustomConstants.MSG_PUSH_DESTINATION_TYPE_1) {
//					if(CustomConstants.CLIENT_ANDROID.equals(client)){
//						result = JPush.getClientInstance().sendPush(payload);
//						msgId = String.valueOf(result.msg_id);
//					}else if(CustomConstants.CLIENT_IOS.equals(client)){
//						if (StringUtils.isNotEmpty(pcode)) {
//						    logger.info("pcode: " + pcode);
//							if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_39)) {
//
//								result = JPushPro.getClientInstance().sendPush(payload);
//								msgProId = String.valueOf(result.msg_id);
//							}else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_YXB)) {
//                                result = JPushYXB.getClientInstance().sendPush(payload);
//                                msgYXBID = String.valueOf(result.msg_id);
//                            }else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZNB)) {
//                                result = JPushZNB.getClientInstance().sendPush(payload);
//                                msgZNBID = String.valueOf(result.msg_id);
//                            }else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZYB)) {
//                                result = JPushZYB.getClientInstance().sendPush(payload);
//                                msgZYBID = String.valueOf(result.msg_id);
//                            }else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZZB)) {
//                                result = JPushZZB.getClientInstance().sendPush(payload);
//                                msgZZBID = String.valueOf(result.msg_id);
//                            }else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_TEST)) {
//                                result = JPushTEST.getClientInstance().sendPush(payload);
//                                msgTESTID = String.valueOf(result.msg_id);
//                            } else {
//								result = JPush.getClientInstance().sendPush(payload);
//								msgId = String.valueOf(result.msg_id);
//							}
//						}
//					}
//				}
//			} else {
//				errorMsg = "发送失败，用户不存在";
//			}
//		} catch (APIConnectionException e) {
//			errorMsg = "调用极光接口异常,连接超时";
//			e.printStackTrace();
//		} catch (APIRequestException e) {
//			e.printStackTrace();
//			switch (e.getErrorCode()) {
//			case 1000:
//				errorMsg = "极光系统内部错误";
//				break;
//			case 1001:
//				errorMsg = "不支持 Get方法";
//				break;
//			case 1002:
//				errorMsg = "缺少了必须的参数";
//				break;
//			case 1003:
//				errorMsg = "参数值不合法";
//				break;
//			case 1004:
//				errorMsg = "验证失败";
//				break;
//			case 1005:
//				errorMsg = "消息体太大";
//				break;
//			case 1008:
//				errorMsg = "app_key参数非法";
//				break;
//			case 1011:
//				errorMsg = "没有满足条件的推送目标，用户不存在";
//				break;
//			case 1020:
//				errorMsg = "只支持 HTTPS请求";
//				break;
//			case 1030:
//				errorMsg = "内部服务超时";
//				break;
//			default:
//				errorMsg = "调用极光发生未知错误";
//				break;
//			}
//		}
//		// 成功
//		if (StringUtils.isNotEmpty(msgId) || StringUtils.isNotEmpty(msgProId) || StringUtils.isNotEmpty(msgZNBID)
//		        || StringUtils.isNotEmpty(msgYXBID) || StringUtils.isNotEmpty(msgZYBID) || StringUtils.isNotEmpty(msgZZBID)|| StringUtils.isNotEmpty(msgTESTID)) {
//			msg.setSendTime(GetDate.getNowTime10());
//			msg.setMsgSendStatus(CustomConstants.MSG_PUSH_SEND_STATUS_1);
//			msg.setMsgJpushId(msgId);
//			msg.setMsgJpushProId(msgProId);
//			msg.setMsgJpushYxbId(msgYXBID);
//			msg.setMsgJpushZnbId(msgZNBID);
//			msg.setMsgJpushZybId(msgZYBID);
//			msg.setMsgJpushZzbId(msgZZBID);
//			msg.setMsgJpushTestId(msgTESTID);
//			logger.info("发送消息成功：msgId: " + msgId);
//			logger.info("发送消息成功：msgProId: " + msgProId);
//			logger.info("发送消息成功：msgYXBID: " + msgYXBID);
//			logger.info("发送消息成功：msgZNBID: " + msgZNBID);
//			logger.info("发送消息成功：msgZYBID: " + msgZYBID);
//			logger.info("发送消息成功：msgZZBID: " + msgZZBID);
//			logger.info("发送消息成功：msgTESTID: " + msgTESTID);
//		} else {
//			msg.setSendTime(GetDate.getNowTime10());
//			msg.setMsgSendStatus(CustomConstants.MSG_PUSH_SEND_STATUS_2);
//			msg.setMsgRemark(errorMsg);
//			logger.info("发送消息失败：" + errorMsg);
//		}
//		// 更新userid
//		if (userId != null) {
//			msg.setMsgUserId(userId);
//		}
//		// 更新操作
//		messagePushMsgHistoryMapper.updateByPrimaryKeySelective(msg);
//	}

}
