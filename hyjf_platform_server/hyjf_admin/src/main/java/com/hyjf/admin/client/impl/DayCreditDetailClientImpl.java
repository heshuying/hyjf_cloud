package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.DayCreditDetailClient;
import com.hyjf.am.response.admin.DayCreditDetailResponse;
import com.hyjf.am.resquest.admin.DayCreditDetailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 产品中心 --> 汇计划 --> 资金计划 -> 转让详情 ClientImpl
 * @Author : huanghui
 */
@Service
public class DayCreditDetailClientImpl implements DayCreditDetailClient {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 转让详情列表
     * @param request
     * @return
     */
    @Override
    public DayCreditDetailResponse hjhDayCreditDetailList(DayCreditDetailRequest request) {
        DayCreditDetailResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/hjhDayCreditDetail/hjhDayCreditList", request, DayCreditDetailResponse.class).getBody();

        if (response != null){
            return response;
        }
        return null;
    }
}
