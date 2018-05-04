package com.hyjf.am.message.processer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;

import com.hyjf.am.message.client.AmConfigClient;
import com.hyjf.am.message.client.AmUserClient;
import com.hyjf.am.vo.config.SmsMailTemplateVO;
import com.hyjf.am.vo.user.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;

import com.hyjf.common.validator.Validator;

/**
 * <p>
 * 发送邮件共通
 * </p>
 *
 * @author gogtz
 * @version 1.0.0
 */
public class MailHandle {

	private static final Logger logger = LoggerFactory.getLogger(MailHandle.class);

	/** SMTP服务器 */
	private static String sendHost;
	/** 邮箱端口号 */
	private static String sendPort;
	/** 邮箱账户 */
	private static String sendUsername;
	/** 邮箱密码 */
	private static String sendPassword;
	/** 回复邮箱 */
	private static String fromMail;
	/** 发件人名 */
	private static String fromMailName;
	/** 是否需要身份验证 */
	private static String smtpAuth;
	/** 超时时间 */
	private static Integer smtpTimeout;
	/** 是否SSL加密 */
	private static String smtpSsl;
	/** 是否发送测试邮件 */
	private static Integer isNeedTest;

	@Autowired
	private static AmUserClient amUserClient;

	@Autowired
	private static AmConfigClient amConfigClient;

	/**
	 * 初始化
	 *
	 * @throws Exception
	 */
	private static void init() throws Exception {
		// if (sendHost == null || sendPort == null || sendUsername == null ||
		// sendPassword == null) {
		// // 取得邮件服务器信息
		// SiteSettingMapper siteSettingMapper =
		// SpringContextHolder.getBean(SiteSettingMapper.class);
		// List<SiteSetting> siteSettingList =
		// siteSettingMapper.selectByExample(new SiteSettingExample());
		// if (siteSettingList == null) {
		// throw new Exception("邮件配置无效");
		// }
		// SiteSetting set = siteSettingList.get(0);
		//
		// // SMTP服务器
		// sendHost = set.getSmtpServer();
		// // 邮箱端口号
		// sendPort = set.getSmtpPort();
		// // 邮箱账户
		// sendUsername = set.getSmtpUsername();
		// // 邮箱密码
		// sendPassword = set.getSmtpPassword();
		// // 回复邮箱
		// fromMail = set.getSmtpReply();
		// // 发件人名
		// fromMailName = set.getSiteName();
		// // 是否需要身份验证
		// smtpAuth = set.getSmtpVerify() == 1 ? "true" : "false";
		// // 是否SSL加密
		// smtpSsl = set.getSmtpSsl() == 1 ? "true" : "false";
		// // 是否发送测试邮件
		// isNeedTest = set.getSiteStatus();
		// // 超时时间(25s)
		// smtpTimeout = 25000;
		// }
	}

	/**
	 * 送信
	 *
	 * @param userId
	 * @param fMail
	 * @param fMailNm
	 * @param mailKbn
	 * @param replaceMap
	 * @throws Exception
	 */
	public static void sendMail(Integer userId, String subject, String mailKbn, Map<String, String> replaceMap,
			String[] fileNames) {
		// String methodName = "sendMail";
		//
		// LogUtil.startLog(MailHandle.class.getName(), methodName);
		// try {
		// // 取得模板
		// String email = getMailAddress(userId);
		// if (Validator.isNull(email)) {
		// return;
		// }
		//
		// // 取得用户详细信息
		// UsersInfoMapper usersInfoMapper =
		// SpringContextHolder.getBean(UsersInfoMapper.class);
		// UsersInfoExample userInfoExample = new UsersInfoExample();
		// userInfoExample.createCriteria().andUserIdEqualTo(userId);
		// List<UsersInfo> list =
		// usersInfoMapper.selectByExample(userInfoExample);
		// if (list != null && list.size() > 0) {
		// UsersInfo userInfo = list.get(0);
		// String sex = userInfo.getSex() == 1 ? "先生" : "女士";
		// replaceMap.put("val_name", userInfo.getTruename() + sex);
		// }
		//
		// // 开始送信
		// sendMail(new String[] { email }, subject, mailKbn, replaceMap,
		// fileNames);
		// } catch (Exception e) {
		// LogUtil.errorLog(MailHandle.class.getName(), methodName, "发送邮件失败!", e);
		// } finally {
		// LogUtil.endLog(MailHandle.class.getName(), methodName);
		// }
	}

