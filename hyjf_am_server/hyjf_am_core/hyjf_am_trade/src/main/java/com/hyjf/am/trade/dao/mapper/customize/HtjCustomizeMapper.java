package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.vo.trade.PlanInvestCustomizeVO;
import com.hyjf.am.vo.trade.PlanLockCustomizeVO;

import java.util.List;
import java.util.Map;

/**
 * @version AccountCustomizeMapper, v0.1 2018/6/19 16:28
 */
public interface HtjCustomizeMapper {


    List<PlanInvestCustomizeVO> selectInvestCreditList(Map<String, Object> param) ;

    List<PlanInvestCustomizeVO> selectCreditCreditList(Map<String, Object> param) ;

    List<PlanLockCustomizeVO>  selectUserProjectListCapital(Map<String, Object> param) ;

    String selectPlanInfoSum(String accedeOrderId);


}
