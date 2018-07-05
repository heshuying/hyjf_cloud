/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.VipManageClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.VipDetailListResponse;
import com.hyjf.am.response.admin.VipManageResponse;
import com.hyjf.am.response.admin.VipUpdateGradeListResponse;
import com.hyjf.am.resquest.admin.VipDetailListRequest;
import com.hyjf.am.resquest.admin.VipManageRequest;
import com.hyjf.am.resquest.admin.VipUpdateGradeListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author yaoyong
 * @version VipManageClientImpl, v0.1 2018/7/2 16:24
 */
@Service
public class VipManageClientImpl implements VipManageClient {

    @Autowired
    RestTemplate restTemplate;

    /**
     * 查找VIP信息
     * @param vipManageRequest
     * @return
     */
    @Override
    public VipManageResponse searchList(VipManageRequest vipManageRequest) {
        String url = "http://AM-USER/am-user/vipManage/getUserList";
        VipManageResponse response = restTemplate.postForEntity(url,vipManageRequest,VipManageResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public VipDetailListResponse searchDetailList(VipDetailListRequest detailListRequest) {
        String url = "http://AM-USER/am-user/vipManage/vipDetailList";
        VipDetailListResponse response = restTemplate.postForEntity(url,detailListRequest,VipDetailListResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public VipUpdateGradeListResponse searchUpdateGradeList(VipUpdateGradeListRequest vgl) {
        String url = "http://AM-USER/am-user/vipManage/vipUpdateGradeList";
        VipUpdateGradeListResponse response = restTemplate.postForEntity(url,vgl,VipUpdateGradeListResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }
}
