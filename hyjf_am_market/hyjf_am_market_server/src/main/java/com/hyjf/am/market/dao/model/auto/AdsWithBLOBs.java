package com.hyjf.am.market.dao.model.auto;

import java.io.Serializable;

public class AdsWithBLOBs extends Ads implements Serializable {
    private String content;

    private String shareContent;

    private static final long serialVersionUID = 1L;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent == null ? null : shareContent.trim();
    }
}