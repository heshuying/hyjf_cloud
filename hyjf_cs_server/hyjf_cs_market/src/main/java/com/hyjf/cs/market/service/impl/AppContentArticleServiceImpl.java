/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service.impl;

import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.cs.market.client.AmConfigClient;
import com.hyjf.cs.market.service.AppContentArticleService;
import com.hyjf.cs.market.util.CdnUrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dangzw
 * @version AppContentArticleImpl, v0.1 2018/7/30 23:36
 */
@Service
public class AppContentArticleServiceImpl implements AppContentArticleService {

    @Autowired
    private AmConfigClient amConfigClient;

    /**
     * 根据id获取网贷知识
     * @param contentArticleId
     * @return
     */
    @Override
    public ContentArticleVO getContentArticleById(Integer contentArticleId) {
        ContentArticleVO contentArticle = amConfigClient.getContentArticleById(contentArticleId);
        String cdnUrl = CdnUrlUtil.getCdnUrl();
        String imgurl = contentArticle.getImgurl();
        contentArticle.setImgurl(cdnUrl + imgurl);
        return contentArticle;
    }
}
