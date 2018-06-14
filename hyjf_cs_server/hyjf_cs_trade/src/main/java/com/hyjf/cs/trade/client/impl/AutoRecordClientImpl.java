/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.vo.borrow.BorrowVO;
import com.hyjf.cs.trade.client.AutoRecordClient;
import org.springframework.stereotype.Service;

/**
 * @author fuqiang
 * @version AutoRecordClientImpl, v0.1 2018/6/14 11:03
 */
@Service
public class AutoRecordClientImpl implements AutoRecordClient {
    @Override
    public BorrowVO selectBorrowByNid(String borrowNid) {
        return null;
    }

    @Override
    public boolean updateBorrowRegist(BorrowVO borrowVO, int i, int i1) {
        return false;
    }
}
