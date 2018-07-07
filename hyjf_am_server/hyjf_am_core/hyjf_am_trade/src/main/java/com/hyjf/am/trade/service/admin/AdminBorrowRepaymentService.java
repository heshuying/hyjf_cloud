package com.hyjf.am.trade.service.admin;

import com.hyjf.am.resquest.admin.BorrowRecoverRequest;
import com.hyjf.am.resquest.admin.BorrowRepaymentPlanRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowRepay;
import com.hyjf.am.trade.dao.model.auto.BorrowRepayPlan;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowRepaymentCustomize;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowRepaymentPlanCustomize;
import com.hyjf.am.trade.dao.model.customize.admin.AdminRepayDelayCustomize;

import java.text.ParseException;
import java.util.List;

/**
 * @author pangchengchao
 * @version AdminBorrowRepaymentService, v0.1 2018/7/4 14:33
 */
public interface AdminBorrowRepaymentService {
    int countBorrowRecover(BorrowRecoverRequest request);

    List<AdminBorrowRepaymentCustomize> selectBorrowRecoverList(BorrowRecoverRequest request);

    AdminBorrowRepaymentCustomize sumBorrowRecoverList(BorrowRecoverRequest request);

    List<AdminBorrowRepaymentPlanCustomize> exportRepayClkActBorrowRepaymentInfoList(BorrowRepaymentPlanRequest request);

    AdminRepayDelayCustomize selectBorrowInfo(String borrowNid) throws ParseException;

    BorrowRepay getBorrowRepayDelay(String borrowNid, String borrowApr, String borrowStyle) throws ParseException;

    BorrowRepayPlan getBorrowRepayPlanDelay(String borrowNid, String borrowApr, String borrowStyle) throws ParseException;

    int updateBorrowRepayDelayDays(String borrowNid, String delayDays) throws ParseException;
}
