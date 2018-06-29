/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.BankAccountManageClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BankAccountManageCustomizeResponse;
import com.hyjf.am.resquest.admin.BankAccountManageRequest;
import com.hyjf.am.vo.admin.BankAccountManageCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageClientImpl, v0.1 2018/6/29 13:36
 */
@Service
public class BankAccountManageClientImpl implements BankAccountManageClient {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public Integer queryAccountCount(BankAccountManageRequest bankAccountManageRequest) {
        String url = "http://AM-USER/am-user/bankAccountManage/queryAccountCount";
        Integer result = restTemplate.postForEntity(url,bankAccountManageRequest,Integer.class).getBody();
        return result;
    }

    @Override
    public List<BankAccountManageCustomizeVO> queryAccountInfos(BankAccountManageRequest bankAccountManageRequest) {
        String url = "http://AM-USER/am-user/bankAccountManage/queryAccountInfos";
        BankAccountManageCustomizeResponse response = restTemplate.postForEntity(url,bankAccountManageRequest,BankAccountManageCustomizeResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }
}
