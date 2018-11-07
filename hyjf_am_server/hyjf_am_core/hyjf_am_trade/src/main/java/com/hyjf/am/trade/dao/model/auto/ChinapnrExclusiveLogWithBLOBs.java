package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;

public class ChinapnrExclusiveLogWithBLOBs extends ChinapnrExclusiveLog implements Serializable {
    /**
     * 请求参数
     *
     * @mbggenerated
     */
    private String content;

    /**
     * 返回结果
     *
     * @mbggenerated
     */
    private String result;

    private static final long serialVersionUID = 1L;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }
}