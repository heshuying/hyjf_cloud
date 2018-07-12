package com.hyjf.am.trade.service.impl;

import com.hyjf.am.resquest.admin.BorrowCreditRepayAmRequest;
import com.hyjf.am.trade.dao.mapper.customize.trade.BorrowCreditTenderCustomizeMapper;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowCreditTenderCustomize;
import com.hyjf.am.trade.service.BorrowCreditTenderService;
import com.hyjf.am.vo.admin.BorrowCreditTenderSumVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowCreditTenderServiceImpl implements BorrowCreditTenderService {

    @Autowired
    private BorrowCreditTenderCustomizeMapper borrowCreditTenderCustomizeMapper;

    @Override
    public Integer countBorrowCreditTender(BorrowCreditRepayAmRequest request) {
        return borrowCreditTenderCustomizeMapper.countBorrowCreditTender(request);
    }

    @Override
    public List<AdminBorrowCreditTenderCustomize> searchBorrowCreditTenderList(BorrowCreditRepayAmRequest request) {
        return borrowCreditTenderCustomizeMapper.searchBorrowCreditTender(request);
    }

    @Override
    public BorrowCreditTenderSumVO sumBorrowCreditTender(BorrowCreditRepayAmRequest request) {
       return borrowCreditTenderCustomizeMapper.sumBorrowCreditTender(request);
    }


}
