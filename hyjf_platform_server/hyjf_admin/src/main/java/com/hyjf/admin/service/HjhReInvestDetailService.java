package com.hyjf.admin.service;

import com.hyjf.am.response.admin.HjhReInvestDetailResponse;
import com.hyjf.am.resquest.admin.HjhReInvestDetailRequest;

/**
 * 产品中心 --> 汇计划 --> 资金计划 -> 复投详情
 * @Author : huanghui
 */
public interface HjhReInvestDetailService {

    /**
     * 列表总数
     * @param request
     * @return
     */
    Integer countHjhReInvestDetailTotal(HjhReInvestDetailRequest request);

    /**
     * 列表
     * @param request
     * @return
     */
    HjhReInvestDetailResponse hjhReInvestDetailList(HjhReInvestDetailRequest request);
}
