package com.hyjf.am.trade.service.front.borrow;

import com.hyjf.am.trade.dao.model.auto.BorrowConfig;

/**
 * @author hesy
 * @version BorrowConfigService, v0.1 2018/7/4 13:57
 */
public interface BorrowConfigService {
    BorrowConfig selectBorrowConfigByCode(String code);
}
