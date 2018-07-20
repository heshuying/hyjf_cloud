package com.hyjf.admin.service;

import com.hyjf.am.response.admin.DayCreditDetailResponse;
import com.hyjf.am.resquest.admin.DayCreditDetailRequest;

/**
 * 产品中心 --> 汇计划 --> 资金计划 -> 转让详情 Service
 * @Author : huanghui
 */
public interface DayCreditDetailService {

    /**
     * 转让详情列表
     * @param request
     * @return
     */
    DayCreditDetailResponse hjhDayCreditDetailList(DayCreditDetailRequest request);
}
