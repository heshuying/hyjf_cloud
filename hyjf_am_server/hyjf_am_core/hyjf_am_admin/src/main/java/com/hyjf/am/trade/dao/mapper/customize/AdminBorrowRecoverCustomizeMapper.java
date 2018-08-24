package com.hyjf.am.trade.dao.mapper.customize;

import java.util.List;

import com.hyjf.am.resquest.admin.BorrowRecoverRequest;
import com.hyjf.am.trade.dao.model.customize.AdminBorrowRecoverCustomize;

/**
 * @author pangchengchao
 * @version AdminBorrowRecoverCustomizeMapper, v0.1 2018/7/2 18:00
 */
public interface AdminBorrowRecoverCustomizeMapper {
    int countBorrowRecover(BorrowRecoverRequest request);

    List<AdminBorrowRecoverCustomize> selectBorrowRecoverList(BorrowRecoverRequest request);

    AdminBorrowRecoverCustomize sumBorrowRecoverList(BorrowRecoverRequest request);
}
