package com.hyjf.admin.client;

import com.hyjf.am.response.admin.HjhReInvestDebtResponse;
import com.hyjf.am.resquest.admin.HjhReInvestDebtRequest;

/**
 * 产品中心-汇计划-资金计划-复投承接债权
 * @Author : huanghui
 */
public interface HjhReInvestDebtClient {

    /**
     * 复投承接债权列表
     * @param request
     * @return
     */
    HjhReInvestDebtResponse hjhReInvestDebtList(HjhReInvestDebtRequest request);
}
