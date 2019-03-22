package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;
import com.hyjf.am.vo.trade.repay.RepayPlanListVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author hesy
 * @version RepayManageCustomizeMapper, v0.1 2018/6/27 15:49
 */
public interface RepayManageCustomizeMapper {
    List<RepayListCustomizeVO> selectRepayList(Map<String,Object> paraMap);

    Integer selectRepayCount(Map<String,Object> paraMap);

    List<RepayListCustomizeVO> selectOrgRepayList(Map<String,Object> paraMap);

    List<RepayListCustomizeVO> searchOrgRepayedList(Map<String,Object> paraMap);

    Integer selectOrgRepayCount(Map<String,Object> paraMap);

    Integer selectOrgRepayedCount(Map<String,Object> paraMap);

    BigDecimal selectUserRepayFeeWaitTotal(Map<String,Object> paraMap);

    BigDecimal selectOrgRepayFeeWaitTotal(Map<String,Object> paraMap);

    Map<String, BigDecimal> selectRepayOrgRepaywait(Integer userId);

    /**
     * 垫付机构本期应还总额
     * @param params
     * @return
     */
    BigDecimal selectOrgRepayWaitTotal(Map<String, Object> params);

    /**
     * 借款人总借款金额
     * @param params
     * @return
     */
    BigDecimal selectUserBorrowAccountTotal(Map<String, Object> params);

    /** 用户还款计划列表*/
    List<RepayPlanListVO> selectRepayPlanList(Map<String,Object> paraMap);
}
