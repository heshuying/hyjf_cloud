/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.common.chatbot.dd;

/**
 * 消息内容的实体类
 * @author liubin
 * @version DDTextBean, v0.1 2018/7/25 14:48
 */
public class DDTextBean {
    // 消息内容(必选)
    private String content = "";

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
