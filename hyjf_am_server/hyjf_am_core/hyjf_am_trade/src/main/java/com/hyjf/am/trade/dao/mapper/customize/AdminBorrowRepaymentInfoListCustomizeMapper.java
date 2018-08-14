package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.admin.BorrowRepaymentInfoListRequset;
import com.hyjf.am.trade.dao.model.customize.AdminBorrowRepaymentInfoListCustomize;

import java.util.List;

/**
 * @author pangchengchao
 * @version AdminBorrowRepaymentInfoListCustomizeMapper, v0.1 2018/7/10 11:26
 */
public interface AdminBorrowRepaymentInfoListCustomizeMapper {
    List<AdminBorrowRepaymentInfoListCustomize> selectBorrowRepaymentInfoListList(BorrowRepaymentInfoListRequset request);

    int  countBorrowRepaymentInfoList(BorrowRepaymentInfoListRequset request);

    AdminBorrowRepaymentInfoListCustomize sumBorrowRepaymentInfoList(BorrowRepaymentInfoListRequset request);
}
