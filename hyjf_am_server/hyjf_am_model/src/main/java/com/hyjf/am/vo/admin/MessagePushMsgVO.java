package com.hyjf.am.vo.admin;

import com.hyjf.common.util.GetDate;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author lisheng
 * @version MessagePushMsgVO, v0.1 2018/8/14 16:21
 */

public class MessagePushMsgVO {
    private static final long serialVersionUID = 1L;

    private String id;
    @ApiModelProperty(value = "消息标签,外键,消息标签表的id")
    private Integer tagId;
    @ApiModelProperty(value = "消息标签编码,消息标签表的编码")
    private String tagCode;
    @ApiModelProperty(value = "消息编码")
    private String msgCode;
    @ApiModelProperty(value = "消息标题")
    private String msgTitle;
    @ApiModelProperty(value = "图片url")
    private String msgImageUrl;
    @ApiModelProperty(value = "消息内容")
    private String msgContent;
    @ApiModelProperty(value = "消息内容")
    private String msgTerminal;
    @ApiModelProperty(value = "后续动作,0打开APP,1打开H5页面,2指定原生页面")
    private Integer msgAction;
    @ApiModelProperty(value = "后续动作url,后续动作为0时此字段无效,1时为填写的url,2时为原生页面的url")
    private String msgActionUrl;
    @ApiModelProperty(value = "推送用户类型,0为所有人,1为单个或多个用户")
    private Integer msgDestinationType;
    @ApiModelProperty(value = "发送状态,0待发送,1已发送(发送成功与否上hyjf_message_push_msg_history中查详情")
    private Integer msgSendStatus;
    @ApiModelProperty(value = "发送时间类型,0为立即发送,1为定时发送")
    private Integer msgSendType;
    @ApiModelProperty(value = "预发送时间(只针对定时发送)")
    private Integer preSendTime;
    @ApiModelProperty(value = "实际发送时间")
    private Integer sendTime;
    @ApiModelProperty(value = "推送用户,推送用户类型为0时无效")
    private String msgDestination;
    @ApiModelProperty(value = "预发送时间（格式化后）")
    private String msgPreSendTime;
    @ApiModelProperty(value = "实际发送时间（格式化后）")
    private String msgSendTime;
    @ApiModelProperty(value = "创建时间（格式化后）")
    private String msgCreateTime;

    private String updateTime;

    private Integer createTime;

    private Integer createUserId;

    private String createUserName;

    private Integer lastupdateTime;

    private Integer lastupdateUserId;

    private String lastupdateUserName;

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

    public String getMsgPreSendTime() {
        if (preSendTime != null) {
            return GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(preSendTime);
        }
        return null;
    }

    public void setMsgPreSendTime(String msgPreSendTime) {
        this.msgPreSendTime = msgPreSendTime;
    }

    public String getMsgSendTime() {
        if (sendTime != null) {
            return GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(sendTime);
        }
        return null;
    }

    public void setMsgSendTime(String msgSendTime) {
        this.msgSendTime = msgSendTime;
    }

    public String getMsgCreateTime() {
        if (createTime != null) {
            return GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(createTime);
        }
        return null;
    }

    public void setMsgCreateTime(String msgCreateTime) {
        this.msgCreateTime = msgCreateTime;
    }

    public String getUpdateTime() {
        if (lastupdateTime != null) {
            return GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(lastupdateTime);
        }
        return null;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
