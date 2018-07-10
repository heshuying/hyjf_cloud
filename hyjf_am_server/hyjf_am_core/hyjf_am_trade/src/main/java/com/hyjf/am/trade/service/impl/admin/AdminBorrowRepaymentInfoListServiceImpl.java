package com.hyjf.am.trade.service.impl.admin;

import com.hyjf.am.resquest.admin.BorrowRepaymentInfoListRequset;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowRepaymentInfoListCustomize;
import com.hyjf.am.trade.service.admin.AdminBorrowRepaymentInfoListService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pangchengchao
 * @version AdminBorrowRepaymentInfoListServiceImpl, v0.1 2018/7/10 11:15
 */
@Service
public class AdminBorrowRepaymentInfoListServiceImpl extends BaseServiceImpl implements AdminBorrowRepaymentInfoListService {
    @Override
    public int countBorrowRepaymentInfoList(BorrowRepaymentInfoListRequset request) {
        return this.borrowRepaymentInfoListCustomizeMapper.selectBorrowRepaymentInfoListList(request);
    }

    @Override
    public List<AdminBorrowRepaymentInfoListCustomize> selectBorrowRepaymentInfoListList(BorrowRepaymentInfoListRequset request) {
        return this.borrowRepaymentInfoListCustomizeMapper.countBorrowRepaymentInfoList(request);
    }

    @Override
    public AdminBorrowRepaymentInfoListCustomize sumBorrowRepaymentInfoList(BorrowRepaymentInfoListRequset request) {
        return this.borrowRepaymentInfoListCustomizeMapper.sumBorrowRepaymentInfoList(request);
    }
}
