package com.hyjf.am.trade.service.front.hjh;

import com.hyjf.am.vo.trade.PlanInvestCustomizeVO;
import com.hyjf.am.vo.trade.PlanLockCustomizeVO;

import java.util.List;
import java.util.Map;

public interface HtjService {

    List<PlanInvestCustomizeVO>  selectInvestCreditList(Map<String,Object> param);

    List<PlanInvestCustomizeVO>  selectCreditCreditList(Map<String,Object> param);

    List<PlanLockCustomizeVO>  selectUserProjectListCapital(Map<String,Object> param);

    String selectPlanInfoSum(String accedeOrderId);

}
