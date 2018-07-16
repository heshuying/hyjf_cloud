package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.AppBorrowImageClient;
import com.hyjf.am.response.config.AppBorrowImageResponse;
import com.hyjf.am.resquest.config.AppBorrowImageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author lisheng
 * @version AppBorrowImageClientImpl, v0.1 2018/7/13 9:42
 */
@Service
public class AppBorrowImageClientImpl implements AppBorrowImageClient {
    @Autowired
    RestTemplate restTemplate;
    @Override
    public AppBorrowImageResponse searchList(AppBorrowImageRequest appBorrowImageRequest) {
        AppBorrowImageResponse response = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/appborrow/getRecordList" ,appBorrowImageRequest,
                        AppBorrowImageResponse.class)
                .getBody();
        return response;
    }

    @Override
    public AppBorrowImageResponse searchInfo(AppBorrowImageRequest appBorrowImageRequest) {
        AppBorrowImageResponse response = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/appborrow/infoAction" ,appBorrowImageRequest,
                        AppBorrowImageResponse.class)
                .getBody();
        return response;
    }

    @Override
    public AppBorrowImageResponse insertInfo(AppBorrowImageRequest appBorrowImageRequest) {
        AppBorrowImageResponse response = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/appborrow/insertAction" ,appBorrowImageRequest,
                        AppBorrowImageResponse.class)
                .getBody();
        return response;
    }

    @Override
    public AppBorrowImageResponse updateInfo(AppBorrowImageRequest appBorrowImageRequest) {
        AppBorrowImageResponse response = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/appborrow/updateAction" ,appBorrowImageRequest,
                        AppBorrowImageResponse.class)
                .getBody();
        return response;
    }

    @Override
    public AppBorrowImageResponse deleteInfo(AppBorrowImageRequest appBorrowImageRequest) {
        AppBorrowImageResponse response = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/appborrow/deleteAction" ,appBorrowImageRequest,
                        AppBorrowImageResponse.class)
                .getBody();
        return response;
    }

}
