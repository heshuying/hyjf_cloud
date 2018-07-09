/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.client.impl;

import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.cs.market.client.AboutUsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author fuqiang
 * @version AboutUsClientImpl, v0.1 2018/7/9 10:09
 */
@Service
public class AboutUsClientImpl implements AboutUsClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ContentArticleVO getAboutUs() {
        return null;
    }

    @Override
    public String getTotalInvestmentAmount() {
        return null;
    }
}
