/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.client.impl;

import com.hyjf.am.response.app.AppContentArticleResponse;
import com.hyjf.am.vo.app.AppContentArticleVO;
import com.hyjf.common.annotation.Cilent;
import com.hyjf.cs.market.client.AppContentArticleClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * @author dangzw
 * @version AppContentArticleClientImpl, v0.1 2018/7/30 23:35
 */
@Cilent
public class AppContentArticleClientImpl implements AppContentArticleClient {

    private static Logger logger = LoggerFactory.getLogger(AppContentArticleClientImpl.class);

    public static final String urlBase = "http://AM-CONFIG/am-config/";

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public AppContentArticleVO getContentArticleById(Integer contentArticleId) {
        String url = urlBase +"find/contentArticle/getContentArticleById/" + contentArticleId;
        AppContentArticleResponse response = restTemplate.getForEntity(url,AppContentArticleResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }
}
