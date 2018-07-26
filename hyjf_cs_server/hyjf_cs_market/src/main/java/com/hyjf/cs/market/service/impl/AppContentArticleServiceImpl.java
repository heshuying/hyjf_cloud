/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service.impl;

import com.hyjf.am.vo.config.ContentArticleCustomizeVO;
import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.cs.market.client.AmContentArticleClient;
import com.hyjf.cs.market.service.AppContentArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author dangzw
 * @version AppContentArticleServiceImpl, v0.1 2018/7/26 15:04
 */
@Service
public class AppContentArticleServiceImpl implements AppContentArticleService {

    @Autowired
    private AmContentArticleClient amContentArticleClient;

    /**
     * 获取网贷知识列表总条数
     * @author pcc
     * @param params
     * @return
     */
    @Override
    public Integer countContentArticleByType(Map<String, Object> params) {
        return amContentArticleClient.countContentArticleByType(params);
    }

    /**
     * 获取网贷知识列表
     * @author pcc
     * @param params
     * @param request
     * @return
     */
    @Override
    public List<ContentArticleCustomizeVO> getContentArticleListByType(Map<String, Object> params, HttpServletRequest request) {
        return amContentArticleClient.getContentArticleListByType(params, request);
    }
}
