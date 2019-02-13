package com.hyjf.cs.trade.service.consumer.hgdatareport.cert.status;

import com.hyjf.cs.trade.service.consumer.BaseHgCertReportService;

import java.util.Map;


/**
 * @author nxl
 */

public interface CertBorrowStatusService extends BaseHgCertReportService {
    /**
     * 获取标的的还款信息
     * @param borrwoNid
     * @return
     */
    Map<String,Object> selectBorrowByBorrowNid(String borrwoNid, String statusAfter, boolean isUserInfo);

}