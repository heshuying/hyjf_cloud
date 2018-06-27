package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.auto.BorrowTenderMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowStyle;
import com.hyjf.am.trade.dao.model.auto.BorrowStyleExample;
import com.hyjf.am.trade.dao.model.auto.BorrowTender;
import com.hyjf.am.trade.dao.model.auto.BorrowTenderExample;
import com.hyjf.am.trade.service.BorrowTenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowTenderServiceImpl implements BorrowTenderService {

    @Autowired
    private BorrowTenderMapper borrowTenderMapper;

    @Override
    public Integer getCountBorrowTenderService(Integer userId, String borrowNid) {
        BorrowTenderExample example = new BorrowTenderExample();
        BorrowTenderExample.Criteria cri = example.createCriteria();
        cri.andNidEqualTo(borrowNid);
        cri.andUserIdEqualTo(userId);
        int count = borrowTenderMapper.countByExample(example);
        return count;
    }
}
