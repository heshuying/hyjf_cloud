package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.ContentArticle;
import com.hyjf.am.response.admin.ContentArticleResponse;
import com.hyjf.am.resquest.trade.ContentArticleRequest;

import java.util.List;

/**
 * 内容管理 - 文章管理 - 公司动态
 *
 * @author yinhui
 */
public interface ContentArticleService {

    /**
     * 获取动态列表
     *
     * @author zhangyk
     * @date 2018/7/5 9:50
     */
    List<ContentArticle> getContentArticleList(ContentArticleRequest request);

    /**
     * 分页
     *
     * @param request
     * @return
     */
    ContentArticleResponse getContentArticleListPage(com.hyjf.am.resquest.config.ContentArticleRequest request);

    /**
     * 获取公司简介
     *
     * @return
     */
    ContentArticle getAboutUs();


    /**
     * 获取联系我们
     *
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

    /**
     * 查询公告列表
     * @author zhangyk
     * @date 2018/7/16 11:39
     */
    List<ContentArticle> getNoticeList(ContentArticleRequest request);

    /**
     * 添加文章管理
     *
     * @param request
     */
    void insertAction(com.hyjf.am.resquest.config.ContentArticleRequest request);

    /**
     * 修改文章
     *
     * @param request
     */
    void updateAction(com.hyjf.am.resquest.config.ContentArticleRequest request);

    /**
     * 删除文章
     *
     * @param id
     */
    void delectAction(Integer id);


}
