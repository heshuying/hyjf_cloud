package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.ApplicantConfigService;
import com.hyjf.am.response.config.ConfigApplicantResponse;
import com.hyjf.am.resquest.config.ConfigApplicantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lisheng
 * @version ApplicantConfigServiceImpl, v0.1 2019/2/21 11:15
 */
@Service
public class ApplicantConfigServiceImpl implements ApplicantConfigService {

    @Autowired
    AmConfigClient amConfigClient;

    @Override
    public ConfigApplicantResponse updateApplicantConfigList(ConfigApplicantRequest request) {
        return amConfigClient.updateApplicantConfigList(request);
    }

    @Override
    public ConfigApplicantResponse addApplicantConfigList(ConfigApplicantRequest request) {
        return amConfigClient.addApplicantConfigList(request);
    }

    @Override
    public ConfigApplicantResponse getApplicantConfigList(ConfigApplicantRequest request) {
        return amConfigClient.getApplicantConfigList(request);
    }

    @Override
    public ConfigApplicantResponse findConfigApplicant(ConfigApplicantRequest request) {
        return amConfigClient.findConfigApplicant(request);
    }
}
