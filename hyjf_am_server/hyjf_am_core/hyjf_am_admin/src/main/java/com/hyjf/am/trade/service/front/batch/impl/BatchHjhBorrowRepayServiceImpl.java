/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.batch.impl;

import com.hyjf.am.trade.dao.mapper.auto.*;
import com.hyjf.am.trade.dao.mapper.customize.BatchHjhBorrowRepayCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.front.batch.BatchHjhBorrowRepayService;
import com.hyjf.am.vo.trade.CalculateInvestInterestVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhRepayVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BatchHjhBorrowRepayServiceImpl, v0.1 2018/6/27 9:21
 */
@Service
public class BatchHjhBorrowRepayServiceImpl implements BatchHjhBorrowRepayService {

    @Autowired
    BorrowTenderMapper borrowTenderMapper;

    @Autowired
    HjhAccedeMapper hjhAccedeMapper;

    @Autowired
    HjhPlanMapper hjhPlanMapper;

    @Autowired
    BorrowRecoverMapper borrowRecoverMapper;

    @Autowired
    HjhRepayMapper hjhRepayMapper;

    @Autowired
    CalculateInvestInterestMapper calculateInvestInterestMapper;

    @Autowired
    BatchHjhBorrowRepayCustomizeMapper batchHjhBorrowRepayCustomizeMapper;

    @Override
    public List<BorrowTender> selectBorrowTenderListByAccedeOrderId(String accedeOrderId) {
        BorrowTenderExample example = new BorrowTenderExample();
        example.createCriteria().andAccedeOrderIdEqualTo(accedeOrderId).andTenderTypeEqualTo(0);
        return this.borrowTenderMapper.selectByExample(example);
    }

    @Override
    public List<HjhAccede> selectHjhAccedeListByOrderId(String accedeOrderId) {
        HjhAccedeExample accedeExample = new HjhAccedeExample();
        accedeExample.createCriteria().andAccedeOrderIdEqualTo(accedeOrderId);
        return this.hjhAccedeMapper.selectByExample(accedeExample);
    }

    @Override
    public Integer updateHjhBorrowRepayInterest(HjhAccedeVO hjhAccedeVO) {
        HjhAccede hjhAccede = new HjhAccede();
        BeanUtils.copyProperties(hjhAccedeVO,hjhAccede);
        boolean result =  this.batchHjhBorrowRepayCustomizeMapper.updateHjhBorrowRepayInterest(hjhAccede) >0 ? true:false;
        return result?1:0;
    }

    @Override
    public List<HjhPlan> selectHjhPlanListByPlanNid(String planNid) {
        HjhPlanExample example = new HjhPlanExample();
        example.createCriteria().andPlanNidEqualTo(planNid);
        return this.hjhPlanMapper.selectByExample(example);
    }

    @Override
    public List<HjhAccede> selectHjhAccedeListById(Integer id) {
        HjhAccedeExample example = new HjhAccedeExample();
        example.createCriteria().andIdEqualTo(id);
        return this.hjhAccedeMapper.selectByExample(example );
    }

    @Override
    public Integer updateHjhAccedeByPrimaryKey(HjhAccedeVO hjhAccedeVO) {
        HjhAccede hjhAccede = new HjhAccede();
        BeanUtils.copyProperties(hjhAccedeVO,hjhAccede);
        boolean result =  this.hjhAccedeMapper.updateByPrimaryKey(hjhAccede) >0 ? true:false;
        return result?1:0;
    }

    @Override
    public List<BorrowRecover> selectBorrowRecoverListByAccedeOrderId(String accedeOrderId) {
        BorrowRecoverExample example = new BorrowRecoverExample();
        example.createCriteria().andAccedeOrderIdEqualTo(accedeOrderId);
        return this.borrowRecoverMapper.selectByExample(example);
    }

    @Override
    public List<HjhRepay> selectHjhRepayListByAccedeOrderId(String accedeOrderId) {
        HjhRepayExample repayExample = new HjhRepayExample();
        repayExample.createCriteria().andAccedeOrderIdEqualTo(accedeOrderId);
        return hjhRepayMapper.selectByExample(repayExample);
    }

    @Override
    public Integer insertHjhBorrowRepay(HjhRepayVO hjhRepayVO) {
        HjhRepay hjhRepay = new HjhRepay();
        BeanUtils.copyProperties(hjhRepayVO,hjhRepay);
        boolean result =  this.hjhRepayMapper.insertSelective(hjhRepay) >0 ? true:false;
        return result?1:0;
    }

    @Override
    public Integer updateBankTotalForLockPlan(AccountVO accountVO) {
        Account account = new Account();
        BeanUtils.copyProperties(accountVO,account);
        boolean result =  this.batchHjhBorrowRepayCustomizeMapper.updateBankTotalForLockPlan(account) >0 ? true:false;
        return result?1:0;
    }

    @Override
    public Integer updateHjhRepayByPrimaryKey(HjhRepayVO hjhRepayVO) {
        HjhRepay hjhRepay = new HjhRepay();
        BeanUtils.copyProperties(hjhRepayVO,hjhRepay);
        boolean result =  hjhRepayMapper.updateByPrimaryKey(hjhRepay) >0 ? true:false;
        return result?1:0;
    }

    @Override
    public HjhRepay selectHjhRepayListById(Integer id) {
        return hjhRepayMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CalculateInvestInterest> selectCalculateInvestInterest() {
        return this.calculateInvestInterestMapper.selectByExample(new CalculateInvestInterestExample());
    }

    @Override
    public Integer updateCalculateInvestByPrimaryKey(CalculateInvestInterestVO calculateInvestInterestVO) {
        CalculateInvestInterest calculateInvestInterest = new CalculateInvestInterest();
        BeanUtils.copyProperties(calculateInvestInterestVO,calculateInvestInterest);
        boolean result =  this.batchHjhBorrowRepayCustomizeMapper.updateCalculateInvestByPrimaryKey(calculateInvestInterest) >0 ? true:false;
        return result?1:0;
    }


}
