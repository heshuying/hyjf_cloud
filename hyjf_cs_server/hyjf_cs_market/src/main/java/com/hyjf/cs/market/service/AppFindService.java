/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service;

import com.hyjf.am.response.config.WechatContentArticleResponse;
import com.hyjf.am.resquest.config.WechatContentArticleRequest;
import com.hyjf.am.vo.config.ContentArticleCustomizeVO;
import com.hyjf.am.vo.config.ContentArticleVO;

import java.util.List;
import java.util.Map;

/**
 * @author fq
 * @version AppFindService, v0.1 2018/7/20 9:49
 */
public interface AppFindService extends BaseMarketService {

    /**
     * 查询文章条数
     * @return
     */
    Integer countContentArticleByType();

    /**
     * 查询文章列表
     * @param params
     * @return
     */
    List<ContentArticleCustomizeVO> getContentArticleListByType(Map<String,Object> params);

    /**
     * 根据文章id查找文章
     * @param contentArticleId
     * @return
     */
    ContentArticleVO getContentArticleById(Integer contentArticleId);

    /**
     * 用于展示发布的信息
     * @param noticeType
     * @param limitStart
     * @param limitEnd
     * @return
     */
    List<ContentArticleVO> searchHomeNoticeList(String noticeType,int limitStart, int limitEnd) ;


    /**
     * 根据文章类型获取文章列表
     * @param form
     * @return
     */
    WechatContentArticleResponse searchContentArticleList(WechatContentArticleRequest form);

	/**
     * 上下翻页
     * @param params
     * @param offset
     * @return
     */
    ContentArticleCustomizeVO getContentArticleFlip(Map<String, Object> params, String offset);
}
