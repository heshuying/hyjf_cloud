package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;

public class ChinapnrExclusiveLogWithBLOBs extends ChinapnrExclusiveLog implements Serializable {
    private String content;

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