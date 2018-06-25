package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.auto.BorrowUsersMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowStyle;
import com.hyjf.am.trade.dao.model.auto.BorrowStyleExample;
import com.hyjf.am.trade.dao.model.auto.BorrowUsers;
import com.hyjf.am.trade.dao.model.auto.BorrowUsersExample;
import com.hyjf.am.trade.service.BorrowUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowUserServiceImpl implements BorrowUserService {


    @Autowired
    private BorrowUsersMapper borrowUsersMapper;


    @Override
    public BorrowUsers getBorrowUserByNid(String borrowNid) {
        BorrowUsersExample example = new BorrowUsersExample();
        BorrowUsersExample.Criteria cri = example.createCriteria();
        cri.andBorrowNidEqualTo(borrowNid);
        List<BorrowUsers> list = borrowUsersMapper.selectByExample(example);
        return list.get(0);
    }
}
