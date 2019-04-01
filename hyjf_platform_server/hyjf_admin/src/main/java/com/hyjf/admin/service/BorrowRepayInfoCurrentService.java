package com.hyjf.admin.service;

import com.hyjf.am.response.admin.BorrowRepayInfoCurrentExportResponse;
import com.hyjf.am.response.admin.BorrowRepayInfoCurrentResponse;

/**
 * @author hesy
 */
public interface BorrowRepayInfoCurrentService {
    BorrowRepayInfoCurrentResponse getRepayInfoCurrentData(String borrowNid);

    BorrowRepayInfoCurrentExportResponse getRepayInfoCurrentExportData(String borrowNid);

    Integer getRepayInfoCurrentExportCount(String borrowNid);
}
