package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.VersionConfigClient;
import com.hyjf.am.response.config.VersionConfigBeanResponse;
import com.hyjf.am.resquest.config.VersionConfigBeanRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author lisheng
 * @version VersionConfigClientImpl, v0.1 2018/7/14 15:54
 */
@Service
public class VersionConfigClientImpl implements VersionConfigClient {

    @Autowired
    RestTemplate restTemplate;
    @Override
    public VersionConfigBeanResponse searchList(VersionConfigBeanRequest request) {
        VersionConfigBeanResponse response = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/appversion/getRecordList", request,
                        VersionConfigBeanResponse.class)
                .getBody();
        return response;
    }

    @Override
    public VersionConfigBeanResponse searchInfo(VersionConfigBeanRequest request) {
        VersionConfigBeanResponse response = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/appversion/infoAction", request,
                        VersionConfigBeanResponse.class)
                .getBody();
        return response;

    }

    @Override
    public VersionConfigBeanResponse insertInfo(VersionConfigBeanRequest request) {
        VersionConfigBeanResponse response = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/appversion/insertAction", request,
                        VersionConfigBeanResponse.class)
                .getBody();
        return response;

    }

    @Override
    public VersionConfigBeanResponse updateInfo(VersionConfigBeanRequest request) {
        VersionConfigBeanResponse response = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/appversion/updateAction", request,
                        VersionConfigBeanResponse.class)
                .getBody();
        return response;

    }

    @Override
    public VersionConfigBeanResponse deleteInfo(VersionConfigBeanRequest request) {
        VersionConfigBeanResponse response = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/appversion/deleteAction", request,
                        VersionConfigBeanResponse.class)
                .getBody();
        return response;

    }



}
