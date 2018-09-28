package com.hyjf.admin.service;

import com.hyjf.am.response.admin.HjhReInvestDetailResponse;
import com.hyjf.am.resquest.admin.HjhReInvestDetailRequest;

/**
 * 产品中心 --> 汇计划 --> 资金计划 -> 复投详情
 * @Author : huanghui
 */
public interface HjhReInvestDetailService {

    /**
     * 列表
     * @param request
     * @return
     */
    HjhReInvestDetailResponse getHjhReInvestDetailList(HjhReInvestDetailRequest request);
}
