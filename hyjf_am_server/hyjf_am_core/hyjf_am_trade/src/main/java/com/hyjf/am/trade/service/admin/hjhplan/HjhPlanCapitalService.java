package com.hyjf.am.trade.service.admin.hjhplan;

import com.hyjf.am.resquest.admin.HjhReInvestDetailRequest;
import com.hyjf.am.vo.trade.hjh.HjhPlanCapitalCustomizeVO;

import java.util.List;

/**
 * 汇计划-资金计划
 * @Author : huanghui
 */
public interface HjhPlanCapitalService {

    List<HjhPlanCapitalCustomizeVO> getReinvestInfo(HjhReInvestDetailRequest request);

}
