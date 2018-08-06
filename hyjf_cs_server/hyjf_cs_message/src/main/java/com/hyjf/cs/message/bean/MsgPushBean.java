/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.bean;

import java.io.Serializable;

/**
 * @author fq
 * @version MsgPushBean, v0.1 2018/7/25 11:47
 */
public class MsgPushBean implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;


    /******************消息列表返回*********************/
    //消息标题
    private String msgTitle;
    //消息内容
    private String msgContent;
    //消息是否已读标识(0未读，1已读)
    private String readFlag;
    //消息id
    private String msgId;
    //消息的操作方式(0打开APP,1打开H5页面,2指定原生页面)
    private String msgAction;
    //消息url
    private String actionUrl;
    //消息时间
    private String time;
    //消息类型(0所有人，1个人或多个用户)
    private String msgType;
    //图片url
    private String imgUrl;

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getMsgTitle() {
        return msgTitle;
    }
    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }
    public String getMsgContent() {
        return msgContent;
    }
    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }
    public String getReadFlag() {
        return readFlag;
    }
    public void setReadFlag(String readFlag) {
        this.readFlag = readFlag;
    }
    public String getMsgId() {
        return msgId;
    }
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
    public String getMsgAction() {
        return msgAction;
    }
    public void setMsgAction(String msgAction) {
        this.msgAction = msgAction;
    }
    public String getActionUrl() {
        return actionUrl;
    }
    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }
    public String getMsgType() {
        return msgType;
    }
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
