package com.hyjf.am.resquest.config;

import java.io.Serializable;

/**
 * @author lisheng
 * @version WechatContentArticleRequest, v0.1 2018/7/27 10:02
 */

public class WechatContentArticleRequest implements Serializable {
    private static final long serialVersionUID = -8174562253205841602L;

    private Integer messageId;
    private Integer currentPage=1;
    private Integer pageSize=10;
    //代表当前网贷知识之前为0，之后为1
    private String offset;

    private String type;

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
    public Integer getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
    public Integer getPageSize() {
        return pageSize;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
