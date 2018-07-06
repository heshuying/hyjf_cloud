/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.auto.BorrowRecoverMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowRecoverPlanMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowRepayPlanMapper;
import com.hyjf.am.trade.dao.mapper.auto.TenderAgreementMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.BorrowRecoverService;
import com.hyjf.am.trade.service.BorrowRepayPlanService;
import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanVO;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author jijun
 * @date 20180630
 */
@Service
public class BorrowRecoverServiceImpl implements BorrowRecoverService {

    @Autowired
    TenderAgreementMapper tenderAgreementMapper;
    @Autowired
    BorrowRecoverMapper borrowRecoverMapper;
    @Autowired
    BorrowRecoverPlanMapper borrowRecoverPlanMapper;


    @Override
    public BorrowRecover selectBorrowRecoverByTenderNid(String tenderAgreementID) {
        TenderAgreement tenderAgreement = this.tenderAgreementMapper.selectByPrimaryKey(Integer.valueOf(tenderAgreementID));
        String tenderNid = tenderAgreement.getTenderNid();
        BorrowRecoverExample example = new BorrowRecoverExample();
        example.createCriteria().andNidEqualTo(tenderNid);
        List<BorrowRecover> borrowRecoverList = this.borrowRecoverMapper.selectByExample(example);
        if (borrowRecoverList!= null && borrowRecoverList.size() >0){
            return borrowRecoverList.get(0);
        }
        return null;
    }

    @Override
    public int updateBorrowRecover(BorrowRecover borrowRecover) {
        return this.borrowRecoverMapper.updateByPrimaryKeySelective(borrowRecover);
    }

    /**
     * 根据tenderNid 和bidNid 查询
     *
     * @param tenderNid
     * @param bidNid
     * @return
     */
    @Override
    public BorrowRecover getBorrowRecoverByTenderNidBidNid(String tenderNid, String bidNid) {
        BorrowRecoverExample borrowRecoverExample = new BorrowRecoverExample();
        BorrowRecoverExample.Criteria borrowRecoverCra = borrowRecoverExample.createCriteria();
        borrowRecoverCra.andBorrowNidEqualTo(bidNid).andNidEqualTo(tenderNid);
        List<BorrowRecover> borrowRecoverList = this.borrowRecoverMapper.selectByExample(borrowRecoverExample);
        if(CollectionUtils.isEmpty(borrowRecoverList)){
            return null;
        }
        return borrowRecoverList.get(0);
    }

    /**
     * 根据tenderNid查询
     *
     * @param tenderNid
     * @return
     */
    @Override
    public BorrowRecover getBorrowRecoverByTenderNid(String tenderNid) {
        BorrowRecoverExample borrowRecoverExample = new BorrowRecoverExample();
        BorrowRecoverExample.Criteria borrowRecoverCra = borrowRecoverExample.createCriteria();
        borrowRecoverCra.andNidEqualTo(tenderNid);
        List<BorrowRecover> borrowRecoverList = this.borrowRecoverMapper.selectByExample(borrowRecoverExample);
        if(CollectionUtils.isEmpty(borrowRecoverList)){
            return null;
        }
        return borrowRecoverList.get(0);
    }

    /**
     * 获取borrow_recover_plan更新每次还款时间
     *
     * @param bidNid
     * @param creditTenderNid
     * @param periodNow
     * @return
     */
    @Override
    public BorrowRecoverPlan getPlanByBidTidPeriod(String bidNid, String creditTenderNid, Integer periodNow) {
        BorrowRecoverPlanExample borrowRecoverPlanExample = new BorrowRecoverPlanExample();
        BorrowRecoverPlanExample.Criteria borrowRecoverPlanCra = borrowRecoverPlanExample.createCriteria();
        borrowRecoverPlanCra.andBorrowNidEqualTo(bidNid).andNidEqualTo(creditTenderNid).andRecoverPeriodEqualTo(periodNow);
        List<BorrowRecoverPlan> list = borrowRecoverPlanMapper.selectByExample(borrowRecoverPlanExample);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }


}
