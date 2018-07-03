package com.hyjf.am.trade.service.impl.admin;

import com.hyjf.am.resquest.admin.BorrowRecoverRequest;
import com.hyjf.am.trade.dao.mapper.customize.admin.AdminBorrowRecoverCustomizeMapper;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowRecoverCustomize;
import com.hyjf.am.trade.service.admin.AdminBorrowRecoverService;
import com.hyjf.common.cache.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pangchengchao
 * @version AdminBorrowRecoverServiceImpl, v0.1 2018/7/2 16:28
 */
@Service
public class AdminBorrowRecoverServiceImpl implements AdminBorrowRecoverService {

    @Autowired
    protected AdminBorrowRecoverCustomizeMapper borrowRecoverCustomizeMapper;

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
