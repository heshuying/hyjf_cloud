/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.admin.beans.request.PlatformCountRequestBean;
import com.hyjf.admin.client.PlatformCountClient;
import com.hyjf.admin.service.PlatformCountService;
import com.hyjf.am.response.admin.PlatformCountCustomizeResponse;

/**
 * @author fuqiang
 * @version PlatformCountServiceImpl, v0.1 2018/7/18 17:59
 */
@Service
public class PlatformCountServiceImpl implements PlatformCountService {
    @Autowired
    private PlatformCountClient platformCountClient;

    @Override
    public PlatformCountCustomizeResponse searchAction(PlatformCountRequestBean requestBean) {
        return platformCountClient.searchAction(requestBean);
    }
}
