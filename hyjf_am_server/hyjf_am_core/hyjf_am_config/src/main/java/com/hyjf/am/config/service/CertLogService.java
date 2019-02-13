/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.CertLog;

import java.util.List;

/**
 * 合规数据上报 CERT 应急中心上报记录
 */
public interface CertLogService {
    /**
     * 根据id查找上报记录
     *
     * @param certLogId
     * @return
     */
    CertLog selectCertLogById(int certLogId);

    /**
     * 查找上报记录
     *
     * @return
     */
    List<CertLog> selectCertLog();

    /**
     * 更新发送日志
     * @param certLog
     * @return
     */
    int updateCertLog(CertLog certLog);

    /**
     * 查询待异步查询的日志数量
     * @return
     */
    int selectCertLogLength();
}
