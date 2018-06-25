package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.auto.BorrowManinfoMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowManinfo;
import com.hyjf.am.trade.dao.model.auto.BorrowManinfoExample;
import com.hyjf.am.trade.service.BorrowManinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowManinfoServiceImpl implements BorrowManinfoService {

    @Autowired
    private BorrowManinfoMapper borrowManinfoMapper;
    @Override
    public BorrowManinfo getBorrowManinfoByNid(String borrowNid) {
        BorrowManinfoExample example = new BorrowManinfoExample();
        BorrowManinfoExample.Criteria cri = example.createCriteria();
        cri.andBorrowNidEqualTo(borrowNid);
        List<BorrowManinfo> list = borrowManinfoMapper.selectByExample(example);
        return list.get(0);
    }
}
