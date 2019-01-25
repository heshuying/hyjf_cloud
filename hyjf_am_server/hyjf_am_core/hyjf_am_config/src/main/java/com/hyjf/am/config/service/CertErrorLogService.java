/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;


import com.hyjf.am.config.dao.model.auto.CertErrLog;
import com.hyjf.am.config.dao.model.auto.CertLog;

import java.util.List;

public interface CertErrorLogService {

	/**
	 * 获取待处理的异常
	 * @return
	 */
	List<CertErrLog> getCertErrLogs();

	/**
	 * 插入发送记录表
	 * @param log
	 */
	void insertCertLog(CertLog log);

	/**
	 * 插入错误日志表
	 * @param log
	 */
	void insertCertErrorLog(CertErrLog log);
}
