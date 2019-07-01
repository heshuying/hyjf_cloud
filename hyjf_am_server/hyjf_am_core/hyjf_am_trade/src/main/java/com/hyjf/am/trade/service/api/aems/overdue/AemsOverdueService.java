package com.hyjf.am.trade.service.api.aems.overdue;

import com.hyjf.am.trade.dao.model.auto.BorrowRepayPlan;
import com.hyjf.am.vo.trade.AemsOverdueVO;

import java.util.List;
import java.util.Map;

/**
 * @author xiehuili on 2019/3/12.
 */
public interface AemsOverdueService {

    List<BorrowRepayPlan> selectBorrowRepayPlanList(String borrowNid, Integer repayPeriods);

    /**
     * 查询borrowRepay 逾期数据
     * @param params
     * @return
     */
    List<AemsOverdueVO> selectRepayOverdue(Map<String, Object> params);
    /**
     * 查询多期 逾期数据
     * @param params
     * @return
     */
    List<AemsOverdueVO> selectRepayPlanOverdue(Map<String, Object> params);
}
