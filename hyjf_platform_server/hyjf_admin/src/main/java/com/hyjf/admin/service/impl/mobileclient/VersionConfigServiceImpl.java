package com.hyjf.admin.service.impl.mobileclient;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.service.mobileclient.VersionConfigService;
import com.hyjf.am.response.config.VersionConfigBeanResponse;
import com.hyjf.am.resquest.config.VersionConfigBeanRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lisheng
 * @version VersionConfigServiceImpl, v0.1 2018/7/14 15:53
 */
@Service
public class VersionConfigServiceImpl implements VersionConfigService {
    @Autowired
    AmAdminClient amAdminClient;
    @Override
    public VersionConfigBeanResponse getRecordList(VersionConfigBeanRequest request) {
        return amAdminClient.searchList(request);
    }

    @Override
    public VersionConfigBeanResponse getRecord(VersionConfigBeanRequest request) {
        return amAdminClient.searchInfo(request);
    }

    @Override
    public VersionConfigBeanResponse insertRecord(VersionConfigBeanRequest request) {
        return amAdminClient.insertInfo(request);
    }

    @Override
    public VersionConfigBeanResponse updateRecord(VersionConfigBeanRequest request) {
        return amAdminClient.updateInfo(request);
    }

    @Override
    public VersionConfigBeanResponse deleteRecord(VersionConfigBeanRequest request) {
        return amAdminClient.deleteInfo(request);
    }
}
