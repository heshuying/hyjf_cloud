package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.ContentArticle;
import com.hyjf.am.config.dao.model.customize.HelpCategoryCustomize;
import com.hyjf.am.config.dao.model.customize.HelpContentCustomize;
import com.hyjf.am.config.dao.model.customize.ContentArticleCustomize;
import com.hyjf.am.response.admin.ContentArticleResponse;
import com.hyjf.am.resquest.config.ContentArticleRequest;



import java.util.List;
import java.util.Map;

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
    ContentArticleResponse getContentArticleListPage(ContentArticleRequest request);

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
     * 取首页公告(风险教育..)数量
     * @return
     */
    int countHomeNoticeList(String noticeType);

    /**
     * 取首页公告(风险教育..)列表
     * @return
     */
    List<ContentArticle> searchHomeNoticeList(String noticeType, Integer offset, Integer limit);


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

    /**
     * 查询文章条数
     * @return
     */
    Integer countContentArticleByType(Map<String, Object> params);

    /**
     * 查询文章列表
     * @param params
     * @return
     */
    List<ContentArticleCustomize> getContentArticleListByType(Map<String,Object> params);

    /**
     * 上下翻页
     * @param params
     * @param offset
     * @return
     */
    ContentArticleCustomize getContentArticleFlip(Map<String, Object> params, String offset);

    /**
     * 获取指定Type的文章列表
     * @param type
     * @return
     * @Author : huanghui
     */
    List<ContentArticle> getContentArticListByType(String type);
}
