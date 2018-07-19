package com.hyjf.am.trade.service.admin;

import com.hyjf.am.resquest.admin.HjhReInvestDetailRequest;
import com.hyjf.am.vo.trade.hjh.HjhPlanCapitalVO;

import java.util.List;

/**
 * 汇计划-资金计划
 * @Author : huanghui
 */
public interface HjhPlanCapitalService {

    List<HjhPlanCapitalVO> getReinvestInfo(HjhReInvestDetailRequest request);

}
