package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.vo.trade.borrow.BorrowHousesVO;
import com.hyjf.cs.trade.client.AmBorrowHousesClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmBorrowHousesClientImpl implements AmBorrowHousesClient {


    @Override
    public List<BorrowHousesVO> getBorrowHousesByNid(String borrowNid) {
        return null;
    }
}
