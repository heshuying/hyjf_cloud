/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;


import com.hyjf.am.config.dao.model.auto.CertErrLog;

import java.util.List;

public interface CertErrorLogService {

	/**
	 * 获取待处理的异常
	 * @return
	 */
	List<CertErrLog> getCertErrLogs();
}
