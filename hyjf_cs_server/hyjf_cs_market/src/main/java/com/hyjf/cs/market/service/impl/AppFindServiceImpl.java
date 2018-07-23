/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service.impl;

import com.hyjf.am.vo.config.ContentArticleCustomizeVO;
import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.cs.market.client.AmConfigClient;
import com.hyjf.cs.market.service.AppFindService;
import com.hyjf.cs.market.service.BaseMarketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author fq
 * @version AppFindServiceImpl, v0.1 2018/7/20 9:49
 */
@Service
public class AppFindServiceImpl extends BaseMarketServiceImpl implements AppFindService {
    @Autowired
    private AmConfigClient amConfigClient;

    @Override
    public Integer countContentArticleByType(Map<String, Object> params) {
        return amConfigClient.countContentArticleByType();
    }

    @Override
    public List<ContentArticleCustomizeVO> getContentArticleListByType(Map<String, Object> params) {
        return amConfigClient.getContentArticleListByType(params);
    }

    @Override
    public ContentArticleVO getContentArticleById(Integer contentArticleId) {
        return amConfigClient.getContentArticleById(contentArticleId);
    }
}
