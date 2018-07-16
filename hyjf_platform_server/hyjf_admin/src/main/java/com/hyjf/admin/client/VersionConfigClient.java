package com.hyjf.admin.client;

import com.hyjf.am.response.config.VersionConfigBeanResponse;
import com.hyjf.am.resquest.config.VersionConfigBeanRequest;

/**
 * @author lisheng
 * @version VersionConfigClient, v0.1 2018/7/14 15:54
 */

public interface VersionConfigClient {
    public VersionConfigBeanResponse searchList(VersionConfigBeanRequest request);

    public VersionConfigBeanResponse searchInfo(VersionConfigBeanRequest request);

    public VersionConfigBeanResponse insertInfo(VersionConfigBeanRequest request);

    public VersionConfigBeanResponse updateInfo(VersionConfigBeanRequest request);

    public VersionConfigBeanResponse deleteInfo(VersionConfigBeanRequest request);

}
