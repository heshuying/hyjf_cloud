package com.hyjf.am.trade.dao.mapper.customize.admin;

import com.hyjf.am.resquest.admin.BorrowRecoverRequest;
import com.hyjf.am.resquest.admin.BorrowRepaymentPlanRequest;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowRepaymentCustomize;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowRepaymentPlanCustomize;
import com.hyjf.am.trade.dao.model.customize.admin.AdminRepayDelayCustomize;

import java.util.List;

/**
 * @author pangchengchao
 * @version AdminBorrowRepaymentCustomizeMapper, v0.1 2018/7/4 15:07
 */
public interface AdminBorrowRepaymentCustomizeMapper {
    int countBorrowRepayment(BorrowRecoverRequest request);

    List<AdminBorrowRepaymentCustomize> selectBorrowRepaymentList(BorrowRecoverRequest request);

    AdminBorrowRepaymentCustomize sumBorrowRepayment(BorrowRecoverRequest request);

    List<AdminBorrowRepaymentPlanCustomize> exportRepayClkActBorrowRepaymentInfoList(BorrowRepaymentPlanRequest request);

    AdminRepayDelayCustomize selectBorrowInfo(AdminRepayDelayCustomize repayDelayCustomize);
}