	/**
	 * 送信
	 *
	 * @param userId
	 * @param fMail
	 * @param fMailNm
	 * @param body
	 * @throws Exception
	 */
	public static void sendMail(Integer userId, String subject, String body, String[] fileNames) {
		try {
			// 取得模板
			String email = getMailAddress(userId);
			if (Validator.isNull(email)) {
				return;
			}
			// 开始送信
			sendMail(new String[] { email }, subject, body, fileNames);
		} catch (Exception e) {
			logger.error("发送邮件失败...", e);
		}
	}

	/**
	 * 送信
	 *
	 * @param toMails
	 * @param fMail
	 * @param fMailNm
	 * @param mailKbn
	 * @param replaceMap
	 * @throws Exception
	 */
	public static void sendMail(String[] toMailArray, String subject, String mailKbn, Map<String, String> replaceMap,
			String[] fileNames) {

		try {
			// 取得模板
			SmsMailTemplateVO template = amConfigClient.findSmsMailTemplateByCode(mailKbn);

			// 替换模板文件中的变量
			if (Validator.isNull(subject)) {
				subject = replaceAllParameter(template.getMailName(), replaceMap);
			}
			String body = replaceAllParameter(template.getMailContent(), replaceMap);

			// 开始送信
			send(toMailArray, subject, body, fileNames);
		} catch (Exception e) {
			logger.error("发送邮件失败", e);
		}
	}

	/**
	 * 给平台自己发送短信
	 *
	 * @param toMails
	 * @param fMail
	 * @param fMailNm
	 * @param mailKbn
	 * @param replaceMap
	 * @throws Exception
	 */
	public static void sendMailToSelf(String mailKbn, Map<String, String> replaceMap, String[] fileNames)
			throws Exception {
		// String methodName = "sendMailToSelf";
		//
		// LogUtil.startLog(MailHandle.class.getName(), methodName);
		// try {
		// // 取得模板
		// SmsMailTemplate template = getMailContent(mailKbn);
		// String body = replaceAllParameter(template.getMailContent(),
		// replaceMap);
		// String subject = template.getMailName();
		// String[] toMailArray = new String[1];
		// toMailArray[0] = fromMail;
		// // 开始送信
		// send(toMailArray, subject, body, fileNames);
		// } catch (Exception e) {
		// LogUtil.errorLog(MailHandle.class.getName(), methodName, "发送邮件失败!", e);
		// } finally {
		// LogUtil.endLog(MailHandle.class.getName(), methodName);
		// }
	}

	/**
	 * 送信
	 *
	 * @param toMails
	 * @param fMail
	 * @param body
	 * @param replaceMap
	 * @throws Exception
	 */
	public static void sendMail(String[] toMailArray, String subject, String body, String[] fileNames) {
		try {
			// 开始送信
			send(toMailArray, subject, body, fileNames);
		} catch (Exception e) {
			logger.error("发送邮件失败...", e);
		}
	}

