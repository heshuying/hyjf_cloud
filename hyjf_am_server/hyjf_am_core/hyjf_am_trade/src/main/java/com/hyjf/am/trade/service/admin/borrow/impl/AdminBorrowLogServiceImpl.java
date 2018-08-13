package com.hyjf.am.trade.service.admin.borrow.impl;

import com.hyjf.am.resquest.admin.BorrowLogRequset;
import com.hyjf.am.trade.dao.model.customize.BorrowLogCustomize;
import com.hyjf.am.trade.service.admin.borrow.AdminBorrowLogService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pangchengchao
 * @version AdminBorrowLogServiceImpl, v0.1 2018/7/11 10:37
 */
@Service
public class AdminBorrowLogServiceImpl extends BaseServiceImpl implements AdminBorrowLogService {
    @Override
    public int countBorrowLog(BorrowLogRequset request) {
        return this.borrowLogCustomizeMapper.countBorrowLog(request);
    }

    @Override
    public List<BorrowLogCustomize> selectBorrowLogList(BorrowLogRequset request) {
        return this.borrowLogCustomizeMapper.selectBorrowLogList(request);
    }

    @Override
    public List<BorrowLogCustomize> exportBorrowLogList(BorrowLogRequset request) {
        return this.borrowLogCustomizeMapper.exportBorrowLogList(request);
    }
}
