package com.hyjf.cs.trade.service.consumer.hgdatareport.cert.transferproject;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.cs.trade.service.consumer.BaseHgCertReportService;


/**
 * @author pcc
 */

public interface CertTransferProjectService extends BaseHgCertReportService {

	JSONArray createDate(String creditNid, String flag);


}