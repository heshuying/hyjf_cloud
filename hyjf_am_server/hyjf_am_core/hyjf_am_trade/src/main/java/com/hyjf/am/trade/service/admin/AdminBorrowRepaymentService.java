package com.hyjf.am.trade.service.admin;

import com.hyjf.am.resquest.admin.BorrowRecoverRequest;
import com.hyjf.am.resquest.admin.BorrowRepaymentPlanRequest;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowRepaymentCustomize;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowRepaymentPlanCustomize;

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
}
