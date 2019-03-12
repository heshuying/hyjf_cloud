package com.hyjf.cs.message.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.hyjf.am.vo.config.SiteSettingsVO;
import com.hyjf.am.vo.config.SmsMailTemplateVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.message.client.AmConfigClient;
import com.hyjf.cs.message.client.AmUserClient;
import com.hyjf.cs.message.config.PropertiesConfig;
import com.hyjf.cs.message.util.MyAuthenticator;

/**
 * <p>
 * 发送邮件共通
 * </p>
 *
 * @author gogtz
 * @version 1.0.0
 */
@Component
public class MailHandler {
	private static final Logger logger = LoggerFactory.getLogger(MailHandler.class);
	private static SiteSettingsVO setting = null;
	/**
	 * 超时时间
	 */
	private static Integer smtpTimeout = 25000;

	@Autowired
	private AmUserClient amUserClient;
	@Autowired
	private AmConfigClient amConfigClient;

	private void init() throws RuntimeException {
		if (setting == null) {
			// 取得邮件服务器信息
			SiteSettingsVO siteSetting = amConfigClient.findSiteSetting();
			if (siteSetting == null) {
				throw new RuntimeException("邮件配置无效");
			}
			setting = siteSetting;
		}
	}

	/**
	 * 送信
	 *
	 * @param userId
	 * @param subject
	 * @param body
	 * @param fileNames
	 *            文件
	 */
	public void sendMail(Integer userId, String subject, String body, String[] fileNames) {
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
	 * @param toMailArray
	 * @param subject
	 * @param mailKbn
	 * @param replaceMap
	 *            替换参数集
	 * @param fileNames
	 *            文件
	 */
	public void sendMail(String[] toMailArray, String subject, String mailKbn, Map<String, String> replaceMap,
			String[] fileNames) {

		try {
			// 取得模板
			SmsMailTemplateVO template = amConfigClient.findSmsMailTemplateByCode(mailKbn);
			if (template == null) {
				logger.warn("未匹配到邮件模板, 模板号 :{}", mailKbn);
				return;
			}

			// 替换模板文件中的变量
			if (Validator.isNull(subject)) {
				subject = replaceAllParameter(template.getMailName(), replaceMap);
			}
			String body = replaceAllParameter(template.getMailContent(), replaceMap);

			doSend(toMailArray, subject, body, fileNames);
		} catch (Exception e) {
			logger.error("发送邮件失败", e);
		}
	}

	/**
	 * 送信
	 *
	 * @param toMailArray
	 * @param subject
	 * @param body
	 * @param fileNames 文件名（磁盘存在）
	 */
	public void sendMail(String[] toMailArray, String subject, String body, String[] fileNames) {
		try {
			// 开始送信
			doSend(toMailArray, subject, body, fileNames);
		} catch (Exception e) {
			logger.error("发送邮件失败...", e);
		}
	}

	/**
	 *
	 * @param toMailArray
	 * @param subject
	 * @param body
	 * @param fileNames 文件名（磁盘不存在）
	 * @param is 文件流
	 */
	public void sendMail(String[] toMailArray, String subject, String body, String[] fileNames, final byte[] is) {
		try {
			// 开始送信
			doSend(toMailArray, subject, body, fileNames, is);
		} catch (Exception e) {
			logger.error("发送邮件失败...", e);
		}
	}

	/**
	 * 送信
	 *
	 * @param toMailArray
	 * @param subject
	 * @param content
	 * @param fileNames
	 * @throws Exception
	 */
	private void doSend(String[] toMailArray, String subject, String content, String[] fileNames) throws Exception {
		if (PropertiesConfig.hyjfEnvProperties.isTest()) {
			if (!isSend(toMailArray, subject)) {
				return;
			}
		}

		init();
		if (setting == null) {
			throw new RuntimeException(CustomConstants.ECOMS001);
		}
		logger.debug("siteSetting is: {}", setting);

		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		// 邮件服务器地址
		senderImpl.setHost(setting.getSmtpServer());
		MyAuthenticator auth = new MyAuthenticator(setting.getSmtpUsername(), setting.getSmtpPassword());
		Session session = Session.getDefaultInstance(buildProperties(), auth);
		senderImpl.setSession(session);
		senderImpl.send(buildMimeMessage(senderImpl, subject, toMailArray, content, fileNames));
	}

	/**
	 * 送信
	 *
	 * @param toMailArray
	 * @param subject
	 * @param content
	 * @param fileNames
	 * @throws Exception
	 */
	private void doSend(String[] toMailArray, String subject, String content, String[] fileNames, final byte[] is)
			throws Exception {
		if (PropertiesConfig.hyjfEnvProperties.isTest()) {
			if (!isSend(toMailArray, subject)) {
				return;
			}
		}

		init();
		if (setting == null) {
			throw new RuntimeException(CustomConstants.ECOMS001);
		}
		logger.debug("siteSetting is: {}", setting);

		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		// 邮件服务器地址
		senderImpl.setHost(setting.getSmtpServer());
		MyAuthenticator auth = new MyAuthenticator(setting.getSmtpUsername(), setting.getSmtpPassword());
		Session session = Session.getDefaultInstance(buildProperties(), auth);
		senderImpl.setSession(session);
		senderImpl.send(buildMimeMessage(senderImpl, subject, toMailArray, content, fileNames, is));
	}

	private MimeMessage buildMimeMessage(JavaMailSenderImpl senderImpl, String subject, String[] toMailArray,
			String content, String[] fileNames) throws Exception {
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true,
				CustomConstants.MAIL_SEND_DEFAULTENCODING);
		buildMimeMessageHelper(messageHelper, subject, toMailArray, content);

		// 附件
		if (fileNames != null && fileNames.length > 0) {
			// 向邮件添加附件
			for (String filename : fileNames) {
				if (StringUtils.isNotEmpty(filename)) {
					File file = new File(filename);
					if (file.exists()) {
						messageHelper.addAttachment(file.getName(), file);
					}
				}
			}
		}
		return mailMessage;
	}

