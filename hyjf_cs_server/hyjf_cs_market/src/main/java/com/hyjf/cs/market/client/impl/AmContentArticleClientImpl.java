/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.client.impl;

import com.hyjf.am.response.admin.ContentArticleResponse;
import com.hyjf.am.response.config.ContentArticleCustomizeResponse;
import com.hyjf.am.resquest.trade.ContentArticleRequest;
import com.hyjf.am.vo.config.ContentArticleCustomizeVO;
import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.common.annotation.Cilent;
import com.hyjf.cs.market.client.AmContentArticleClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author dangzw
 * @version AmContentArticleClientImpl, v0.1 2018/7/26 15:06
 */
@Cilent
public class AmContentArticleClientImpl implements AmContentArticleClient {

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public Integer countContentArticleByType(Map<String, Object> params) {
        ContentArticleResponse response = restTemplate.postForObject(
                "http://AM-CONFIG/am-config/am-config/find/contentArticle/getContentArticleListByType", params,
                ContentArticleResponse.class);
        if (response != null) {
            return response.getCount();
        }
        return null;
    }

    @Override
    public List<ContentArticleCustomizeVO> getContentArticleListByType(Map<String, Object> params, HttpServletRequest request) {
        ContentArticleCustomizeResponse response = restTemplate.postForObject(
                "http://AM-CONFIG/am-config/am-config/find/contentArticle/getContentArticleFlip", params,
                ContentArticleCustomizeResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
}
