/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.borrow.impl;

import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.front.borrow.BorrowRecoverService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author jijun
 * @date 20180630
 */
@Service
public class BorrowRecoverServiceImpl extends BaseServiceImpl implements BorrowRecoverService {

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

    /**
     * 根据id检索BorrowRecover
     * @param id
     * @return
     */
    @Override
    public BorrowRecover selectBorrowRecoverById(Integer id){
        return borrowRecoverMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<BorrowRecover> selectByBorrowNid(String borrowNid){
        BorrowRecoverExample example = new BorrowRecoverExample();
        BorrowRecoverExample.Criteria crt = example.createCriteria();
        crt.andBorrowNidEqualTo(borrowNid);
        List<BorrowRecover> borrowRecovers = borrowRecoverMapper.selectByExample(example);
        return borrowRecovers;
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
    
    /**
     * 根据用户id和borrowNid查询还款总表
     * @param userId,borrowNid
     * @return
     */
    @Override
    public BorrowRepay getRepayPlanByUserIdAndBorrowNid(Integer userId, String borrowNid){
        // 获取还款总表数据
        BorrowRepayExample borrowRepayExample = new BorrowRepayExample();
        BorrowRepayExample.Criteria borrowRepayCrt = borrowRepayExample.createCriteria();
        borrowRepayCrt.andUserIdEqualTo(userId);
        borrowRepayCrt.andBorrowNidEqualTo(borrowNid);
        List<BorrowRepay> borrowRepays = borrowRepayMapper.selectByExample(borrowRepayExample);
        if (borrowRepays != null && borrowRepays.size() == 1) {
            return borrowRepays.get(0);
        } else {
            return null;
        }
    }

    /**
     * 根据borrowNid，tenderNid，accedeOrderId查找放款记录
     *
     * @param tenderNid
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowRecover getRecoverDateByTenderNid(String tenderNid, String borrowNid,String accedeOrderId) {
        BorrowRecoverExample example = new BorrowRecoverExample();
        BorrowRecoverExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        cra.andNidEqualTo(tenderNid);
        if(StringUtils.isNotBlank(accedeOrderId)){
            cra.andAccedeOrderIdEqualTo(accedeOrderId);
        }
        List<BorrowRecover> borrowRecoverList = borrowRecoverMapper.selectByExample(example);
        if (null != borrowRecoverList && borrowRecoverList.size() > 0) {
            return borrowRecoverList.get(0);
        }
        return null;
    }
    @Override
    public BigDecimal selectServiceCostSum(String borrowNid) {
        return bifaBorrowRecoverCustomizeMapper.selectServiceCostSum(borrowNid);
    }


}
