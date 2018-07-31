/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.client;

import com.hyjf.am.vo.app.AppContentArticleVO;

/**
 * @author dangzw
 * @version AppContentArticleClient, v0.1 2018/7/30 23:35
 */
public interface AppContentArticleClient {
    /**
     * 根据id获取网贷知识
     * @param contentArticleId
     * @return
     */
    AppContentArticleVO getContentArticleById(Integer contentArticleId);
}
