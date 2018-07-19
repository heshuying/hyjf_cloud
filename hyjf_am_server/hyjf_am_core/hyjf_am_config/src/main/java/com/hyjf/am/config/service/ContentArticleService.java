package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.ContentArticle;
import com.hyjf.am.config.dao.model.customize.HelpCategoryCustomize;
import com.hyjf.am.config.dao.model.customize.HelpContentCustomize;
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

    /**
     * 查询公告列表
     * @author zhangyk
     * @date 2018/7/16 11:39
     */
    List<ContentArticle> getNoticeList(ContentArticleRequest request);


    /**
     * 取首页公告(风险教育..)数量
     * @return
     */
    int countHomeNoticeList(String noticeType);

    /**
     * 取首页公告(风险教育..)列表
     * @return
     */
    List<ContentArticle> searchHomeNoticeList(String noticeType, int offset, int limit);


    /**
     *
     * @description: 根据help查出大分类
     */
    List<HelpCategoryCustomize> selectCategory(String group);

    /**
     *
     * @description: 	根据大类id查询子类
     */
    List<HelpCategoryCustomize> selectSunCategory(String pageName);
    /**
     *
     * @description: 根据子类id和直属于大类的id查询出所属帮助内容
     */
    List<HelpContentCustomize> selectSunContentCategory(String type, String pid);



}
