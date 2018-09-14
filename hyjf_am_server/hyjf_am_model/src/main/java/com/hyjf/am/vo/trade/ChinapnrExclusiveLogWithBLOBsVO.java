package com.hyjf.am.vo.trade;

import java.io.Serializable;

public class ChinapnrExclusiveLogWithBLOBsVO extends ChinapnrExclusiveLogVO implements Serializable {
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