package com.hyjf.am.trade.service.impl;

import com.hyjf.am.resquest.trade.BorrowTenderRequest;
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

    @Override
    public BorrowTender selectBorrowTender(BorrowTenderRequest request) {
        BorrowTenderExample example = new BorrowTenderExample();
        BorrowTenderExample.Criteria cra = example.createCriteria();
        cra.andNidEqualTo(request.getTenderNid());
        cra.andBorrowNidEqualTo(request.getBorrowNid());
        cra.andUserIdEqualTo(request.getTenderUserId());
        List<BorrowTender> tenderList = this.borrowTenderMapper.selectByExample(example);
        if(tenderList != null && tenderList.size() > 0){
            return tenderList.get(0);
        }
        return null;
    }
}
