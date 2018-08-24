/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.bean.mc;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 消息推送-模板消息统计报表
 * @author fuqiang
 * @version MessagePushTemplateStatics, v0.1 2018/6/22 9:25
 */
@Document(collection = "ht_message_push_template_statics")
public class MessagePushTemplateStatics implements Serializable {
    @Id
    private String id;

    private Integer msgId;

    private String tagId;

    private String msgTitle;

    private String msgCode;

    private String sendTime;

    private Integer iosDestinationCount;

    private Integer iosSendCount;

    private Integer iosReadCount;

    private Integer androidDestinationCount;

    private Integer androidSendCount;

    private Integer androidReadCount;

    private String createTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle == null ? null : msgTitle.trim();
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode == null ? null : msgCode.trim();
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getIosDestinationCount() {
        return iosDestinationCount;
    }

    public void setIosDestinationCount(Integer iosDestinationCount) {
        this.iosDestinationCount = iosDestinationCount;
    }

    public Integer getIosSendCount() {
        return iosSendCount;
    }

    public void setIosSendCount(Integer iosSendCount) {
        this.iosSendCount = iosSendCount;
    }

    public Integer getIosReadCount() {
        return iosReadCount;
    }

    public void setIosReadCount(Integer iosReadCount) {
        this.iosReadCount = iosReadCount;
    }

    public Integer getAndroidDestinationCount() {
        return androidDestinationCount;
    }

    public void setAndroidDestinationCount(Integer androidDestinationCount) {
        this.androidDestinationCount = androidDestinationCount;
    }

    public Integer getAndroidSendCount() {
        return androidSendCount;
    }

    public void setAndroidSendCount(Integer androidSendCount) {
        this.androidSendCount = androidSendCount;
    }

    public Integer getAndroidReadCount() {
        return androidReadCount;
    }

    public void setAndroidReadCount(Integer androidReadCount) {
        this.androidReadCount = androidReadCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
