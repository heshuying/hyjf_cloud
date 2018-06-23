/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.auto.BorrowRepayMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowRepay;
import com.hyjf.am.trade.dao.model.auto.BorrowRepayExample;
import com.hyjf.am.trade.service.BorrowRepayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowRepayServiceImpl, v0.1 2018/6/23 11:34
 */
@Service
public class BorrowRepayServiceImpl implements BorrowRepayService {

    @Autowired
    private BorrowRepayMapper borrowRepayMapper;

    /**
     * 通过标的编号和还款短信提醒次数查询
     * @param borrowNid
     * @param repaySmsReminder
     * @return
     */
    @Override
    public List<BorrowRepay> selectBorrowRepayList(String borrowNid,Integer repaySmsReminder) {
        BorrowRepayExample example = new BorrowRepayExample();
        BorrowRepayExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        cra.andRepaySmsReminderEqualTo(repaySmsReminder);
        return this.borrowRepayMapper.selectByExample(example);
    }

    /**
     * 更新还款短信提醒次数
     * @param borrowRepay
     * @return
     */
    @Override
    public Integer updateBorrowRepay(BorrowRepay borrowRepay) {
        boolean result =  this.borrowRepayMapper.updateByPrimaryKeySelective(borrowRepay) >0 ? true:false;
        return result?1:0;
    }


}
