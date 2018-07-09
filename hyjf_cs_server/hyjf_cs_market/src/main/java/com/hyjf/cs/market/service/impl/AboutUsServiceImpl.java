/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service.impl;

import com.hyjf.am.vo.config.TeamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.cs.market.client.AboutUsClient;
import com.hyjf.cs.market.service.AboutUsService;
import com.hyjf.cs.market.service.BaseMarketServiceImpl;

/**
 * @author fuqiang
 * @version AboutUsServiceImpl, v0.1 2018/7/9 9:52
 */
@Service
public class AboutUsServiceImpl extends BaseMarketServiceImpl implements AboutUsService {

    @Autowired
    private AboutUsClient aboutUsClient;

    @Override
    public ContentArticleVO getAboutUs() {
        return aboutUsClient.getAboutUs();
    }

    @Override
    public String getTotalInvestmentAmount() {
        return aboutUsClient.getTotalInvestmentAmount();
    }

    @Override
    public TeamVO getFounder() {
        return null;
    }
}
