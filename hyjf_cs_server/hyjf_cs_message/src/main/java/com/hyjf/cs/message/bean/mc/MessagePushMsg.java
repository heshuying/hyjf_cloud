package com.hyjf.cs.message.bean.mc;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 消息推送-发送的消息表
 * @author lisheng
 * @version MessagePushMsg, v0.1 2018/8/14 14:51
 */

@Document(collection = "ht_message_push_msg")
public class MessagePushMsg implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private Integer tagId;

    private String tagCode;

    private String msgCode;

    private String msgTitle;

    private String msgImageUrl;

    private String msgContent;

    //private String msgTerminal;
    private String msgTerminal[];
    private Integer msgAction;

    private String msgActionUrl;

    private Integer msgDestinationType;

    private Integer msgSendStatus;

    private Integer msgSendType;

    private Integer preSendTime;

    private Integer sendTime;

    private Integer createTime;

    private Integer createUserId;

    private String createUserName;

    private Integer lastupdateTime;

    private Integer lastupdateUserId;

    private String lastupdateUserName;

    private String msgDestination;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagCode() {
        return tagCode;
    }

    public void setTagCode(String tagCode) {
        this.tagCode = tagCode;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgImageUrl() {
        return msgImageUrl;
    }

    public void setMsgImageUrl(String msgImageUrl) {
        this.msgImageUrl = msgImageUrl;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String[] getMsgTerminal() {
        return msgTerminal;
    }

    public void setMsgTerminal(String[] msgTerminal) {
        this.msgTerminal = msgTerminal;
    }

    public Integer getMsgAction() {
        return msgAction;
    }

    public void setMsgAction(Integer msgAction) {
        this.msgAction = msgAction;
    }

    public String getMsgActionUrl() {
        return msgActionUrl;
    }

    public void setMsgActionUrl(String msgActionUrl) {
        this.msgActionUrl = msgActionUrl;
    }

    public Integer getMsgDestinationType() {
        return msgDestinationType;
    }

    public void setMsgDestinationType(Integer msgDestinationType) {
        this.msgDestinationType = msgDestinationType;
    }

    public Integer getMsgSendStatus() {
        return msgSendStatus;
    }

    public void setMsgSendStatus(Integer msgSendStatus) {
        this.msgSendStatus = msgSendStatus;
    }

    public Integer getMsgSendType() {
        return msgSendType;
    }

    public void setMsgSendType(Integer msgSendType) {
        this.msgSendType = msgSendType;
    }

    public Integer getPreSendTime() {
        return preSendTime;
    }

    public void setPreSendTime(Integer preSendTime) {
        this.preSendTime = preSendTime;
    }

    public Integer getSendTime() {
        return sendTime;
    }

    public void setSendTime(Integer sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Integer getLastupdateTime() {
        return lastupdateTime;
    }

    public void setLastupdateTime(Integer lastupdateTime) {
        this.lastupdateTime = lastupdateTime;
    }

    public Integer getLastupdateUserId() {
        return lastupdateUserId;
    }

    public void setLastupdateUserId(Integer lastupdateUserId) {
        this.lastupdateUserId = lastupdateUserId;
    }

    public String getLastupdateUserName() {
        return lastupdateUserName;
    }

    public void setLastupdateUserName(String lastupdateUserName) {
        this.lastupdateUserName = lastupdateUserName;
    }

    public String getMsgDestination() {
        return msgDestination;
    }

    public void setMsgDestination(String msgDestination) {
        this.msgDestination = msgDestination;
    }
}
