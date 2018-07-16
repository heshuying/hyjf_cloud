package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.ContentArticle;
import com.hyjf.am.resquest.trade.ContentArticleRequest;

import java.util.List;

public interface ContentArticleService {

    /**
     * 获取动态列表
     * @author zhangyk
     * @date 2018/7/5 9:50
     */
    List<ContentArticle> getContentArticleList(ContentArticleRequest request);

    /**
     * 获取公司简介
     *
     * @return
     */
    ContentArticle getAboutUs();


    /**
     * 获取联系我们
     * @return
     */
    public ContentArticle getContactUs();

    /**
     * 根据id获取动态
     *
     * @param id
     * @return
     */
    ContentArticle getArticleById(Integer id);
}
