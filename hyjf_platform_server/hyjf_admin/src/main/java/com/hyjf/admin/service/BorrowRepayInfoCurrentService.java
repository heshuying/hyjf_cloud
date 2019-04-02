package com.hyjf.admin.service;

import com.hyjf.am.response.admin.BorrowRepayInfoCurrentExportResponse;
import com.hyjf.am.response.admin.BorrowRepayInfoCurrentResponse;
import com.hyjf.am.resquest.admin.BorrowRepayInfoCurrentRequest;

/**
 * @author hesy
 */
public interface BorrowRepayInfoCurrentService {
    BorrowRepayInfoCurrentResponse getRepayInfoCurrentData(String borrowNid);

    BorrowRepayInfoCurrentExportResponse getRepayInfoCurrentExportData(BorrowRepayInfoCurrentRequest requestBean);

    Integer getRepayInfoCurrentExportCount(String borrowNid);
}
