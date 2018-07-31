package com.hyjf.am.trade.service.admin;

import com.hyjf.am.resquest.admin.HjhReInvestDebtRequest;
import com.hyjf.am.vo.trade.hjh.HjhPlanCapitalCustomizeVO;

import java.util.List;

/**
 * 汇计划-资金计划-复投承接债权
 * @Author : huanghui
 */
public interface HjhReInvestDebtService {

    List<HjhPlanCapitalCustomizeVO> getReinvestDebtList(HjhReInvestDebtRequest request);

}
