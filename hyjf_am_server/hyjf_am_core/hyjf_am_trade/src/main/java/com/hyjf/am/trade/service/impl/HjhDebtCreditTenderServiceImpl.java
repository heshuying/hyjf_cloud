/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.resquest.trade.HjhDebtCreditTenderRequest;
import com.hyjf.am.trade.dao.mapper.auto.HjhDebtCreditMapper;
import com.hyjf.am.trade.dao.mapper.auto.HjhDebtCreditTenderMapper;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCredit;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCreditExample;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCreditTender;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCreditTenderExample;
import com.hyjf.am.trade.service.HjhDebtCreditService;
import com.hyjf.am.trade.service.HjhDebtCreditTenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jijun
 * @date 20180629
 */
@Service
public class HjhDebtCreditTenderServiceImpl implements HjhDebtCreditTenderService {


    @Autowired
    HjhDebtCreditTenderMapper hjhDebtCreditTenderMapper;

    @Override
    public List<HjhDebtCreditTender> getHjhDebtCreditTenderList(HjhDebtCreditTenderRequest request) {
        HjhDebtCreditTenderExample creditTenderExample = new HjhDebtCreditTenderExample();
        HjhDebtCreditTenderExample.Criteria creditTenderCra = creditTenderExample.createCriteria();
        creditTenderCra.andBorrowNidEqualTo(request.getBorrowNid());
        creditTenderCra.andCreditNidEqualTo(request.getCreditNid());
        creditTenderCra.andInvestOrderIdEqualTo(request.getInvestOrderId());
        creditTenderCra.andAssignOrderIdEqualTo(request.getAssignOrderId());
		List<HjhDebtCreditTender> creditTenderList = this.hjhDebtCreditTenderMapper.selectByExample(creditTenderExample);
        return creditTenderList;
    }

    /**
     * 通过AssignOrderId查找HjhDebtCreditTender
     * @auther: hesy
     * @date: 2018/7/13
     */
    @Override
    public HjhDebtCreditTender selectHjhDebtCreditTenderByAssignOrderId(String assignOrderId) {
        HjhDebtCreditTenderExample example = new HjhDebtCreditTenderExample();
        HjhDebtCreditTenderExample.Criteria criteria = example.createCriteria();
        criteria.andAssignOrderIdEqualTo(assignOrderId);
        List<HjhDebtCreditTender> list = hjhDebtCreditTenderMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
