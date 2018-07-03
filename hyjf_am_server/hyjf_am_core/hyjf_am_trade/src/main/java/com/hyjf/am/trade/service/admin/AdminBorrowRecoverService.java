package com.hyjf.am.trade.service.admin;

import com.hyjf.am.resquest.admin.BorrowRecoverRequest;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowRecoverCustomize;

import java.util.List;

/**
 * @author pangchengchao
 * @version AdminBorrowRecoverService, v0.1 2018/7/2 16:27
 */
public interface AdminBorrowRecoverService {
    int countBorrowRecover(BorrowRecoverRequest request);

    List<AdminBorrowRecoverCustomize> selectBorrowRecoverList(BorrowRecoverRequest request);

    AdminBorrowRecoverCustomize sumBorrowRecoverList(BorrowRecoverRequest request);
}
