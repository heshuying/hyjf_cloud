/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.beans.request.PlatformCountRequestBean;
import com.hyjf.admin.client.PlatformCountClient;
import com.hyjf.am.response.admin.PlatformCountCustomizeResponse;
import com.hyjf.am.response.admin.promotion.PlatformUserCountCustomizeResponse;
import com.hyjf.am.vo.admin.PlatformCountCustomizeVO;
import com.hyjf.am.vo.admin.PlatformUserCountCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author fq
 * @version PlatformCountClientImpl, v0.1 2018/8/9 15:09
 */
@Service
public class PlatformCountClientImpl implements PlatformCountClient {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public PlatformCountCustomizeResponse searchAction(PlatformCountRequestBean requestBean) {
        // 获取投资信息
        PlatformCountCustomizeResponse response = restTemplate.postForObject(
                "http://AM-ADMIN/am-admin/platform_count/search_action", requestBean,
                PlatformCountCustomizeResponse.class);
        if (response != null) {
            return response;
        }
        return null;
    }
}