	private MimeMessage buildMimeMessage(JavaMailSenderImpl senderImpl, String subject, String[] toMailArray,
			String content, String[] fileNames, final byte[] is) throws Exception {
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true,
				CustomConstants.MAIL_SEND_DEFAULTENCODING);
		buildMimeMessageHelper(messageHelper, subject, toMailArray, content);
		InputStreamSource iss = new ByteArrayResource(is);
		// 附件
		if (fileNames != null && fileNames.length > 0) {
			// 向邮件添加附件
			for (String filename : fileNames) {
				messageHelper.addAttachment(filename, iss);
			}
		}
		return mailMessage;
	}

	private MimeMessageHelper buildMimeMessageHelper(MimeMessageHelper messageHelper, String subject,
			String[] toMailArray, String content) throws Exception {
		// 发件人
		messageHelper.setFrom(new InternetAddress(setting.getSmtpReply(), setting.getSiteName()));
		// 收件人
		List<String> toMailList = new ArrayList<String>();
		if (null == toMailArray || toMailArray.length == 0) {
			throw new Exception("收件人不能为空");
		} else {
			for (String toMail : toMailArray) {
				if (StringUtils.isNoneBlank(toMail)) {
					toMailList.add(toMail);
				}
			}
			if (null == toMailList || toMailList.size() <= 0) {
				throw new RuntimeException("收件人不能为空");
			} else {
				toMailArray = new String[toMailList.size()];
				for (int i = 0; i < toMailList.size(); i++) {
					toMailArray[i] = toMailList.get(i);
				}
			}
		}
		messageHelper.setTo(toMailArray);
		// 标题
		messageHelper.setSubject(subject);
		// 内容
		messageHelper.setText(content, true);
		// 发送时间
		messageHelper.setSentDate(GetDate.getDate());

		return messageHelper;
	}

	private Properties buildProperties() {
		Properties prop = new Properties();
		prop.put(CustomConstants.MAIL_SMTP_AUTH, setting.getSmtpVerify() == 1 ? "true" : "false");
		prop.put(CustomConstants.MAIL_SMTP_TIMEOUT, smtpTimeout);
		prop.put(CustomConstants.MAIL_SMTP_STARTTLS_ENABLE, setting.getSmtpSsl() == 1 ? "true" : "false");
		prop.put(CustomConstants.MAIL_SMTP_SOCKETFACTORY_CLASS, CustomConstants.MAIL_SMTP_SOCKETFACTORY_CLASS_VALUE);
		prop.put(CustomConstants.MAIL_SMTP_SOCKETFACTORY_FALLBACK,
				CustomConstants.MAIL_SMTP_SOCKETFACTORY_FALLBACK_VALUE);
		prop.put(CustomConstants.MAIL_SMTP_PORT, CustomConstants.MAIL_SMTP_SOCKETFACTORY_PORT_VALUE);
		prop.put(CustomConstants.MAIL_SMTP_SOCKETFACTORY_PORT, CustomConstants.MAIL_SMTP_SOCKETFACTORY_PORT_VALUE);
		return prop;
	}

	/**
	 * 根据用户ID取得用户邮箱
	 *
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	private String getMailAddress(Integer userId) throws Exception {
		UserVO userVO = amUserClient.findUserById(userId);
		if (userVO == null) {
			throw new Exception("用户信息不存在");
		}
		return userVO.getEmail();
	}

	/**
	 * 替换模板变量
	 *
	 * @param messageStr
	 * @param replaceMap
	 * @return
	 */
	private String replaceAllParameter(String messageStr, Map<String, String> replaceMap) {
		if (Validator.isNotNull(messageStr)) {
			for (String key : replaceMap.keySet()) {
				messageStr = StringUtils.replace(messageStr, "[" + key + "]", replaceMap.get(key));
			}
		}
		return messageStr;
	}

	private boolean isSend(String[] toMailArray, String subject) {
		String emailWhiteList = PropertiesConfig.hyjfEnvProperties.getEmailWhiteList();
		if (StringUtils.isBlank(emailWhiteList)) {
			logger.warn("测试环境白名单用户未配置！， subject is : {}", subject);
			return false;
		}
		for (String toMail : toMailArray) {
			if (StringUtils.isNoneBlank(toMail)) {
				boolean contains = emailWhiteList.contains(toMail);
				if (!contains) {
					logger.warn("测试环境非白名单用户不发送邮件， subject is : {}", subject);
					return false;
				}
			}
		}
		return true;
	}
}
