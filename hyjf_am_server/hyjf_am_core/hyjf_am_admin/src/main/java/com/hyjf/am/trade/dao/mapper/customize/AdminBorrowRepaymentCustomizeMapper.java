package com.hyjf.am.trade.dao.mapper.customize;

import java.util.List;

import com.hyjf.am.resquest.admin.BorrowRepaymentPlanRequest;
import com.hyjf.am.resquest.admin.BorrowRepaymentRequest;
import com.hyjf.am.trade.dao.model.customize.AdminBorrowRepaymentCustomize;
import com.hyjf.am.trade.dao.model.customize.AdminBorrowRepaymentPlanCustomize;
import com.hyjf.am.trade.dao.model.customize.AdminRepayDelayCustomize;

/**
 * @author pangchengchao
 * @version AdminBorrowRepaymentCustomizeMapper, v0.1 2018/7/4 15:07
 */
public interface AdminBorrowRepaymentCustomizeMapper {
    int countBorrowRepayment(BorrowRepaymentRequest request);

    List<AdminBorrowRepaymentCustomize> selectBorrowRepaymentList(BorrowRepaymentRequest request);

    AdminBorrowRepaymentCustomize sumBorrowRepayment(BorrowRepaymentRequest request);

    List<AdminBorrowRepaymentPlanCustomize> exportRepayClkActBorrowRepaymentInfoList(BorrowRepaymentPlanRequest request);

    AdminRepayDelayCustomize selectBorrowInfo(AdminRepayDelayCustomize repayDelayCustomize);

    int countBorrowRepaymentPlan(BorrowRepaymentRequest request);

    List<AdminBorrowRepaymentPlanCustomize> selectBorrowRepaymentPlanList(BorrowRepaymentRequest request);

    AdminBorrowRepaymentPlanCustomize sumBorrowRepaymentPlanInfo(BorrowRepaymentRequest request);
}
