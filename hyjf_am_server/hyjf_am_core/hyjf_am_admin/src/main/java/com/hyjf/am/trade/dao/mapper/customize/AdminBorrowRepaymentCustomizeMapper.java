package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.admin.BorrowRecoverRequest;
import com.hyjf.am.resquest.admin.BorrowRepaymentPlanRequest;
import com.hyjf.am.resquest.admin.BorrowRepaymentRequest;
import com.hyjf.am.trade.dao.model.customize.AdminBorrowRepaymentCustomize;
import com.hyjf.am.trade.dao.model.customize.AdminBorrowRepaymentPlanCustomize;
import com.hyjf.am.trade.dao.model.customize.AdminRepayDelayCustomize;

import java.util.List;

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
}
