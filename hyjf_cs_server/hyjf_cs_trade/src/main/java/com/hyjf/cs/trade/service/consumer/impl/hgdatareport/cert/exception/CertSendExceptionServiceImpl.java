package com.hyjf.cs.trade.service.consumer.impl.hgdatareport.cert.exception;

import com.hyjf.am.vo.hgreportdata.cert.CertErrLogVO;
import com.hyjf.cs.trade.client.AmConfigClient;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.exception.CertSendExceptionService;
import com.hyjf.cs.trade.service.consumer.impl.BaseHgCertReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author sss
 */

@Service
public class CertSendExceptionServiceImpl extends BaseHgCertReportServiceImpl implements CertSendExceptionService {

    @Autowired
    AmConfigClient amConfigClient;


    /**
     * 获取待处理的异常
     *
     * @return
     */
    @Override
    public List<CertErrLogVO> getCertErrLogs() {
        return amConfigClient.getCertErrLogs();
    }
}
