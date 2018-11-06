/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.vo.HjhUserAuthConfigCustomizeAPIVO;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.AuthConfigService;
import com.hyjf.am.response.admin.AdminAuthConfigLogResponse;
import com.hyjf.am.response.admin.AdminAuthConfigCustomizeResponse;
import com.hyjf.am.response.admin.AdminAuthConfigResponse;
import com.hyjf.am.vo.admin.HjhUserAuthConfigCustomizeVO;
import com.hyjf.am.vo.admin.HjhUserAuthConfigLogCustomizeVO;
import com.hyjf.am.vo.user.HjhUserAuthConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 授權配置service
 * jijun
 */
@Service
public class AuthConfigServiceImpl extends BaseServiceImpl implements AuthConfigService {

    @Autowired
    AmConfigClient amConfigClient;

    @Override
    public AdminAuthConfigCustomizeResponse getAuthConfigList() {
        return amConfigClient.getAuthConfigList();
    }

    @Override
    public AdminAuthConfigLogResponse getAuthConfigLogList(HjhUserAuthConfigLogCustomizeVO request) {
        return amConfigClient.getAuthConfigLogList(request);
    }

    @Override
    public AdminAuthConfigResponse getAuthConfigById(Integer id) {
        return amConfigClient.getAuthConfigById(id);
    }

    @Override
    public int updateAuthConfig(HjhUserAuthConfigVO form) {
        return amConfigClient.updateAuthConfig(form);
    }
}
