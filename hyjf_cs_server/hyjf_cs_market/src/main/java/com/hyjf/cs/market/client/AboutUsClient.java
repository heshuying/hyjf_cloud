/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.client;

import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.am.vo.config.EventVO;
import com.hyjf.am.vo.config.TeamVO;

import java.util.List;

/**
 * @author fuqiang
 * @version AboutUsClient, v0.1 2018/7/9 10:09
 */
public interface AboutUsClient {
    /**
     * 获取公司简介
     *
     * @return
     */
    ContentArticleVO getAboutUs();

    /**
     * 获取累计投资金额
     *
     * @return
     */
    String getTotalInvestmentAmount();

    /**
     * 获取创始人信息
     *
     * @return
     */
    TeamVO getFounder();

    /**
     * 获取所有公司记事记录
     *
     * @return
     */
    List<EventVO> getEventsList();

    /**
     * 检索活动列表数据
     *
     * @return
     */
    List<ContentArticleVO> aboutUsClient();

    /**
     * 根据主键ID获取Aricle
     *
     * @param id
     * @return
     */
    ContentArticleVO getNoticeInfo(Integer id);
}
