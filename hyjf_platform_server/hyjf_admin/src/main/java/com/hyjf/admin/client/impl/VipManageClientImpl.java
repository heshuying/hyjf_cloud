/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.VipManageClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.VipManageResponse;
import com.hyjf.am.resquest.admin.VipManageRequest;
import com.hyjf.am.vo.admin.VipManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
    public List<VipManageVO> searchList(VipManageRequest vipManageRequest) {
        String url = "http://AM-USER/am-user/vipManage/getUserList";
        VipManageResponse response = restTemplate.postForEntity(url,vipManageRequest,VipManageResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }
}
