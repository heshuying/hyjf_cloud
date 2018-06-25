package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.auto.BorrowHousesMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowHouses;
import com.hyjf.am.trade.dao.model.auto.BorrowHousesExample;
import com.hyjf.am.trade.service.BorrowHousesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowHousesServiceImpl implements BorrowHousesService {


    @Autowired
    private BorrowHousesMapper borrowHousesMapper;

    @Override
    public List<BorrowHouses> getBorrowHousesByNid(String borrowNid) {
        BorrowHousesExample example = new BorrowHousesExample();
        BorrowHousesExample.Criteria cri = example.createCriteria();
        cri.andBorrowNidEqualTo(borrowNid);
        List<BorrowHouses> list = borrowHousesMapper.selectByExample(example);
        return list;
    }
}
