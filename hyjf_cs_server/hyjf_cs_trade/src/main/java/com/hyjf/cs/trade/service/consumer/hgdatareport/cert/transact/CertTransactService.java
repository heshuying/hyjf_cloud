package com.hyjf.cs.trade.service.consumer.hgdatareport.cert.transact;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.cs.trade.service.consumer.BaseHgCertReportService;


/**
 * @author pcc
 */

public interface CertTransactService extends BaseHgCertReportService {

	JSONArray createDate(String minId, String maxId);



}