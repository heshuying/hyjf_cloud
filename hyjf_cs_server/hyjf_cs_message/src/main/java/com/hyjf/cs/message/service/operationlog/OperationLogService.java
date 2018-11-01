package com.hyjf.cs.message.service.operationlog;

import com.hyjf.cs.message.bean.mc.UserOperationLogEntity;

import java.util.List;
import java.util.Map;

public interface OperationLogService {


	/**
	 * 查询会员操作日志总数
	 * @param operationLog
	 * @return
	 */
	int countOperationLog(Map<String, Object> operationLog);

	/**
	 * 查询会员操作日志列表
	 * @param operationLog
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<UserOperationLogEntity> getOperationLogList(Map<String, Object> operationLog, int offset, int limit);
}
