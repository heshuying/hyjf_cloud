package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.auto.BorrowCarinfoMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowCarinfo;
import com.hyjf.am.trade.dao.model.auto.BorrowCarinfoExample;
import com.hyjf.am.trade.service.BorrowCarinfoService;
import com.hyjf.am.vo.trade.WebCarinfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowCarinfoServiceImpl implements BorrowCarinfoService {

    @Autowired
    private BorrowCarinfoMapper borrowCarinfoMapper;
    @Override
    public List<BorrowCarinfo> getBorrowCarinfoListByNid(String borrowNid) {
        BorrowCarinfoExample example = new BorrowCarinfoExample();
        BorrowCarinfoExample.Criteria cri = example.createCriteria();
        cri.andBorrowNidEqualTo(borrowNid);
        List<BorrowCarinfo> list = borrowCarinfoMapper.selectByExample(example);
        return list;
    }




}
