package com.hyjf.am.trade.service.admin.borrow;

import com.hyjf.am.resquest.admin.BorrowRepaymentInfoListRequset;
import com.hyjf.am.trade.dao.model.customize.AdminBorrowRepaymentInfoListCustomize;

import java.util.List;

/**
 * @author pangchengchao
 * @version AdminBorrowRepaymentInfoListService, v0.1 2018/7/10 11:15
 */
public interface AdminBorrowRepaymentInfoListService {
    int countBorrowRepaymentInfoList(BorrowRepaymentInfoListRequset request);

    List<AdminBorrowRepaymentInfoListCustomize> selectBorrowRepaymentInfoListList(BorrowRepaymentInfoListRequset request);

    AdminBorrowRepaymentInfoListCustomize sumBorrowRepaymentInfoList(BorrowRepaymentInfoListRequset request);
}
