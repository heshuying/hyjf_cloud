/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize.batch;

import com.hyjf.am.trade.dao.model.customize.BorrowRepayLateCustomize;
import com.hyjf.am.trade.dao.model.customize.BorrowRepayLateSetCustomize;

import java.util.List;

/**
 * @author wangjun
 * @version BorrowRepayLateMapper, v0.1 2019/3/21 10:30
 */
public interface BorrowRepayLateMapper {
    /**
     * 查询不分期逾期的标的
     * @return
     */
    List<BorrowRepayLateCustomize> selectBorrowRepayLate();

    /**
     * 查询分期逾期的标的
     * @return
     */
    List<BorrowRepayLateCustomize> selectBorrowRepayLateByStages();

    /**
     * 更新borrow_repay待还逾期利息与逾期天数
     * @param borrowNid
     * @return
     */
    int updateBorrowRepay(String borrowNid);

    /**
     * 更新borrow_repay_plan待还逾期利息与逾期天数
     * @param borrowRepayLateSetCustomize
     * @return
     */
    int updateBorrowRepayPlan(BorrowRepayLateSetCustomize borrowRepayLateSetCustomize);
}
