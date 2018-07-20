package com.hyjf.admin.client;

import com.hyjf.am.response.admin.DayCreditDetailResponse;
import com.hyjf.am.resquest.admin.DayCreditDetailRequest;

/**
 * 产品中心 --> 汇计划 --> 资金计划 -> 转让详情 Client
 * @Author : huanghui
 */
public interface DayCreditDetailClient {

    /**
     * 转让详情列表
     * @param request
     * @return
     */
    DayCreditDetailResponse hjhDayCreditDetailList(DayCreditDetailRequest request);
}
