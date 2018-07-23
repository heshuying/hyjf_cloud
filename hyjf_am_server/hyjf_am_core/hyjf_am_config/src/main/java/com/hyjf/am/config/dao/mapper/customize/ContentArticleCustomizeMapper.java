/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.dao.mapper.customize;

import com.hyjf.am.config.dao.model.auto.ContentArticle;
import com.hyjf.am.resquest.config.ContentArticleRequest;

import java.util.List;
import java.util.Map;

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

    /**
     * 查询文章条数
     * @param params
     * @return
     */
    Integer countContentArticleByType(Map<String,Object> params);

    /**
     * 查询文章列表
     * @param params
     * @return
     */
    List<ContentArticle> getContentArticleListByType(Map<String,Object> params);
}
