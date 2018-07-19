/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.dao.mapper.customize;

import com.hyjf.am.config.dao.model.auto.ContentArticle;
import com.hyjf.am.resquest.config.ContentArticleRequest;

import java.util.List;

/**
 * @author yinhui
 * @version ContentArticleCustomizeMapper, v0.1 2018/7/18 16:20
 */
public interface ContentArticleCustomizeMapper {

    /**
     * 根据条件查询文章列表
     * @return
     */
    List<ContentArticle> selectContentArticle(ContentArticleRequest contentArticleCustomize);

    Integer countContentArticle(ContentArticleRequest contentArticleCustomize);
}
