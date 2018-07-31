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

    /**
     * 查询文章条数
     * @param contentArticleCustomize
     * @return
     */
    Integer countContentArticle(ContentArticleRequest contentArticleCustomize);

    /**
     * (条件)查询文章条数
     * @param params
     * @return
     */
    Integer countContentArticleByType(Map<String,Object> params);

    /**
     * 通过类型查询文章列表
     * @param params
     * @return
     */
    List<ContentArticle> getContentArticleListByType(Map<String,Object> params);

    /**
     * (条件)查询文章列表(正序)
     * @param params
     * @return
     */
    ContentArticle getContentArticleUp(Map<String, Object> params);

    /**
     * (条件)查询文章列表(倒序)
     * @param params
     * @return
     */
    ContentArticle getContentArticleDown(Map<String, Object> params);
}
