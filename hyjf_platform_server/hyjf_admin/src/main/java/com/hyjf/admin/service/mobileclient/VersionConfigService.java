package com.hyjf.admin.service.mobileclient;

import com.hyjf.am.response.config.VersionConfigBeanResponse;
import com.hyjf.am.resquest.config.VersionConfigBeanRequest;

/**
 * @author lisheng
 * @version VersionConfigService, v0.1 2018/7/14 15:52
 */

public interface VersionConfigService {
    public VersionConfigBeanResponse getRecordList(VersionConfigBeanRequest request);

    public VersionConfigBeanResponse getRecord(VersionConfigBeanRequest request);

    public VersionConfigBeanResponse insertRecord(VersionConfigBeanRequest request);

    public VersionConfigBeanResponse updateRecord(VersionConfigBeanRequest request);

    public VersionConfigBeanResponse deleteRecord(VersionConfigBeanRequest request);

}
