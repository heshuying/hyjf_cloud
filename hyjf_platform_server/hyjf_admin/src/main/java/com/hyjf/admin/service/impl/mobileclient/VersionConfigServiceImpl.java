package com.hyjf.admin.service.impl.mobileclient;

import com.hyjf.admin.client.VersionConfigClient;
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
    VersionConfigClient versionConfigClient;
    @Override
    public VersionConfigBeanResponse getRecordList(VersionConfigBeanRequest request) {
        return versionConfigClient.searchList(request);
    }

    @Override
    public VersionConfigBeanResponse getRecord(VersionConfigBeanRequest request) {
        return versionConfigClient.searchInfo(request);
    }

    @Override
    public VersionConfigBeanResponse insertRecord(VersionConfigBeanRequest request) {
        return versionConfigClient.insertInfo(request);
    }

    @Override
    public VersionConfigBeanResponse updateRecord(VersionConfigBeanRequest request) {
        return versionConfigClient.updateInfo(request);
    }

    @Override
    public VersionConfigBeanResponse deleteRecord(VersionConfigBeanRequest request) {
        return versionConfigClient.deleteInfo(request);
    }
}
