/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl.app;

import com.hyjf.am.config.dao.mapper.auto.ContentArticleMapper;
import com.hyjf.am.config.dao.model.auto.ContentArticle;
import com.hyjf.am.config.service.app.AppContentArticleService;
import com.hyjf.am.vo.config.ContentArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dangzw
 * @version AppContentArticleServiceImpl, v0.1 2018/7/31 1:11
 */
@Service
public class AppContentArticleServiceImpl implements AppContentArticleService {

    @Autowired
    ContentArticleMapper appContentArticleMapper;

    /**
     * 根据id获取网贷知识
     * @param contentArticleId
     * @return
     */
    @Override
    public ContentArticle getContentArticleById(Integer contentArticleId) {
        ContentArticle contentArticle=appContentArticleMapper.selectByPrimaryKey(contentArticleId);
        return contentArticle;
    }

}
