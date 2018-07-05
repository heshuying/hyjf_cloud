package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.borrow.BorrowConfigVO;

/**
 * @author hesy
 * @version BorrowConfigClient, v0.1 2018/7/4 14:13
 */
public interface BorrowConfigClient {
    BorrowConfigVO getConfigByCode(String code);
}
