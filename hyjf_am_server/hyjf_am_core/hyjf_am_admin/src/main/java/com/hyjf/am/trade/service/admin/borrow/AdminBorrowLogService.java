package com.hyjf.am.trade.service.admin.borrow;

import com.hyjf.am.resquest.admin.BorrowLogRequset;
import com.hyjf.am.trade.dao.model.customize.BorrowLogCustomize;

import java.util.List;

/**
 * @author pangchengchao
 * @version AdminBorrowLogService, v0.1 2018/7/11 10:36
 */
public interface AdminBorrowLogService {
    int countBorrowLog(BorrowLogRequset request);

    List<BorrowLogCustomize> selectBorrowLogList(BorrowLogRequset request);

    List<BorrowLogCustomize> exportBorrowLogList(BorrowLogRequset request);

}
