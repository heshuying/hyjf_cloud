package com.hyjf.cs.trade.service.consumer.hgdatareport.cert.scatterinvest;

import com.hyjf.cs.trade.service.consumer.BaseHgCertReportService;

import java.util.Map;


/**
 * @author sss
 */

public interface CertScatterInveService extends BaseHgCertReportService {


    /**
     * 组装调用应急中心日志
     * @param borrowNid
     * @return
     */
    Map<String, Object> getSendData(String borrowNid, String idcardHash) throws Exception;

}