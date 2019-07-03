package com.hyjf.cs.trade.service.consumer.hgdatareport.cert.investdetail;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.vo.hgreportdata.cert.CertAccountListIdCustomizeVO;
import com.hyjf.cs.trade.service.consumer.BaseHgCertReportService;

import java.util.Map;


/**
 * @author pcc
 */

public interface CertInvestDetailService extends BaseHgCertReportService {

	JSONArray createDate(String minId, String maxId);
}