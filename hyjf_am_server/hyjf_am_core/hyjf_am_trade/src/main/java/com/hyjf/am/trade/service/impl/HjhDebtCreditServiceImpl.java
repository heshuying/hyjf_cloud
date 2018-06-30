/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.resquest.trade.HjhDebtCreditRequest;
import com.hyjf.am.trade.dao.mapper.auto.HjhDebtCreditMapper;
import com.hyjf.am.trade.dao.mapper.auto.HjhDebtCreditTenderMapper;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCredit;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCreditExample;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCreditTender;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCreditTenderExample;
import com.hyjf.am.trade.service.HjhDebtCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version HjhDebtCreditServiceImpl, v0.1 2018/6/27 14:45
 */
@Service
public class HjhDebtCreditServiceImpl implements HjhDebtCreditService {

    @Autowired
    HjhDebtCreditMapper hjhDebtCreditMapper;
    @Autowired
    HjhDebtCreditTenderMapper hjhDebtCreditTenderMapper;

    /**
     * 判断是否存在债转未承接的项目
     * @Author liushouyi
     * @param accedeOrderId
     * @return
     */
    @Override
    public List<HjhDebtCredit> selectHjhDebtCreditListByAccedeOrderId(String accedeOrderId) {
        HjhDebtCreditExample example = new HjhDebtCreditExample();
        example.createCriteria().andPlanOrderIdEqualTo(accedeOrderId);
        return this.hjhDebtCreditMapper.selectByExample(example);
    }

    /**
     * 判断剩余待清算金额是否全部债转
     * @Author liushouyi
     * @param accedeOrderId
     * @param borrowNid
     * @return
     */
    @Override
    public List<HjhDebtCredit> selectHjhDebtCreditListByOrderIdNid(String accedeOrderId, String borrowNid) {
        HjhDebtCreditExample example = new HjhDebtCreditExample();
        example.createCriteria().andPlanOrderIdEqualTo(accedeOrderId).andBorrowNidEqualTo(borrowNid).andCreditStatusNotEqualTo(3);
        return this.hjhDebtCreditMapper.selectByExample(example);
    }


    @Override
    public List<HjhDebtCreditTender> selectHjhCreditTenderListByAssignOrderId(String assignOrderId) {
        HjhDebtCreditTenderExample example = new HjhDebtCreditTenderExample();
        HjhDebtCreditTenderExample.Criteria cra = example.createCriteria();
        cra.andAssignOrderIdEqualTo(assignOrderId);
        List<HjhDebtCreditTender> hjhCreditTenderList = this.hjhDebtCreditTenderMapper.selectByExample(example);
        return hjhCreditTenderList;
    }

    @Override
    public List<HjhDebtCredit> getHjhDebtCreditList(HjhDebtCreditRequest request) {
        HjhDebtCreditExample borrowCreditExample = new HjhDebtCreditExample();
        HjhDebtCreditExample.Criteria borrowCreditCra = borrowCreditExample.createCriteria();
        borrowCreditCra.andBorrowNidEqualTo(request.getBorrowNid());
        borrowCreditCra.andCreditNidEqualTo(request.getCreditNid());
        borrowCreditCra.andInvestOrderIdEqualTo(request.getInvestOrderId());//??
        List<HjhDebtCredit> borrowCredit = this.hjhDebtCreditMapper.selectByExample(borrowCreditExample);
        return borrowCredit;
    }
}
