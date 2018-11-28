/*
 * Copyright(c) 2012-2016 JD Pharma.Ltd. All Rights Reserved.
 */
package com.hyjf.am.vo.message;

import org.springframework.core.io.InputStreamSource;

import java.util.Arrays;
import java.util.Map;

/**
 * 邮件消息实体类
 * @author xiasq
 * @version AppMsMessage, v0.1 2018/6/22 14:08
 */
public class MailMessage extends HyjfMessage {

    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = -2686602497364771708L;

    private String subject;

    private String body;

    private String[] fileNames;

    private String[] toMailArray;

    private String mailKbn;

    private String serviceType;

    private byte[] is;

    public MailMessage() {
        super();
    }

    public MailMessage(Integer userId, Map<String, String> replaceStrs, String subject, String body,
                       String[] fileNames, String[] toMailArray, String mailKbn, String serviceType) {
        super(userId, replaceStrs);
        this.subject = subject;
        this.body = body;
        this.fileNames = fileNames;
        this.toMailArray = toMailArray;
        this.mailKbn = mailKbn;
        this.serviceType = serviceType;
    }

    public MailMessage(Integer userId, Map<String, String> replaceStrs, String subject, String body,
                       String[] fileNames, String[] toMailArray, String mailKbn, String serviceType, byte[] is) {
        super(userId, replaceStrs);
        this.subject = subject;
        this.body = body;
        this.fileNames = fileNames;
        this.toMailArray = toMailArray;
        this.mailKbn = mailKbn;
        this.serviceType = serviceType;
        this.is = is;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String[] getFileNames() {
        return fileNames;
    }

    public void setFileNames(String[] fileNames) {
        this.fileNames = fileNames;
    }

    public String[] getToMailArray() {
        return toMailArray;
    }

    public void setToMailArray(String[] toMailArray) {
        this.toMailArray = toMailArray;
    }

    public String getMailKbn() {
        return mailKbn;
    }

    public void setMailKbn(String mailKbn) {
        this.mailKbn = mailKbn;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public byte[] getIs() {
        return is;
    }

    public void setIs(byte[] is) {
        this.is = is;
    }


    @Override
    public String toString() {
        return "MailMessage{" +
                "subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", fileNames=" + Arrays.toString(fileNames) +
                ", toMailArray=" + Arrays.toString(toMailArray) +
                ", mailKbn='" + mailKbn + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", is=" + is +
                '}';
    }
}
