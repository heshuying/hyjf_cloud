/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.response.admin.ContentArticleResponse;
import com.hyjf.am.resquest.config.ContentArticleRequest;

/**
 * @author yinhui
 * @version ContentArticleClient, v0.1 2018/7/18 10:21
 */
public interface ContentArticleClient {

    ContentArticleResponse searchAction(ContentArticleRequest contentArticleRequestBean);

    ContentArticleResponse inserAction(ContentArticleRequest contentArticleRequestBean);

    ContentArticleResponse updateAction(ContentArticleRequest contentArticleRequestBean);

    ContentArticleResponse deleteById(Integer id);
}
