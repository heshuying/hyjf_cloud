/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.config;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.ContentArticleCustomizeVO;

/**
 * @author fq
 * @version ContentArticleCustomizeResponse, v0.1 2018/7/20 10:10
 */
public class ContentArticleCustomizeResponse extends Response<ContentArticleCustomizeVO> {
    /** 文章数量 */
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
