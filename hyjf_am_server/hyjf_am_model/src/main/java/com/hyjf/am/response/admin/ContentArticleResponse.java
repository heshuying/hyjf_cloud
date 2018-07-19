/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.ContentArticleVO;

/**
 * @author yinhui
 * @version ContentArticleResponse, v0.1 2018/7/18 10:16
 */
public class ContentArticleResponse extends Response<ContentArticleVO> {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
