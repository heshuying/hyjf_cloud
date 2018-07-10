package com.hyjf.am.trade.dao.mapper.customize.admin;

import com.hyjf.am.resquest.admin.BorrowRepaymentInfoRequset;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowRepaymentInfoCustomize;

import java.util.List;

/**
 * @author pangchengchao
 * @version AdminBorrowRepaymentInfoCustomizeMapper, v0.1 2018/7/9 10:02
 */
public interface AdminBorrowRepaymentInfoCustomizeMapper {
    int countBorrowRepaymentInfo(BorrowRepaymentInfoRequset request);

    List<AdminBorrowRepaymentInfoCustomize> selectBorrowRepaymentInfoListForView(BorrowRepaymentInfoRequset request);

    AdminBorrowRepaymentInfoCustomize sumBorrowRepaymentInfo(BorrowRepaymentInfoRequset request);

    List<AdminBorrowRepaymentInfoCustomize> selectBorrowRepaymentInfoList(BorrowRepaymentInfoRequset request);
}
