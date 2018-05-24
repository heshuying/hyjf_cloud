package com.hyjf.cs.message.bean;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author xiasq
 * @version MessagePushMsgHistory, v0.1 2018/5/4 10:48
 */

@Document(collection = "ht_message_push_history")
public class MessagePushMsgHistory implements Serializable {
    private Integer id;

    private Integer tagId;

    private String tagCode;

    private String msgCode;

    private String msgTitle;

    private String msgImageUrl;

    private String msgContent;

    private String msgTerminal;

    private Integer msgAction;

    private String msgActionUrl;

    private Integer msgDestinationType;

    private String msgDestination;

    private Integer msgUserId;

    private Integer msgSendStatus;

    private Integer msgReadCountAndroid;

    private Integer msgReadCountIos;

    private Integer msgFirstreadPlat;

    private String msgJpushId;

    private String msgJpushProId;

    private String msgJpushZybId;

    private String msgJpushZzbId;

    private String msgJpushYxbId;

    private String msgJpushZnbId;

    private String msgJpushTestId;

    private String msgRemark;

    private Integer sendTime;

    private Integer msgDestinationCountIos;

    private Integer msgDestinationCountAndroid;

    private Integer createTime;

    private Integer createUserId;

    private String createUserName;

    private Integer lastupdateTime;

    private Integer lastupdateUserId;

    private String lastupdateUserName;

    private String msgJpushZyb2Id;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        this.tagCode = tagCode == null ? null : tagCode.trim();
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode == null ? null : msgCode.trim();
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle == null ? null : msgTitle.trim();
    }

    public String getMsgImageUrl() {
        return msgImageUrl;
    }

    public void setMsgImageUrl(String msgImageUrl) {
        this.msgImageUrl = msgImageUrl == null ? null : msgImageUrl.trim();
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent == null ? null : msgContent.trim();
    }

    public String getMsgTerminal() {
        return msgTerminal;
    }

    public void setMsgTerminal(String msgTerminal) {
        this.msgTerminal = msgTerminal == null ? null : msgTerminal.trim();
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
        this.msgActionUrl = msgActionUrl == null ? null : msgActionUrl.trim();
    }

    public Integer getMsgDestinationType() {
        return msgDestinationType;
    }

    public void setMsgDestinationType(Integer msgDestinationType) {
        this.msgDestinationType = msgDestinationType;
    }

    public String getMsgDestination() {
        return msgDestination;
    }

    public void setMsgDestination(String msgDestination) {
        this.msgDestination = msgDestination == null ? null : msgDestination.trim();
    }

    public Integer getMsgUserId() {
        return msgUserId;
    }

    public void setMsgUserId(Integer msgUserId) {
        this.msgUserId = msgUserId;
    }

    public Integer getMsgSendStatus() {
        return msgSendStatus;
    }

    public void setMsgSendStatus(Integer msgSendStatus) {
        this.msgSendStatus = msgSendStatus;
    }

    public Integer getMsgReadCountAndroid() {
        return msgReadCountAndroid;
    }

    public void setMsgReadCountAndroid(Integer msgReadCountAndroid) {
        this.msgReadCountAndroid = msgReadCountAndroid;
    }

    public Integer getMsgReadCountIos() {
        return msgReadCountIos;
    }

    public void setMsgReadCountIos(Integer msgReadCountIos) {
        this.msgReadCountIos = msgReadCountIos;
    }

    public Integer getMsgFirstreadPlat() {
        return msgFirstreadPlat;
    }

    public void setMsgFirstreadPlat(Integer msgFirstreadPlat) {
        this.msgFirstreadPlat = msgFirstreadPlat;
    }

    public String getMsgJpushId() {
        return msgJpushId;
    }

    public void setMsgJpushId(String msgJpushId) {
        this.msgJpushId = msgJpushId == null ? null : msgJpushId.trim();
    }

    public String getMsgJpushProId() {
        return msgJpushProId;
    }

    public void setMsgJpushProId(String msgJpushProId) {
        this.msgJpushProId = msgJpushProId == null ? null : msgJpushProId.trim();
    }

    public String getMsgJpushZybId() {
        return msgJpushZybId;
    }

    public void setMsgJpushZybId(String msgJpushZybId) {
        this.msgJpushZybId = msgJpushZybId == null ? null : msgJpushZybId.trim();
    }

    public String getMsgJpushZzbId() {
        return msgJpushZzbId;
    }

    public void setMsgJpushZzbId(String msgJpushZzbId) {
        this.msgJpushZzbId = msgJpushZzbId == null ? null : msgJpushZzbId.trim();
    }

    public String getMsgJpushYxbId() {
        return msgJpushYxbId;
    }

    public void setMsgJpushYxbId(String msgJpushYxbId) {
        this.msgJpushYxbId = msgJpushYxbId == null ? null : msgJpushYxbId.trim();
    }

    public String getMsgJpushZnbId() {
        return msgJpushZnbId;
    }

    public void setMsgJpushZnbId(String msgJpushZnbId) {
        this.msgJpushZnbId = msgJpushZnbId == null ? null : msgJpushZnbId.trim();
    }

    public String getMsgRemark() {
        return msgRemark;
    }

    public void setMsgRemark(String msgRemark) {
        this.msgRemark = msgRemark == null ? null : msgRemark.trim();
    }

    public Integer getSendTime() {
        return sendTime;
    }

    public void setSendTime(Integer sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getMsgDestinationCountIos() {
        return msgDestinationCountIos;
    }

    public void setMsgDestinationCountIos(Integer msgDestinationCountIos) {
        this.msgDestinationCountIos = msgDestinationCountIos;
    }

    public Integer getMsgDestinationCountAndroid() {
        return msgDestinationCountAndroid;
    }

    public void setMsgDestinationCountAndroid(Integer msgDestinationCountAndroid) {
        this.msgDestinationCountAndroid = msgDestinationCountAndroid;
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
        this.createUserName = createUserName == null ? null : createUserName.trim();
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
        this.lastupdateUserName = lastupdateUserName == null ? null : lastupdateUserName.trim();
    }

    public String getMsgJpushZyb2Id() {
        return msgJpushZyb2Id;
    }

    public void setMsgJpushZyb2Id(String msgJpushZyb2Id) {
        this.msgJpushZyb2Id = msgJpushZyb2Id == null ? null : msgJpushZyb2Id.trim();
    }

    public String getMsgJpushTestId() {
        return msgJpushTestId;
    }

    public void setMsgJpushTestId(String msgJpushTestId) {
        this.msgJpushTestId = msgJpushTestId == null ? null : msgJpushTestId.trim();
    }


}