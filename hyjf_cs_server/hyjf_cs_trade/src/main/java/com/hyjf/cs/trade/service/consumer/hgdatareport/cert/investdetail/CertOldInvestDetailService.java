package com.hyjf.cs.trade.service.consumer.hgdatareport.cert.investdetail;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.vo.hgreportdata.cert.CertAccountListCustomizeVO;
import com.hyjf.am.vo.hgreportdata.cert.CertReportEntityVO;
import com.hyjf.cs.trade.service.consumer.BaseHgCertReportService;

import java.util.List;


/**
 * @author pcc
 */

public interface CertOldInvestDetailService extends BaseHgCertReportService {

	JSONArray createDate(List<CertAccountListCustomizeVO> accountLists);

	void insertOldMessage(CertReportEntityVO entity);

	List<CertAccountListCustomizeVO> getCertAccountListCustomizeVO(Integer page, Integer size,String trader);
}