	/**
	 * 送信
	 *
	 * @param toMails
	 * @param fMail
	 * @param fMailNm
	 * @param subject
	 * @param content
	 * @throws Exception
	 */
	private static void send(String[] toMailArray, String subject, String content, String[] fileNames)
			throws Exception {
		init();

		// if (StringUtils.isBlank(sendHost) || StringUtils.isBlank(sendHost) ||
		// StringUtils.isBlank(sendHost)
		// || StringUtils.isBlank(sendHost)) {
		// throw new Exception(PropUtils.getMessage(CustomConstants.ECOMS001));
		// }
		//
		// JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		//
		// // 邮件服务器地址
		// senderImpl.setHost(sendHost);
		//
		// MimeMessage mailMessage = senderImpl.createMimeMessage();
		//
		// MimeMessageHelper messageHelper = null;
		// messageHelper = new MimeMessageHelper(mailMessage, true,
		// CustomConstants.MAIL_SEND_DEFAULTENCODING);
		// // 发件人
		// messageHelper.setFrom(new InternetAddress(fromMail, fromMailName));
		// // 收件人
		// List<String> toMailList = new ArrayList<String>();
		// if (null == toMailArray || toMailArray.length == 0) {
		// throw new Exception("收件人不能为空");
		// } else {
		// for (String toMail : toMailArray) {
		// if (StringUtils.isNoneBlank(toMail)) {
		// toMailList.add(toMail);
		// }
		// }
		// if (null == toMailList || toMailList.size() <= 0) {
		// throw new RuntimeException("收件人不能为空");
		// } else {
		// toMailArray = new String[toMailList.size()];
		// for (int i = 0; i < toMailList.size(); i++) {
		// toMailArray[i] = toMailList.get(i);
		// }
		// }
		// }
		// messageHelper.setTo(toMailArray);
		// // 标题
		// messageHelper.setSubject(subject);
		// // 内容
		// messageHelper.setText(content, true);
		// // 发送时间
		// messageHelper.setSentDate(GetDate.getDate());
		// // 附件
		// if (fileNames != null && fileNames.length > 0) {
		// // 向邮件添加附件
		// for (String filename : fileNames) {
		// if (StringUtils.isNotEmpty(filename)) {
		// File file = new File(filename);
		// if (file.exists()) {
		// messageHelper.addAttachment(file.getName(), file);
		// }
		// }
		// }
		// }
		//
		// Properties prop = new Properties();
		// prop.put(CustomConstants.MAIL_SMTP_AUTH, smtpAuth);
		// prop.put(CustomConstants.MAIL_SMTP_TIMEOUT, smtpTimeout);
		// prop.put(CustomConstants.MAIL_SMTP_STARTTLS_ENABLE, smtpSsl);
		// MyAuthenticator auth = new MyAuthenticator(sendUsername,
		// sendPassword);
		// Session session = Session.getDefaultInstance(prop, auth);
		// senderImpl.setSession(session);
		// senderImpl.send(mailMessage);
	}

	/**
	 * 替换模板变量
	 *
	 * @param replace
	 * @param replaceMap
	 * @return
	 */
	private static String replaceAllParameter(String messageStr, Map<String, String> replaceMap) {
		if (Validator.isNotNull(messageStr)) {
			for (String key : replaceMap.keySet()) {
				messageStr = StringUtils.replace(messageStr, "[" + key + "]", replaceMap.get(key));
			}
		}
		return messageStr;
	}

	/**
	 * 取得邮件内容
	 *
	 * @param mailKbn
	 * @return
	 * @throws Exception
	 */
	// private static SmsMailTemplate getMailContent(String mailKbn) throws
	// Exception {
	//// // 取得模板
	//// SmsMailTemplateExample example = new SmsMailTemplateExample();
	//// example.createCriteria().andMailValueEqualTo(mailKbn).andMailStatusEqualTo(1);
	//// SmsMailTemplateMapper mailTemplateMapper =
	// SpringContextHolder.getBean(SmsMailTemplateMapper.class);
	//// List<SmsMailTemplate> templateList =
	// mailTemplateMapper.selectByExampleWithBLOBs(example);
	//// if (templateList == null || templateList.size() == 0) {
	//// throw new Exception("邮件模板不存在");
	//// }
	//// return templateList.get(0);
	// }

