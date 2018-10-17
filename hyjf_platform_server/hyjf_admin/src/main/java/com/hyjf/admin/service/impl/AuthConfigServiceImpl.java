/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.AuthConfigService;
import com.hyjf.am.response.admin.AdminAuthConfigLogResponse;
import com.hyjf.am.response.admin.AdminAuthConfigResponse;
import com.hyjf.am.vo.admin.HjhUserAuthConfigLogCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 授權配置service
 * jijun
 */
@Service
public class AuthConfigServiceImpl extends BaseServiceImpl implements AuthConfigService {

    @Autowired
    AmConfigClient amConfigClient;

    @Override
    public AdminAuthConfigResponse getAuthConfigList() {
        return amConfigClient.getAuthConfigList();
    }

    @Override
    public AdminAuthConfigLogResponse getAuthConfigLogList(HjhUserAuthConfigLogCustomizeVO request) {
        return amConfigClient.getAuthConfigLogList(request);
    }
}
