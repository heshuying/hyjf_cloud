/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.hgreportdata.cert;

import com.hyjf.am.resquest.hgreportdata.cert.CertReportEntitRequest;
import com.hyjf.cs.common.service.BaseService;
import com.hyjf.cs.message.bean.hgreportdata.cert.CertAccountList;
import com.hyjf.cs.message.bean.hgreportdata.cert.CertReportEntity;

/**
 * @author nxl
 * @version CertStatisticalService, v0.1 2019/1/19 10:07
 */
public interface CertStatisticalService extends BaseService {
    /**
     * 插入mongo数据
     * @param bean
     */
    void insertAndSendPost(CertReportEntity bean);
    /**
     * 插入mongo数据
     * @param bean
     */
    void updateCertReport(CertReportEntitRequest bean);

    /**
     * 根据订单号查询
     * @param logOrdId
     * @return
     */
    CertReportEntity getCertSendLogByLogOrdId(String logOrdId);

    void insertOldMessage(CertAccountList certAccountList);
}
