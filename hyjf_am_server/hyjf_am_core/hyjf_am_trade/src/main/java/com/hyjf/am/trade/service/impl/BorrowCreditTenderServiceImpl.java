package com.hyjf.am.trade.service.impl;

import com.hyjf.am.resquest.admin.BorrowCreditRepayAmRequest;
import com.hyjf.am.trade.dao.mapper.auto.CreditRepayMapper;
import com.hyjf.am.trade.dao.mapper.customize.trade.BorrowCreditTenderCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.CreditRepayExample;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowCreditTenderCustomize;
import com.hyjf.am.trade.service.BorrowCreditTenderService;
import com.hyjf.am.vo.admin.BorrowCreditRepaySumVO;
import com.hyjf.am.vo.admin.BorrowCreditTenderVO;
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
    public Integer countBorrowCreditRepay(BorrowCreditRepayAmRequest request) {
        return borrowCreditTenderCustomizeMapper.countBorrowCreditRepay(request);
    }

    @Override
    public List<AdminBorrowCreditTenderCustomize> searchBorrowCreditRepayList(BorrowCreditRepayAmRequest request) {
        return borrowCreditTenderCustomizeMapper.searchBorrowCreditRepay(request);
    }

    @Override
    public BorrowCreditRepaySumVO sumBorrowCreditRepay(BorrowCreditRepayAmRequest request) {
       return borrowCreditTenderCustomizeMapper.sumBorrowCreditRepay(request);
    }

    /**
     * 查询汇转让还款明细count
     * @author zhangyk
     * @date 2018/7/12 14:25
     */
    @Override
    public Integer getCreditRepayInfoListCount(BorrowCreditRepayAmRequest request) {
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
    public List<BorrowCreditRepayInfoVO> getCreditRepayInfoList(BorrowCreditRepayAmRequest request) {
        return borrowCreditTenderCustomizeMapper.getCreditRepayInfoList(request);
    }


    /**
     * 查询汇转让还款明细合计行
     * @author zhangyk
     * @date 2018/7/12 14:27
     */
    @Override
    public Map<String, Object> getCreditRepayInfoListSum(BorrowCreditRepayAmRequest request) {
        return borrowCreditTenderCustomizeMapper.sumCreditRepayInfo(request);
    }



    /**
     * 查询承接列表count
     * @author zhangyk
     * @date 2018/7/12 20:13
     */
    @Override
    public Integer getCreditTenderCount(BorrowCreditRepayAmRequest request) {
        return borrowCreditTenderCustomizeMapper.countBorrowCreditTender(request);
    }


    /**
     * 查询承接列表list
     * @author zhangyk
     * @date 2018/7/12 20:13
     */
    @Override
    public List<BorrowCreditTenderVO> getCreditTenderList(BorrowCreditRepayAmRequest request) {
        return borrowCreditTenderCustomizeMapper.searchBorrowCreditTender(request);
    }


    /**
     * 查询承接列表合计行
     * @author zhangyk
     * @date 2018/7/12 20:13
     */
    @Override
    public Map<String, Object> getCreditTenderSum(BorrowCreditRepayAmRequest request) {
        return borrowCreditTenderCustomizeMapper.sumCreditTender(request);
    }


}
