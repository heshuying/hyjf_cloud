/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.ContentArticle;
import com.hyjf.am.config.dao.model.customize.ContentArticleCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author dangzw
 * @version AppContentArticleService, v0.1 2018/7/26 16:10
 */
public interface AppContentArticleService {

    /**
     * 获取网贷知识列表总条数
     * @author pcc
     * @param params
     * @return
     */
    Integer countContentArticleByType(Map<String, Object> params);
    /**
     * 获取网贷知识列表
     * @author pcc
     * @param params
     * @return
     */
    List<ContentArticleCustomize> getContentArticleFlip(Map<String, Object> params);
}
