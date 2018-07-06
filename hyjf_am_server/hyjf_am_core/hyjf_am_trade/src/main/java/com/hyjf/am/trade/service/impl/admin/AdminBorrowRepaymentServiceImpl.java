package com.hyjf.am.trade.service.impl.admin;

import com.hyjf.am.resquest.admin.BorrowRecoverRequest;
import com.hyjf.am.resquest.admin.BorrowRepaymentPlanRequest;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowRepaymentCustomize;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowRepaymentPlanCustomize;
import com.hyjf.am.trade.service.admin.AdminBorrowRepaymentService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pangchengchao
 * @version AdminBorrowRepaymentServiceImpl, v0.1 2018/7/4 14:33
 */
@Service
public class AdminBorrowRepaymentServiceImpl extends BaseServiceImpl implements AdminBorrowRepaymentService {


    @Override
    public int countBorrowRecover(BorrowRecoverRequest request) {
        return this.borrowRepaymentCustomizeMapper.countBorrowRepayment(request);
    }

    @Override
    public List<AdminBorrowRepaymentCustomize> selectBorrowRecoverList(BorrowRecoverRequest request) {
        return this.borrowRepaymentCustomizeMapper.selectBorrowRepaymentList(request);
    }

    @Override
    public AdminBorrowRepaymentCustomize sumBorrowRecoverList(BorrowRecoverRequest request) {
        return this.borrowRepaymentCustomizeMapper.sumBorrowRepayment(request);
    }

    @Override
    public List<AdminBorrowRepaymentPlanCustomize> exportRepayClkActBorrowRepaymentInfoList(BorrowRepaymentPlanRequest request) {
        return this.borrowRepaymentCustomizeMapper.exportRepayClkActBorrowRepaymentInfoList(request);
    }
}
