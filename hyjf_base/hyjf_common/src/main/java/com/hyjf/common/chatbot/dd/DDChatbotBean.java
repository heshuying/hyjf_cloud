/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.common.chatbot.dd;

/**
 * 给钉钉发送信息内容的实体类
 * @author liubin
 * @version DDChatbotBean, v0.1 2018/7/25 14:42
 */
public class DDChatbotBean {
    //此消息类型为固定text (必选)
    private String msgtype = "text";
    //消息内容 (必选)
    private DDTextBean text;
    // @对象(非必选)
    private DDAtBean at;

    public DDChatbotBean() {
    }

    public DDChatbotBean(String content) {
        DDTextBean textBean = new DDTextBean();
        textBean.setContent(content);
        this.text = textBean;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public DDTextBean getText() {
        return text;
    }

    public void setText(DDTextBean text) {
        this.text = text;
    }

    public DDAtBean getAt() {
        return at;
    }

    public void setAt(DDAtBean at) {
        this.at = at;
    }
}
