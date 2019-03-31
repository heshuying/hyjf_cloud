package com.hyjf.admin.service;

import com.hyjf.am.response.admin.BorrowRepayInfoCurrentResponse;

/**
 * @author hesy
 */
public interface BorrowRepayInfoCurrentService {
    BorrowRepayInfoCurrentResponse getRepayInfoCurrentData(String borrowNid);
}
