/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.borrow.impl;

import com.hyjf.am.trade.dao.mapper.auto.BorrowRepayMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowRepay;
import com.hyjf.am.trade.dao.model.auto.BorrowRepayExample;
import com.hyjf.am.trade.service.front.borrow.BorrowRepayService;
import com.hyjf.am.vo.trade.borrow.BorrowRepayVO;
import org.springframework.beans.BeanUtils;
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
     * @param borrowRepayVO
     * @return
     */
    @Override
    public Integer updateBorrowRepay(BorrowRepayVO borrowRepayVO) {
        BorrowRepay borrowRepay = new BorrowRepay();
        BeanUtils.copyProperties(borrowRepayVO,borrowRepay);
        boolean result =  this.borrowRepayMapper.updateByPrimaryKeySelective(borrowRepay) >0 ? true:false;
        return result?1:0;
    }

    /**
     * 根据borrowNid查询还款信息
     * @author zhangyk
     * @date 2018/6/30 14:05
     */
    @Override
    public List<BorrowRepay> getBorrowRepayList(String borrowNid) {
        BorrowRepayExample example = new BorrowRepayExample();
        BorrowRepayExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        return this.borrowRepayMapper.selectByExample(example);
    }
}
