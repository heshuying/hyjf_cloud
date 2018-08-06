/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.borrow.impl;

import com.hyjf.am.trade.dao.mapper.auto.BorrowTenderCpnMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowTenderCpn;
import com.hyjf.am.trade.dao.model.auto.BorrowTenderCpnExample;
import com.hyjf.am.trade.service.front.borrow.BorrowTenderCpnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yaoy
 * @version BorrowTenderCpnServiceImpl, v0.1 2018/6/26 10:38
 */
@Service
public class BorrowTenderCpnServiceImpl implements BorrowTenderCpnService {

    @Autowired
    BorrowTenderCpnMapper borrowTenderCpnMapper;
    @Override
    @Transactional
    public int updateByPrimaryKeySelective(BorrowTenderCpn borrowTenderCpn) {
        int result = borrowTenderCpnMapper.updateByPrimaryKeySelective(borrowTenderCpn);
        if (result > 0) {
            return result;
        }
        return 0;
    }

    @Override
    public BorrowTenderCpn getCouponTenderInfo(String couponTenderNid) {
        BorrowTenderCpnExample example = new BorrowTenderCpnExample();
        example.createCriteria().andNidEqualTo(couponTenderNid);
        List<BorrowTenderCpn> btList = this.borrowTenderCpnMapper.selectByExample(example);
        if (btList != null && btList.size() > 0) {
            return btList.get(0);
        }
        return null;
    }
}
