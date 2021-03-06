/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.ContentArticleResponse;
import com.hyjf.am.resquest.config.ContentArticleRequest;
import com.hyjf.am.vo.admin.CategoryVO;

import java.util.List;

/**
 * 内容管理 - 文章管理
 *
 * @author yinhui
 * @version ContentArticleService, v0.1 2018/7/18 10:07
 */
public interface ContentArticleService {

    ContentArticleResponse searchAction(ContentArticleRequest contentArticleRequestBean);

    ContentArticleResponse inserAction(ContentArticleRequest contentArticleRequestBean);

    ContentArticleResponse infoAction(Integer id);

    ContentArticleResponse updateAction(ContentArticleRequest contentArticleRequestBean);

    ContentArticleResponse deleteById(Integer id);

    List<CategoryVO> putCategory();
}
