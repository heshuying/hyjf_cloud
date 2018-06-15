/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.auto.*;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.RtbLoansService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ${yaoy}
 * @version RtbLoansServiceImpl, v0.1 2018/6/14 11:08
 */
@Service
public class RtbLoansServiceImpl implements RtbLoansService {

    private Logger logger = LoggerFactory.getLogger(RtbLoansServiceImpl.class);

    @Autowired
    private IncreaseInterestInvestMapper increaseInterestInvestMapper;
    @Autowired
    private IncreaseInterestLoanMapper increaseInterestLoanMapper;
    @Autowired
    private IncreaseInterestRepayMapper increaseInterestRepayMapper;
    @Autowired
    private IncreaseInterestLoanDetailMapper increaseInterestLoanDetailMapper;
    @Autowired
    private IncreaseInterestRepayDetailMapper increaseInterestRepayDetailMapper;


    @Override
    public List<IncreaseInterestInvest> getBorrowTenderList(String borrowNid) {
        IncreaseInterestInvestExample example = new IncreaseInterestInvestExample();
        IncreaseInterestInvestExample.Criteria criteria = example.createCriteria();
        criteria.andBorrowNidEqualTo(borrowNid);
        criteria.andStatusEqualTo(0);
        example.setOrderByClause(" id asc ");
        List<IncreaseInterestInvest> list = this.increaseInterestInvestMapper.selectByExample(example);
        return list;
    }

    @Override
    public int updateBorrowTender(IncreaseInterestInvest increaseInterestInvest) {
        return increaseInterestInvestMapper.updateByPrimaryKeySelective(increaseInterestInvest);
    }

    @Override
    public int insertBorrowRecover(IncreaseInterestLoan increaseInterestLoan) {
        return increaseInterestLoanMapper.insertSelective(increaseInterestLoan);
    }

    @Override
    public int insertIncreaseInterestRepay(IncreaseInterestRepay increaseInterestRepay) {
        return increaseInterestRepayMapper.insertSelective(increaseInterestRepay);
    }

    @Override
    public int updateIncreaseInterestRepay(IncreaseInterestRepay increaseInterestRepay) {
        return increaseInterestRepayMapper.updateByPrimaryKeySelective(increaseInterestRepay);
    }

    @Override
    public IncreaseInterestRepay getBorrowRepay(String borrowNid) {
        IncreaseInterestRepayExample example = new IncreaseInterestRepayExample();
        IncreaseInterestRepayExample.Criteria criteria = example.createCriteria();
        criteria.andBorrowNidEqualTo(borrowNid);
        List<IncreaseInterestRepay> list = this.increaseInterestRepayMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public int IncreaseInterestLoanDetail(IncreaseInterestLoanDetail increaseInterestLoanDetail) {
        return increaseInterestLoanDetailMapper.insertSelective(increaseInterestLoanDetail);
    }

    @Override
    public IncreaseInterestRepayDetail getBorrowRepayPlan(String borrowNid, Integer period) {
        IncreaseInterestRepayDetailExample example = new IncreaseInterestRepayDetailExample();
        IncreaseInterestRepayDetailExample.Criteria criteria = example.createCriteria();
        criteria.andBorrowNidEqualTo(borrowNid);
        criteria.andRepayPeriodEqualTo(period);
        List<IncreaseInterestRepayDetail> list = this.increaseInterestRepayDetailMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public int insertIncreaseInterestRepayDetail(IncreaseInterestRepayDetail increaseInterestRepayDetail) {
        return increaseInterestRepayDetailMapper.insertSelective(increaseInterestRepayDetail);
    }

    @Override
    public int updateIncreaseInterestRepayDetail(IncreaseInterestRepayDetail increaseInterestRepayDetail) {
        return increaseInterestRepayDetailMapper.updateByPrimaryKeySelective(increaseInterestRepayDetail);
    }
}
