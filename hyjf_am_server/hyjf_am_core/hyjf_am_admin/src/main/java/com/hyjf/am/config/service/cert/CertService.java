/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.cert;

import com.hyjf.am.config.dao.model.auto.CertLog;
import com.hyjf.am.resquest.admin.CertReportLogRequestBean;

import java.util.List;

/**
 * 合规数据上报 CERT 应急中心上报记录
 */
public interface CertService {

    /**
     * 上报日志数量
     * @param request
     * @return
     */
    int selectCertReportLogListCount(CertReportLogRequestBean request);

    /**
     * 上报日志
     * @param request
     * @return
     */
    List<CertLog> selectCertReportLogList(CertReportLogRequestBean request);
}
