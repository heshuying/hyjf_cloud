/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service.impl;

import com.hyjf.am.vo.app.AppContentArticleVO;
import com.hyjf.cs.market.client.AppContentArticleClient;
import com.hyjf.cs.market.service.AppContentArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dangzw
 * @version AppContentArticleImpl, v0.1 2018/7/30 23:36
 */
@Service
public class AppContentArticleImpl implements AppContentArticleService {

    @Autowired
    AppContentArticleClient appContentArticleClient;

    /**
     * 根据id获取网贷知识
     * @param contentArticleId
     * @return
     */
    @Override
    public AppContentArticleVO getContentArticleById(Integer contentArticleId) {
        return appContentArticleClient.getContentArticleById(contentArticleId);
    }
}
