package com.hyjf.am.trade.service.front.borrow.impl;

import com.hyjf.am.resquest.admin.BorrowCreditRepayAmRequest;
import com.hyjf.am.trade.dao.mapper.auto.CreditRepayMapper;
import com.hyjf.am.trade.dao.mapper.auto.CreditTenderMapper;
import com.hyjf.am.trade.dao.mapper.customize.BorrowCreditTenderCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.CreditRepayExample;
import com.hyjf.am.trade.dao.model.auto.CreditTenderExample;
import com.hyjf.am.trade.dao.model.customize.AdminBorrowCreditTenderCustomize;
import com.hyjf.am.trade.service.front.borrow.BorrowCreditTenderService;
import com.hyjf.am.vo.admin.BorrowCreditRepaySumVO;
import com.hyjf.am.vo.admin.BorrowCreditTenderVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditRepayInfoVO;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private CreditTenderMapper creditTenderMapper;

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
        if (StringUtils.isNotBlank(request.getAssignNid())) {
            creditRepayCra.andAssignNidEqualTo(request.getAssignNid());
        }
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


    /**
     * admin： 用户出借记录数
     * @author zhangyk
     * @date 2018/8/28 19:07
     */
    @Override
    public int getBorrowCreditTenderCount4Admin(Map<String, Object> params) {
        CreditTenderExample example = new CreditTenderExample();
        CreditTenderExample.Criteria cra = example.createCriteria();
        Object userIdObject = params.get("userId");
        if (null != userIdObject){
            String userId = String.valueOf(userIdObject);
            cra.andUserIdEqualTo(Integer.valueOf(userId));
        }
        cra.andBidNidEqualTo((String) params.get("borrowNid"));
        cra.andAssignNidEqualTo((String) params.get("assignNid"));
        cra.andCreditTenderNidEqualTo((String) params.get("creditTenderNid"));
        cra.andCreditNidEqualTo((String) params.get("creditNid"));
        int count = creditTenderMapper.countByExample(example);
        return count;
    }


    /**
     * 根据creditNId查询服务费总计
     * @author zhangyk
     * @date 2018/8/30 11:11
     */
    @Override
    public String getCreditTenderServiceFee(String creditNid) {
       String serviceFee = borrowCreditTenderCustomizeMapper.getServiceFee(creditNid);
        return serviceFee;
    }

    /**
     * 根据债转编号和出让人id查询assignPay
     * @author zhangyk
     * @date 2018/9/4 10:38
     */
    @Override
    public String getCreditTenderAssignPay(Map<String, String> params) {
        String assignPay = borrowCreditTenderCustomizeMapper.getCreditTenderAssignPay(params);

        return assignPay;
    }
}
