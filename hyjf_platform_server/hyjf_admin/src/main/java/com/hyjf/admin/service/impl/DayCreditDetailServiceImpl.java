package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.DayCreditDetailClient;
import com.hyjf.admin.service.DayCreditDetailService;
import com.hyjf.am.response.admin.DayCreditDetailResponse;
import com.hyjf.am.resquest.admin.DayCreditDetailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 产品中心 --> 汇计划 --> 资金计划 -> 转让详情 ServiceImpl
 * @Author : huanghui
 */
@Service
public class DayCreditDetailServiceImpl implements DayCreditDetailService {

    @Autowired
    private DayCreditDetailClient dayCreditDetailClient;

    @Override
    public DayCreditDetailResponse hjhDayCreditDetailList(DayCreditDetailRequest request) {
        DayCreditDetailResponse response = dayCreditDetailClient.hjhDayCreditDetailList(request);
        return response;
    }
}
