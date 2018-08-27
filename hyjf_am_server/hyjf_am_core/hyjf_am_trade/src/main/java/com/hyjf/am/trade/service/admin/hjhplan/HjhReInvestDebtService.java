package com.hyjf.am.trade.service.admin.hjhplan;

import com.hyjf.am.resquest.admin.HjhReInvestDebtRequest;
import com.hyjf.am.vo.trade.hjh.HjhReInvestDebtVO;

import java.util.List;

/**
 * 汇计划-资金计划-复投承接债权
 * @Author : huanghui
 */
public interface HjhReInvestDebtService {

    /**
     * 资金计划 - 复投承接债权总条数
     * @param request
     * @return
     */
    Integer getReinvestDebtCount(HjhReInvestDebtRequest request);

    /**
     * 资金计划 - 复投承接债权总条数
     * @param request
     * @return
     */
    List<HjhReInvestDebtVO> getReinvestDebtList(HjhReInvestDebtRequest request);

}
