package com.hyjf.am.trade.service.admin.borrow.impl;

import com.hyjf.am.resquest.admin.BorrowRecoverRequest;
import com.hyjf.am.trade.dao.model.customize.AdminBorrowRecoverCustomize;
import com.hyjf.am.trade.service.admin.borrow.AdminBorrowRecoverService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.cache.CacheUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pangchengchao
 * @version AdminBorrowRecoverServiceImpl, v0.1 2018/7/2 16:28
 */
@Service
public class AdminBorrowRecoverServiceImpl extends BaseServiceImpl implements AdminBorrowRecoverService {


    @Override
    public int countBorrowRecover(BorrowRecoverRequest request) {
        return this.borrowRecoverCustomizeMapper.countBorrowRecover(request);
    }

    @Override
    public List<AdminBorrowRecoverCustomize> selectBorrowRecoverList(BorrowRecoverRequest request) {
        List<AdminBorrowRecoverCustomize> list=this.borrowRecoverCustomizeMapper.selectBorrowRecoverList(request);
        for (AdminBorrowRecoverCustomize customize:list) {
            customize.setIsRecover(CacheUtil.getParamName("LOAN_STATUS",customize.getIsRecover()));
        }
        return list;
    }

    @Override
    public AdminBorrowRecoverCustomize sumBorrowRecoverList(BorrowRecoverRequest request) {
        return this.borrowRecoverCustomizeMapper.sumBorrowRecoverList(request);
    }
}
