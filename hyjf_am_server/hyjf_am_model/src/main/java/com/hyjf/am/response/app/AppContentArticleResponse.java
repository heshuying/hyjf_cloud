/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.app;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.app.AppContentArticleVO;

/**
 * @author dangzw
 * @version AppContentArticleResponse, v0.1 2018/7/30 23:59
 */
public class AppContentArticleResponse extends Response<AppContentArticleVO> {

    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = 5825234430507653546L;
    private Object details;
    private String topTitle;
    public Object getDetails() {
        return details;
    }
    public void setDetails(Object details) {
        this.details = details;
    }
    public String getTopTitle() {
        return topTitle;
    }
    public void setTopTitle(String topTitle) {
        this.topTitle = topTitle;
    }
}
