package com.hyjf.am.trade.service.impl;

import com.hyjf.am.resquest.admin.BorrowCreditRepayAmRequest;
import com.hyjf.am.trade.dao.mapper.auto.CreditRepayMapper;
import com.hyjf.am.trade.dao.mapper.customize.trade.BorrowCreditTenderCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.CreditRepayExample;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowCreditTenderCustomize;
import com.hyjf.am.trade.service.BorrowCreditTenderService;
import com.hyjf.am.vo.admin.BorrowCreditTenderSumVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditRepayInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BorrowCreditTenderServiceImpl implements BorrowCreditTenderService {

    @Autowired
    private BorrowCreditTenderCustomizeMapper borrowCreditTenderCustomizeMapper;

    @Autowired
    private CreditRepayMapper creditRepayMapper;

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

    /**
     * 查询汇转让还款明细count
     * @author zhangyk
     * @date 2018/7/12 14:25
     */
    @Override
    public Integer getCreditRepayListCount(BorrowCreditRepayAmRequest request) {
        CreditRepayExample creditRepayExample = new CreditRepayExample();
        CreditRepayExample.Criteria creditRepayCra = creditRepayExample.createCriteria();
        creditRepayCra.andAssignNidEqualTo(request.getAssignNid());
        Integer count = creditRepayMapper.countByExample(creditRepayExample);
        return count;
    }


    /**
     * 查询汇转让还款明细list
     * @author zhangyk
     * @date 2018/7/12 14:26
     */
    @Override
    public List<BorrowCreditRepayInfoVO> getCreditRepayList(BorrowCreditRepayAmRequest request) {
        return borrowCreditTenderCustomizeMapper.getCreditRepayList(request);
    }


    /**
     * 查询汇转让还款明细合计行
     * @author zhangyk
     * @date 2018/7/12 14:27
     */
    @Override
    public Map<String, Object> getCreditRepayListSum(BorrowCreditRepayAmRequest request) {
        return borrowCreditTenderCustomizeMapper.sumCreditRepay(request);
    }



}
