/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.model.auto.HjhDebtCreditRepay;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCreditRepayExample;
import com.hyjf.am.trade.service.HjhDebtCreditRepayService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 汇计划债转还款
 * @author hesy
 * @version HjhDebtCreditRepayServiceImpl, v0.1 2018/6/26 18:15
 */
@Service
public class HjhDebtCreditRepayServiceImpl extends BaseServiceImpl implements HjhDebtCreditRepayService {

    /**
     * 查询相应的债转还款记录
     * @param borrowNid
     * @param tenderOrderId
     * @param periodNow
     * @param status
     * @return
     */
    @Override
    public List<HjhDebtCreditRepay> selectHjhDebtCreditRepay(String borrowNid, String tenderOrderId, int periodNow, int status) {
        HjhDebtCreditRepayExample example = new HjhDebtCreditRepayExample();
        HjhDebtCreditRepayExample.Criteria crt = example.createCriteria();
        crt.andBorrowNidEqualTo(borrowNid);
        crt.andInvestOrderIdEqualTo(tenderOrderId);
        crt.andRepayPeriodEqualTo(periodNow);
        crt.andRepayStatusEqualTo(status);
        crt.andDelFlagEqualTo(0);
        crt.andRepayAccountGreaterThan(BigDecimal.ZERO);
        example.setOrderByClause("id ASC");
        List<HjhDebtCreditRepay> creditRepayList = this.hjhDebtCreditRepayMapper.selectByExample(example);
        return creditRepayList;
    }

}
