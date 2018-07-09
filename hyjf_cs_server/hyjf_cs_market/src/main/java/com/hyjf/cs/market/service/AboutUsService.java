/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service;

import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.am.vo.config.TeamVO;

/**
 * @author fuqiang
 * @version AboutUsService, v0.1 2018/7/9 9:51
 */
public interface AboutUsService extends BaseMarketService {
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
}
