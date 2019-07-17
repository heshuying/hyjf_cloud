/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.cert;

import com.hyjf.am.config.dao.model.auto.CertErrLog;
import com.hyjf.am.config.dao.model.auto.CertLog;
import com.hyjf.am.resquest.admin.CertErrorReportLogRequestBean;
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

    /**
     * 错误日志数量
     * @param request
     * @return
     */
    int selectCertErrorReportLogListCount(CertErrorReportLogRequestBean request);

    /**
     * 错误日志
     * @param request
     * @return
     */
    List<CertErrLog> selectCertErrorReportLogList(CertErrorReportLogRequestBean request);

    /**
     * 重新跑批
     * @param id
     */
    void updateCertErrorCount(Integer id);
    /**
     * 根据id修改对账状态
     * @param id
     * @return
     */
    int updateCertLogById(Integer id);
    /**
     * 批量修改对账状态
     * @param ids
     * @param certLog
     * @return
     */
    int batchUpdateCertLogByIds(List<Integer> ids,CertLog certLog);
    /**
     * 重新上报数据
     * @param logOrderId
     * @return
     */
    int insertCertErrorLogByLogOrderId(String logOrderId);
}
