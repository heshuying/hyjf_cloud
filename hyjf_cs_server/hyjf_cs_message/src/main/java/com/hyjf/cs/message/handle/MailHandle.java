package com.hyjf.cs.message.handle;

import com.hyjf.am.vo.config.SiteSettingsVO;
import com.hyjf.am.vo.config.SmsMailTemplateVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.message.client.AmConfigClient;
import com.hyjf.cs.message.client.AmUserClient;
import com.hyjf.cs.message.util.MyAuthenticator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * <p>
 * 发送邮件共通
 * </p>
 *
 * @author gogtz
 * @version 1.0.0
 */
@Component
public class MailHandle {

    private static final Logger logger = LoggerFactory.getLogger(MailHandle.class);
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
     * @param mailKbn
     * @param replaceMap
     * @param fileNames
     */
    public void sendMail(Integer userId, String subject, String mailKbn, Map<String, String> replaceMap,
                         String[] fileNames) {
        try {
            // 取得模板
            String email = getMailAddress(userId);
            if (Validator.isNull(email)) {
                return;
            }

            // 取得用户详细信息
            UserInfoVO userInfoVO = amUserClient.findUsersInfoById(userId);
            if (userInfoVO != null) {
                String sex = userInfoVO.getSex() == 1 ? "先生" : "女士";
                replaceMap.put("val_name", userInfoVO.getTruename() + sex);
            }

            // 开始送信
            sendMail(new String[]{email}, subject, mailKbn, replaceMap, fileNames);
        } catch (Exception e) {
            logger.error("发送邮件失败...", e);
        }
    }

    /**
     * 送信
     *
     * @param userId
     * @param subject
     * @param body
     * @param fileNames
     */
    public void sendMail(Integer userId, String subject, String body, String[] fileNames) {
        try {
            // 取得模板
            String email = getMailAddress(userId);
            if (Validator.isNull(email)) {
                return;
            }
            // 开始送信
            sendMail(new String[]{email}, subject, body, fileNames);
        } catch (Exception e) {
            logger.error("发送邮件失败...", e);
        }
    }

    /**
     * @param toMailArray
     * @param subject
     * @param mailKbn
     * @param replaceMap
     * @param fileNames
     */
    public void sendMail(String[] toMailArray, String subject, String mailKbn, Map<String, String> replaceMap,
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
     * @param mailKbn
     * @param replaceMap
     * @param fileNames
     * @throws Exception
     */
    public void sendMailToSelf(String mailKbn, Map<String, String> replaceMap, String[] fileNames)
            throws Exception {
        try {
            init();
            // 取得模板
            SmsMailTemplateVO template = amConfigClient.findSmsMailTemplateByCode(mailKbn);
            String body = replaceAllParameter(template.getMailContent(), replaceMap);
            String subject = template.getMailName();
            String[] toMailArray = new String[1];
            toMailArray[0] = setting.getSmtpReply();
            // 开始送信
            send(toMailArray, subject, body, fileNames);
        } catch (Exception e) {
            logger.error("发送邮件失败...", e);
        }
    }

    /**
     * 送信
     *
     * @param toMailArray
     * @param subject
     * @param body
     * @param fileNames
     */
    public void sendMail(String[] toMailArray, String subject, String body, String[] fileNames) {
        try {
            // 开始送信
            send(toMailArray, subject, body, fileNames);
        } catch (Exception e) {
            logger.error("发送邮件失败...", e);
        }
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

    /**
     * 送信
     *
     * @param toMailArray
     * @param subject
     * @param content
     * @param fileNames
     * @throws Exception
     */
    private void send(String[] toMailArray, String subject, String content, String[] fileNames)
            throws Exception {
        init();
        if (setting == null) {
            throw new RuntimeException(CustomConstants.ECOMS001);
        }

        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

        // 邮件服务器地址
        senderImpl.setHost(setting.getSmtpServer());

        MimeMessage mailMessage = senderImpl.createMimeMessage();

        MimeMessageHelper messageHelper = null;
        messageHelper = new MimeMessageHelper(mailMessage, true, CustomConstants.MAIL_SEND_DEFAULTENCODING);
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
        logger.info("siteSetting is: {}", setting);
        Properties prop = new Properties();
        prop.put(CustomConstants.MAIL_SMTP_AUTH, setting.getSmtpVerify() == 1 ? "true" : "false");
        prop.put(CustomConstants.MAIL_SMTP_TIMEOUT, smtpTimeout);
        prop.put(CustomConstants.MAIL_SMTP_STARTTLS_ENABLE, setting.getSmtpSsl() == 1 ? "true" : "false");
        prop.put(CustomConstants.MAIL_SMTP_SOCKETFACTORY_CLASS, CustomConstants.MAIL_SMTP_SOCKETFACTORY_CLASS_VALUE);
        prop.put(CustomConstants.MAIL_SMTP_SOCKETFACTORY_FALLBACK, CustomConstants.MAIL_SMTP_SOCKETFACTORY_FALLBACK_VALUE);
        prop.put(CustomConstants.MAIL_SMTP_PORT, CustomConstants.MAIL_SMTP_SOCKETFACTORY_PORT_VALUE);
        prop.put(CustomConstants.MAIL_SMTP_SOCKETFACTORY_PORT, CustomConstants.MAIL_SMTP_SOCKETFACTORY_PORT_VALUE);
        MyAuthenticator auth = new MyAuthenticator(setting.getSmtpUsername(), setting.getSmtpPassword());
        Session session = Session.getDefaultInstance(prop, auth);
        senderImpl.setSession(session);
        senderImpl.send(mailMessage);
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
     * 使用25端口发邮件   等同send()方法，参数不同，使用流
     *
     * @param toMailArray
     * @param subject
     * @param content
     * @param fileName
     * @param is
     * @throws Exception
     */
    public void sendAttachmentsMailOnPort25(String[] toMailArray, String subject, String content, String fileName, InputStreamSource is)
            throws Exception {
        this.init();

        if (StringUtils.isBlank(setting.getSmtpServer())) {
            throw new Exception(CustomConstants.ECOMS001);
        }

        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

        // 邮件服务器地址
        senderImpl.setHost(setting.getSmtpPort());
        //邮件服务器端口
        senderImpl.setPort(Integer.parseInt(setting.getSmtpPort()));

        MimeMessage mailMessage = senderImpl.createMimeMessage();

        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, CustomConstants.MAIL_SEND_DEFAULTENCODING);

        // 是否需要身份验证
        String smtpAuth = setting.getSmtpVerify() == 1 ? "true" : "false";
        // 是否SSL加密
        String smtpSsl = setting.getSmtpSsl() == 1 ? "true" : "false";
        // 邮箱账户
        String sendUsername = setting.getSmtpUsername();
        // 邮箱密码
        String sendPassword = setting.getSmtpPassword();
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
        // 附件
        // 向邮件添加附件
        messageHelper.addAttachment(fileName, is);

        Properties prop = new Properties();
        prop.put(CustomConstants.MAIL_SMTP_AUTH, smtpAuth);
        prop.put(CustomConstants.MAIL_SMTP_TIMEOUT, smtpTimeout);
        prop.put(CustomConstants.MAIL_SMTP_STARTTLS_ENABLE, smtpSsl);
        MyAuthenticator auth = new MyAuthenticator(sendUsername, sendPassword);
        Session session = Session.getDefaultInstance(prop, auth);
        senderImpl.setSession(session);
        senderImpl.send(mailMessage);
    }
}
