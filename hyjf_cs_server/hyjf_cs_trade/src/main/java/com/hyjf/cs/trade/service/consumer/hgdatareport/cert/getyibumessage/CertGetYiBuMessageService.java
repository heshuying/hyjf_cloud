package com.hyjf.cs.trade.service.consumer.hgdatareport.cert.getyibumessage;


import com.hyjf.am.vo.hgreportdata.cert.CertLogVO;
import com.hyjf.am.vo.hgreportdata.cert.CertReportEntityVO;
import com.hyjf.cs.trade.service.consumer.BaseHgCertReportService;

import java.util.List;

/**
 * 查询批次数据入库消息
 * @Author nxl
 * @Date 2018/12/25 17:10
 */
public interface CertGetYiBuMessageService extends BaseHgCertReportService {

	CertReportEntityVO updateYiBuMessage(String batchNum, int certLogId, String infType);

	List<CertLogVO> getCertLog();
	/**
	 * 应急中心 查询待异步查询的日志数量
	 * add by nxl
	 * @return
	 */
	int selectCertLogLength();
}
