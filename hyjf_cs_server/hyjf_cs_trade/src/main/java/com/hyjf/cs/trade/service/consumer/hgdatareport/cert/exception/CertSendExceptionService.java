package com.hyjf.cs.trade.service.consumer.hgdatareport.cert.exception;

import com.hyjf.am.vo.hgreportdata.cert.CertErrLogVO;
import com.hyjf.cs.trade.service.consumer.BaseHgCertReportService;

import java.util.List;


/**
 * @author sss
 */

public interface CertSendExceptionService extends BaseHgCertReportService {

    /**
     * 获取待处理的异常
     * @return
     */
    List<CertErrLogVO> getCertErrLogs();
}