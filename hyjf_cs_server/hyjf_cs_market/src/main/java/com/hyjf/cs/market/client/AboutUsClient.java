/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.client;

import com.hyjf.am.vo.config.ContentArticleVO;

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
}
