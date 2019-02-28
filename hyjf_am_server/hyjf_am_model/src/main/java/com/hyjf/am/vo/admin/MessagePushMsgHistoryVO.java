package com.hyjf.am.vo.admin;

import com.hyjf.common.util.GetDate;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

/**
 * @author lisheng
 * @version MessagePushMsgHistoryVO, v0.1 2018/8/14 20:08
 */

public class MessagePushMsgHistoryVO {
    private String id;
    @ApiModelProperty(value = "消息标签,外键,消息标签表的id")
    private Integer  tagId;
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
    @ApiModelProperty(value = "推送终端")
    private String msgTerminal;
    @ApiModelProperty(value = "后续动作,0打开APP,1打开H5页面,2指定原生页面")
    private Integer msgAction;
    @ApiModelProperty(value = "后续动作url,后续动作为0时此字段无效,1时为填写的url,2时为原生页面的url")
    private String msgActionUrl;
    @ApiModelProperty(value = "推送用户类型,0为所有人,1为单个或多个用户")
    private Integer msgDestinationType;
    @ApiModelProperty(value = "推送用户,推送用户类型为0时无效")
    private String msgDestination;
    @ApiModelProperty(value = "用户user_id")
    private Integer msgUserId;
    @ApiModelProperty(value = "发送状态,0待发送,1发送成功,2发送失败")
    private Integer msgSendStatus;
    @ApiModelProperty(value = "android阅读次数,累加,android阅读次数和ios阅读次数加起来大于1代表阅读过了")
    private Integer msgReadCountAndroid;
    @ApiModelProperty(value = "ios阅读次数,累加,android阅读次数和ios阅读次数加起来大于1代表阅读过了")
    private Integer msgReadCountIos;
    @ApiModelProperty(value = "首次阅读平台,根据程序中的平台来记")
    private Integer msgFirstreadPlat;
    @ApiModelProperty(value = "极光推送返回的ID")
    private String msgJpushId;
    @ApiModelProperty(value = "极光推送PRO返回的ID")
    private String msgJpushProId;
    @ApiModelProperty(value = "极光推送周年版返回id")
    private String msgJpushZybId;
    @ApiModelProperty(value = "极光推送悦享版返回id")
    private String msgJpushZzbId;
    @ApiModelProperty(value = "极光推送至尊版返回id")
    private String msgJpushYxbId;
    @ApiModelProperty(value = "极光推送专业版返回的ID")
    private String msgJpushZnbId;
    @ApiModelProperty(value = "发送成功失败备注")
    private String msgJpushTestId;
    @ApiModelProperty(value = "实际发送时间")
    private String msgRemark;
    @ApiModelProperty(value = "ios目标推送数")
    private Integer sendTime;
    @ApiModelProperty(value = "安卓目标推送数")
    private Integer msgDestinationCountIos;
    @ApiModelProperty(value = "")
    private Integer msgDestinationCountAndroid;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "创建人用户id")
    private Integer createUserId;
    @ApiModelProperty(value = "创建人用户名")
    private String createUserName;
    @ApiModelProperty(value = "最后修改时间")
    private String lastupdateTime;
    @ApiModelProperty(value = "极光推送专业版返回的ID2")
    private String msgJpushZyb2Id;
    @ApiModelProperty(value = "消息标签名")
    private String tagName;

    private String sendTimeT;

    private String createTimeT;

    private String lastupdateTimeT;

    private Integer lastupdateUserId;

    private String lastupdateUserName;


    public String getSendTimeT() {
        if(this.sendTime != null&& this.sendTime!=0){
            return GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(this.sendTime);
        }
        return null;
    }

    public String getCreateTimeT() {
        if(this.createTime != null&& !StringUtils.equals(this.createTime,"null")){
            Integer createTimeTh = Integer.valueOf(this.createTime);
            return GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(createTimeTh);
        }
        return null;
    }

    public String getLastupdateTimeT() {
        if(this.lastupdateTime != null&&!StringUtils.equals(this.lastupdateTime,"null")){
            Integer lastupdateTimeTh = Integer.valueOf(this.lastupdateTime);
            return GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(lastupdateTimeTh);
        }
        return null;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    private static final long serialVersionUID = 1L;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
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

    public String getLastupdateTime() {
        return lastupdateTime;
    }

    public void setLastupdateTime(String lastupdateTime) {
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
