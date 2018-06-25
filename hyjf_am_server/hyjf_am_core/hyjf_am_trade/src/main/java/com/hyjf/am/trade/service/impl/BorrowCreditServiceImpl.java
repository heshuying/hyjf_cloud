/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.auto.BorrowCreditMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowCredit;
import com.hyjf.am.trade.dao.model.auto.BorrowCreditExample;
import com.hyjf.am.trade.service.BorrowCreditService;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowCreditServiceImpl, v0.1 2018/6/24 10:59
 */
@Service
public class BorrowCreditServiceImpl implements BorrowCreditService {

    @Autowired
    BorrowCreditMapper borrowCreditMapper;

    @Override
    public List<BorrowCredit> selectBorrowCreditList() {
        BorrowCreditExample borrowCreditExample = new BorrowCreditExample();
        BorrowCreditExample.Criteria borrowCreditCra = borrowCreditExample.createCriteria();
        borrowCreditCra.andCreditStatusEqualTo(0);
        return this.borrowCreditMapper.selectByExample(borrowCreditExample);
    }

    @Override
    public Integer updateBorrowCredit(BorrowCreditVO borrowCreditVO) {
        BorrowCredit borrowCredit = new BorrowCredit();
        BeanUtils.copyProperties(borrowCreditVO,borrowCredit);
        boolean result =  this.borrowCreditMapper.updateByPrimaryKeySelective(borrowCredit) >0 ? true:false;
        return result?1:0;
    }
}
