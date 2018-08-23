/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.app;

import com.hyjf.am.config.dao.model.auto.ContentArticle;

/**
 * @author dangzw
 * @version AppContentArticleService, v0.1 2018/7/31 1:11
 */
public interface AppContentArticleService {
    /**
     * 根据id获取网贷知识
     * @param contentArticleId
     * @return
     */
    ContentArticle getContentArticleById(Integer contentArticleId);
}
