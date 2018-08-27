package com.hyjf.am.trade.service.admin.borrow.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hyjf.am.resquest.admin.BorrowRepaymentInfoRequset;
import com.hyjf.am.trade.dao.model.customize.AdminBorrowRepaymentInfoCustomize;
import com.hyjf.am.trade.service.admin.borrow.AdminBorrowRepaymentInfoService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;

/**
 * @author pangchengchao
 * @version AdminBorrowRepaymentInfoServiceImpl, v0.1 2018/7/9 9:50
 */
@Service
public class AdminBorrowRepaymentInfoServiceImpl extends BaseServiceImpl implements AdminBorrowRepaymentInfoService {
    @Override
    public int countBorrowRecover(BorrowRepaymentInfoRequset request) {
        return this.borrowRepaymentInfoCustomizeMapper.countBorrowRepaymentInfo(request);
    }

    @Override
    public List<AdminBorrowRepaymentInfoCustomize> selectBorrowRepaymentInfoListForView(BorrowRepaymentInfoRequset request) {
        return this.borrowRepaymentInfoCustomizeMapper.selectBorrowRepaymentInfoListForView(request);
    }

    @Override
    public AdminBorrowRepaymentInfoCustomize sumBorrowRecoverList(BorrowRepaymentInfoRequset request) {
        return this.borrowRepaymentInfoCustomizeMapper.sumBorrowRepaymentInfo(request);
    }

    @Override
    public List<AdminBorrowRepaymentInfoCustomize> selectBorrowRepaymentInfoList(BorrowRepaymentInfoRequset request) {
        return this.borrowRepaymentInfoCustomizeMapper.selectBorrowRepaymentInfoList(request);
    }
}
