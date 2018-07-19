package com.hyjf.admin.service;

import com.hyjf.am.response.admin.HjhReInvestDebtResponse;
import com.hyjf.am.resquest.admin.HjhReInvestDebtRequest;

/**
 * @Author : huanghui
 */
public interface HjhReInvestDebtService {

    /**
     * 复投债权列表
     * @param request
     * @return
     */
    HjhReInvestDebtResponse hjhReInvestDebtList(HjhReInvestDebtRequest request);

}
