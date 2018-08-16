package com.hyjf.am.trade.service.admin.borrow;

import com.hyjf.am.resquest.admin.BorrowRepaymentInfoRequset;
import com.hyjf.am.trade.dao.model.customize.AdminBorrowRepaymentInfoCustomize;

import java.util.List;

/**
 * @author pangchengchao
 * @version AdminBorrowRepaymentInfoService, v0.1 2018/7/9 9:50
 */
public interface AdminBorrowRepaymentInfoService {
    int countBorrowRecover(BorrowRepaymentInfoRequset request);

    List<AdminBorrowRepaymentInfoCustomize> selectBorrowRepaymentInfoListForView(BorrowRepaymentInfoRequset request);

    AdminBorrowRepaymentInfoCustomize sumBorrowRecoverList(BorrowRepaymentInfoRequset request);

    List<AdminBorrowRepaymentInfoCustomize> selectBorrowRepaymentInfoList(BorrowRepaymentInfoRequset request);
}
