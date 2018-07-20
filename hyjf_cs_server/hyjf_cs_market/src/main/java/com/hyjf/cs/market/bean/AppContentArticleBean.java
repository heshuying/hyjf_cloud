/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.bean;

import java.io.Serializable;

/**
 * @author fq
 * @version AppContentArticleBean, v0.1 2018/7/20 9:38
 */
public class AppContentArticleBean extends AppBaseBean implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -8174562253205841602L;

    private Integer messageId;
    private Integer page=1;
    private Integer size=10;
    //代表当前网贷知识之前为0，之后为1
    private String offset;

    private String type;
    public Integer getPage() {
        return page;
    }
    public void setPage(Integer page) {
        this.page = page;
    }
    public Integer getSize() {
        return size;
    }
    public void setSize(Integer size) {
        this.size = size;
    }
    public Integer getMessageId() {
        return messageId;
    }
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }
    public String getOffset() {
        return offset;
    }
    public void setOffset(String offset) {
        this.offset = offset;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
