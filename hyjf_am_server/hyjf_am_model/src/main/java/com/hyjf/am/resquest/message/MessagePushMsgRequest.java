/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.message;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.admin.MessagePushMsgVO;

import java.io.Serializable;
import java.util.List;

/**
 * @author yaoyong
 * @version MessagePushMsgRequest, v0.1 2018/8/15 15:20
 */
public class MessagePushMsgRequest extends BasePage implements Serializable {

    /**
     * 时间查询
     */
    private String startDateSrch;
    /**
     * 时间查询
     */
    private String endDateSrch;

    /**
     * 更新或是转发,0为更新1为转发
     */
    private String updateOrReSend;

    /**
     * 发送时间
     */
    private String messagesPreSendTimeStr;

    private String id;

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

    private String msgActionUrl1;

    private String msgActionUrl2;

    private String msgActionUrl3;

    private List<MessagePushMsgVO> recordList;

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

    public String getMsgTerminal() {
        return msgTerminal;
    }

    public void setMsgTerminal(String msgTerminal) {
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

    public String getStartDateSrch() {
        return startDateSrch;
    }

    public void setStartDateSrch(String startDateSrch) {
        this.startDateSrch = startDateSrch;
    }

    public String getEndDateSrch() {
        return endDateSrch;
    }

    public void setEndDateSrch(String endDateSrch) {
        this.endDateSrch = endDateSrch;
    }

    public String getMsgActionUrl1() {
        return msgActionUrl1;
    }

    public void setMsgActionUrl1(String msgActionUrl1) {
        this.msgActionUrl1 = msgActionUrl1;
    }

    public String getMsgActionUrl2() {
        return msgActionUrl2;
    }

    public void setMsgActionUrl2(String msgActionUrl2) {
        this.msgActionUrl2 = msgActionUrl2;
    }

    public String getMsgActionUrl3() {
        return msgActionUrl3;
    }

    public void setMsgActionUrl3(String msgActionUrl3) {
        this.msgActionUrl3 = msgActionUrl3;
    }

    public String getUpdateOrReSend() {
        return updateOrReSend;
    }

    public void setUpdateOrReSend(String updateOrReSend) {
        this.updateOrReSend = updateOrReSend;
    }

    public String getMessagesPreSendTimeStr() {
        return messagesPreSendTimeStr;
    }

    public void setMessagesPreSendTimeStr(String messagesPreSendTimeStr) {
        this.messagesPreSendTimeStr = messagesPreSendTimeStr;
    }

    public List<MessagePushMsgVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<MessagePushMsgVO> recordList) {
        this.recordList = recordList;
    }
}
