/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.batch;

import com.hyjf.am.trade.dao.model.customize.BorrowRepayLateCustomize;
import com.hyjf.am.trade.dao.model.customize.BorrowRepayLateSetCustomize;
import com.hyjf.am.trade.service.BaseService;

import java.util.Set;

/**
 * @author wangjun
 * @version BorrowRepayLateService, v0.1 2019/3/21 9:53
 */
public interface BorrowRepayLateService extends BaseService {
    /**
     * 更新期逾期标的信息
     */
    void updBorrowRepayLate();

    /**
     * 更新逾期信息
     * @param borrowRepayLateCustomize
     * @param isByStages 是否分期 true:分期 false:不分期
     */
    void updateBorrowRepayLate(BorrowRepayLateCustomize borrowRepayLateCustomize, boolean isByStages);

    /**
     * 更新还款逾期标的borrow表与borrow_repay(分期是borrow_repay_plan)的相关状态
     * @param borrowNidSet
     * @param borrowRepayLateSetCustomizeSet
     * @param isByStages 是否分期 true:分期 false:不分期
     */
    void updateBorrowAndRepayStatus(Set<String> borrowNidSet, Set<BorrowRepayLateSetCustomize> borrowRepayLateSetCustomizeSet, boolean isByStages);

}
