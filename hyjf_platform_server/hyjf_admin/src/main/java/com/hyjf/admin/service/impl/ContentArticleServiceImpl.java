/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.ContentArticleClient;
import com.hyjf.admin.service.ContentArticleService;
import com.hyjf.am.response.admin.ContentArticleResponse;
import com.hyjf.am.resquest.config.ContentArticleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yinhui
 * @version ContentArticleServiceImpl, v0.1 2018/7/18 10:11
 */
@Service
public class ContentArticleServiceImpl implements ContentArticleService {

    @Autowired
    private ContentArticleClient contentArticleClient;

    @Override
    public ContentArticleResponse searchAction(ContentArticleRequest contentArticleRequestBean) {

        return contentArticleClient.searchAction(contentArticleRequestBean);
    }

    @Override
    public ContentArticleResponse inserAction(ContentArticleRequest contentArticleRequestBean) {
        return contentArticleClient.inserAction(contentArticleRequestBean);
    }

    @Override
    public ContentArticleResponse updateAction(ContentArticleRequest contentArticleRequestBean) {
        return contentArticleClient.updateAction(contentArticleRequestBean);
    }

    @Override
    public ContentArticleResponse deleteById(Integer id) {
        return contentArticleClient.deleteById(id);
    }

}