	/**
	 * 根据用户ID取得用户邮箱
	 *
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	private static String getMailAddress(Integer userId) throws Exception {
		UserVO userVO = amUserClient.findUserById(userId);
		if (userVO == null) {
			throw new Exception("用户信息不存在");
		}
		return userVO.getEmail();
	}

	/**
	 * 送信(包含附件),用spring实现
	 * 
	 * @param toMails
	 * @param fMail
	 * @param fMailNm
	 * @param subject
	 * @param content
	 * @throws Exception
	 */
	public static void sendAttachmentsMailBySpring(String[] toMailArray, String subject, String content,
			String[] fileNames, InputStreamSource is) throws Exception {
		init();
		// if (StringUtils.isBlank(sendHost) || StringUtils.isBlank(sendHost) ||
		// StringUtils.isBlank(sendHost)
		// || StringUtils.isBlank(sendHost)) {
		// throw new Exception(PropUtils.getMessage(CustomConstants.ECOMS001));
		// }
		//
		// JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		//
		// // 邮件服务器地址
		// senderImpl.setHost(sendHost);
		// senderImpl.setPort(Integer.parseInt(sendPort));
		//
		// MimeMessage mailMessage = senderImpl.createMimeMessage();
		//
		// MimeMessageHelper messageHelper = null;
		// messageHelper = new MimeMessageHelper(mailMessage, true,
		// CustomConstants.MAIL_SEND_DEFAULTENCODING);
		// // 发件人
		// messageHelper.setFrom(new InternetAddress(fromMail, fromMailName));
		// // 收件人
		// List<String> toMailList = new ArrayList<String>();
		// if (null == toMailArray || toMailArray.length == 0) {
		// throw new Exception("收件人不能为空");
		// } else {
		// for (String toMail : toMailArray) {
		// if (StringUtils.isNoneBlank(toMail)) {
		// toMailList.add(toMail);
		// }
		// }
		// if (null == toMailList || toMailList.size() <= 0) {
		// throw new RuntimeException("收件人不能为空");
		// } else {
		// toMailArray = new String[toMailList.size()];
		// for (int i = 0; i < toMailList.size(); i++) {
		// toMailArray[i] = toMailList.get(i);
		// }
		// }
		// }
		// messageHelper.setTo(toMailArray);
		// // 标题
		// messageHelper.setSubject(subject);
		// // 内容
		// messageHelper.setText(content, true);
		// // 发送时间
		// messageHelper.setSentDate(GetDate.getDate());
		// // 附件
		// if (fileNames != null && fileNames.length > 0) {
		// // 向邮件添加附件
		// for (String filename : fileNames) {
		// messageHelper.addAttachment(filename, is);
		// }
		// }
		//
		// Properties prop = new Properties();
		// prop.put(CustomConstants.MAIL_SMTP_AUTH, smtpAuth);
		// prop.put(CustomConstants.MAIL_SMTP_TIMEOUT, smtpTimeout);
		// prop.put(CustomConstants.MAIL_SMTP_STARTTLS_ENABLE, smtpSsl);
		// prop.setProperty(CustomConstants.MAIL_SMTP_SOCKETFACTORY_CLASS,
		// CustomConstants.MAIL_SMTP_SOCKETFACTORY_CLASS_VALUE);
		// prop.setProperty(CustomConstants.MAIL_SMTP_SOCKETFACTORY_FALLBACK,
		// CustomConstants.MAIL_SMTP_SOCKETFACTORY_FALLBACK_VALUE);
		// MyAuthenticator auth = new MyAuthenticator(sendUsername,
		// sendPassword);
		// Session session = Session.getDefaultInstance(prop, auth);
		// senderImpl.setSession(session);
		// senderImpl.send(mailMessage);
	}

