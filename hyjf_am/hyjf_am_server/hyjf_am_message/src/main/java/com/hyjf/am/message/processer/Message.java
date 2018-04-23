/*
 * Copyright(c) 2012-2016 JD Pharma.Ltd. All Rights Reserved.
 */
package com.hyjf.am.message.processer;

import java.io.Serializable;
import java.util.Map;

/**
 * 消息基类
 * @author renxingchen
 * @version hyjf 1.0
 * @since hyjf 1.0 2016年6月17日
 * @see 下午2:16:49
 */
public class Message implements Serializable {

    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = 1248384598497228429L;

    public Message() {
        super();
    }

    public Message(Integer userId, Map<String, String> replaceStrs) {
        super();
        this.userId = userId;
        this.replaceStrs = replaceStrs;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Map<String, String> getReplaceStrs() {
        return replaceStrs;
    }

    public void setReplaceStrs(Map<String, String> replaceStrs) {
        this.replaceStrs = replaceStrs;
    }

    private Integer userId;

    private Map<String, String> replaceStrs;

}
