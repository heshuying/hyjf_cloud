package com.hyjf.admin.client;

import com.hyjf.am.response.admin.HjhDebtCreditReponse;
import com.hyjf.am.resquest.admin.HjhDebtCreditListRequest;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/4
 * @Description:
 */
public interface HjhDebtCreditClient {
    /**
     * 查询汇计划-转让列表
     * @param request
     * @return
     */
    HjhDebtCreditReponse queryHjhDebtCreditList(HjhDebtCreditListRequest request);

    /**
     * 查询汇计划-转让列表总数
     * @param request
     * @return
     */
    Integer queryHjhDebtCreditListTotal(HjhDebtCreditListRequest request);

}