	/**
	 * 由于阿里云25邮箱端口封禁，使用465端口发送邮件(用Javax实现)
	 * 
	 * @param toMailArray
	 * @param subject
	 * @param content
	 * @param fileNames
	 * @param is
	 * @throws Exception
	 */
	public static void sendAttachmentsMailByJavax(String[] toMailArray, String subject, String content,
			final String[] fileNames, final InputStreamSource is) throws Exception {
		// 1. 初始化配置
		init();

		// 2. 建立连接
		Session session = Session.getDefaultInstance(buildMailProperties(), new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sendUsername, sendPassword);
			}
		});

		// 3. 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
		Multipart multipart = new MimeMultipart();

		// 添加邮件正文
		BodyPart contentPart = new MimeBodyPart();
		try {
			contentPart.setContent(content, "text/html;charset=UTF-8");
			multipart.addBodyPart(contentPart);
			// File[] attachments = buildAttachments(fileNames);
			// 添加附件的内容
			if (fileNames != null) {
				BodyPart attachmentBodyPart = null;
				for (String attachment : fileNames) {
					attachmentBodyPart = new MimeBodyPart();
					DataSource dataSource = createDataSource(is, "text/html; charset=UTF-8", attachment);
					attachmentBodyPart.setDataHandler(new DataHandler(dataSource));
					// MimeUtility.encodeWord可以避免文件名乱码
					attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachment));
					multipart.addBodyPart(attachmentBodyPart);
				}
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 4. 创建邮件对象, 发送
		Message msg = new MimeMessage(session);
		try {
			// 设置邮件标题
			msg.setSubject(subject);
			// 将multipart对象放到message中
			msg.setContent(multipart);
			// 设置发件人
			msg.setFrom(new InternetAddress(fromMail, fromMailName));
			// 设置收件人
			msg.setRecipients(Message.RecipientType.TO, buildRcvAddress(toMailArray));
			// 邮件发送
			Transport.send(msg);
		} catch (MessagingException e) {
			logger.error("邮件发送失败....", e);
		}
	}

	protected static DataSource createDataSource(final InputStreamSource inputStreamSource, final String contentType,
			final String name) {
		return new DataSource() {
			@Override
			public InputStream getInputStream() throws IOException {
				return inputStreamSource.getInputStream();
			}

			@Override
			public OutputStream getOutputStream() {
				throw new UnsupportedOperationException("Read-only javax.activation.DataSource");
			}

			@Override
			public String getContentType() {
				return contentType;
			}

			@Override
			public String getName() {
				return name;
			}
		};
	}

	/**
	 * 构建邮件属性
	 * 
	 * @return
	 */
	private static Properties buildMailProperties() {
		// Properties props = new Properties();
		// // 发送服务器需要身份验证
		// props.setProperty(CustomConstants.MAIL_SMTP_AUTH, smtpAuth);
		// // 设置邮件服务器主机名
		// props.setProperty(CustomConstants.MAIL_HOST, sendHost);
		// // 发送邮件协议名称
		// //props.setProperty("mail.transport.protocol", "SMTP");
		// props.setProperty(CustomConstants.MAIL_SMTP_SOCKETFACTORY_CLASS,
		// CustomConstants.MAIL_SMTP_SOCKETFACTORY_CLASS_VALUE);
		// props.setProperty(CustomConstants.MAIL_SMTP_SOCKETFACTORY_FALLBACK,
		// CustomConstants.MAIL_SMTP_SOCKETFACTORY_FALLBACK_VALUE);
		// props.setProperty(CustomConstants.MAIL_SMTP_PORT,
		// CustomConstants.MAIL_SMTP_SOCKETFACTORY_PORT_VALUE);
		// props.setProperty(CustomConstants.MAIL_SMTP_SOCKETFACTORY_PORT,
		// CustomConstants.MAIL_SMTP_SOCKETFACTORY_PORT_VALUE);
		// return props;
		return null;
	}

	/**
	 * 构建收信人地址
	 * 
	 * @param toMailArray
	 * @return
	 * @throws AddressException
	 */
	private static InternetAddress[] buildRcvAddress(String[] toMailArray) throws AddressException {
		InternetAddress[] addrs = new InternetAddress[toMailArray.length];
		for (int i = 0; i < toMailArray.length; i++) {
			addrs[i] = new InternetAddress(toMailArray[i]);
		}
		return addrs;
	}
}
