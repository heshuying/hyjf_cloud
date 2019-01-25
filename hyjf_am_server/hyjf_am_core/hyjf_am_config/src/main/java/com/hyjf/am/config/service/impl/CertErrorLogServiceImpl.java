package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.CertErrLogMapper;
import com.hyjf.am.config.dao.model.auto.CertErrLog;
import com.hyjf.am.config.dao.model.auto.CertErrLogExample;
import com.hyjf.am.config.service.CertErrorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CertErrorLogServiceImpl implements CertErrorLogService {
	@Autowired
	protected CertErrLogMapper certErrLogMapper;

	/**
	 * 获取待处理的异常
	 *
	 * @return
	 */
	@Override
	public List<CertErrLog> getCertErrLogs() {
		CertErrLogExample example = new CertErrLogExample();
		CertErrLogExample.Criteria cra = example.createCriteria();
		// 默认是1  发三次是 4  所以查询小于5的
		cra.andSendCountLessThan(4);
		example.setLimitStart(0);
		example.setLimitEnd(50);
		return this.certErrLogMapper.selectByExample(example);
	}
}
