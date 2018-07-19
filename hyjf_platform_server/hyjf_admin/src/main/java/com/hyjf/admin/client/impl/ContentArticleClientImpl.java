/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.ContentArticleClient;
import com.hyjf.am.response.admin.ContentArticleResponse;
import com.hyjf.am.resquest.config.ContentArticleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author yinhui
 * @version ContentArticleClientImpl, v0.1 2018/7/18 10:22
 */
@Service
public class ContentArticleClientImpl implements ContentArticleClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ContentArticleResponse searchAction(ContentArticleRequest contentArticleRequestBean) {
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/content/contentarticle/searchaction",
                contentArticleRequestBean, ContentArticleResponse.class).getBody();

    }

    @Override
    public ContentArticleResponse inserAction(ContentArticleRequest contentArticleRequestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentarticle/insertaction",
                contentArticleRequestBean, ContentArticleResponse.class);
    }

    @Override
    public ContentArticleResponse updateAction(ContentArticleRequest contentArticleRequestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentarticle/updateaction",
                contentArticleRequestBean, ContentArticleResponse.class);
    }

    @Override
    public ContentArticleResponse deleteById(Integer id) {
        return restTemplate.getForObject("http://AM-CONFIG/am-config/content/contentarticle/delete/" + id, ContentArticleResponse.class);
    }


}
