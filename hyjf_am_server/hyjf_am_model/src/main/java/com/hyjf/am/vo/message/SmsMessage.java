/*
 * Copyright(c) 2012-2016 JD Pharma.Ltd. All Rights Reserved.
 */
package com.hyjf.am.vo.message;

import java.util.Map;

/**
 * 短信消息实体类
 * @author xiasq
 * @version AppMsMessage, v0.1 2018/6/22 14:08
 */
public class SmsMessage extends HyjfMessage {

    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = -5957270230076216841L;

    private String mobile;

    private String message;

    private String serviceType;

    private String sender;

    private String tplCode;

    private String channelType;

    private Integer isDisplay;

    public SmsMessage() {
        super();
    }

    public SmsMessage(Integer userId, Map<String, String> replaceStrs, String mobile, String message,
                      String serviceType, String sender, String tplCode, String channelType) {
        super(userId, replaceStrs);
        this.mobile = mobile;
        this.message = message;
        this.serviceType = serviceType;
        this.sender = sender;
        this.tplCode = tplCode;
        this.channelType = channelType;
    }

    public SmsMessage(Integer userId, Map<String, String> replaceStrs, String mobile, String message,
                      String serviceType, String sender, String tplCode, String channelType, Integer isDisplay) {
        super(userId, replaceStrs);
        this.mobile = mobile;
        this.message = message;
        this.serviceType = serviceType;
        this.sender = sender;
        this.tplCode = tplCode;
        this.channelType = channelType;
        this.isDisplay = isDisplay;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTplCode() {
        return tplCode;
    }

    public void setTplCode(String tplCode) {
        this.tplCode = tplCode;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public Integer getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(Integer isDisplay) {
        this.isDisplay = isDisplay;
    }

    @Override
    public String toString() {
        return "SmsMessage{" +
                "mobile='" + mobile + '\'' +
                ", message='" + message + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", sender='" + sender + '\'' +
                ", tplCode='" + tplCode + '\'' +
                ", channelType='" + channelType + '\'' +
                '}';
    }
}
