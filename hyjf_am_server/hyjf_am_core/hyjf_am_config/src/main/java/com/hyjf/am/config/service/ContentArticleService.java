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

}