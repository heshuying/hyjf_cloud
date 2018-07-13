package com.hyjf.am.trade.dao.mapper.customize.admin;

import com.hyjf.am.resquest.admin.BorrowLogRequset;
import com.hyjf.am.resquest.admin.BorrowRecoverRequest;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowRecoverCustomize;
import com.hyjf.am.trade.dao.model.customize.admin.BorrowLogCustomize;

import java.util.List;

/**
 * @author pangchengchao
 * @version AdminBorrowRecoverCustomizeMapper, v0.1 2018/7/2 18:00
 */
public interface AdminBorrowLogCustomizeMapper {
    int countBorrowLog(BorrowLogRequset request);

    List<BorrowLogCustomize> selectBorrowLogList(BorrowLogRequset request);

    List<BorrowLogCustomize> exportBorrowLogList(BorrowLogRequset request);
}
