package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.vo.trade.borrow.BorrowCarinfoVO;
import com.hyjf.cs.trade.client.AmBorrowCarinfoClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmBorrowCarinfoClientImpl implements AmBorrowCarinfoClient {


    @Override
    public List<BorrowCarinfoVO> getBorrowCarinfoByNid(String borrowNid) {
        return null;
    }
}
