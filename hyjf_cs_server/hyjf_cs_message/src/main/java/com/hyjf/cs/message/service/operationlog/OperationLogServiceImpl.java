package com.hyjf.cs.message.service.operationlog;

import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.ic.userbehaviourn.UserOperationLog;
import com.hyjf.cs.message.mongo.ic.userbehaviourn.UserOperationLogMongDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class OperationLogServiceImpl  implements OperationLogService {

	@Autowired
	private UserOperationLogMongDao userOperationLogMongDao;


    @Override
    public int countOperationLog(Map<String, Object> operationLog) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		if (operationLog.get("userName") != null) {
			criteria.and("userName").is(operationLog.get("userName"));
		}
		if (operationLog.get("operationType") != null) {
			criteria.and("operationType").is(operationLog.get("operationType"));
		}
		if (operationLog.get("userRole") != null) {
			criteria.and("userRole").is(operationLog.get("userRole"));
		}
		if (operationLog.get("operationTimeStart") != null || operationLog.get("operationTimeEnd") != null) {
			Date operationTimeStart = GetDate.stringToDate(operationLog.get("operationTimeStart").toString());
			Date operationTimeEnd = GetDate.stringToDate(operationLog.get("operationTimeEnd").toString());
			criteria.and("operationTime").gte(operationTimeStart).lte(operationTimeEnd);
		}
		query.addCriteria(criteria);
		int count = userOperationLogMongDao.count(query).intValue();
        return count;
    }

	@Override
	public List<UserOperationLog> getOperationLogList(Map<String, Object> operationLog, int limitStart, int limitEnd) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		if (operationLog.get("userName") != null) {
			criteria.and("userName").is(operationLog.get("userName"));
		}
		if (operationLog.get("operationType") != null) {
			criteria.and("operationType").is(operationLog.get("operationType"));
		}
		if (operationLog.get("userRole") != null) {
			criteria.and("userRole").is(operationLog.get("userRole"));
		}
		if (operationLog.get("operationTimeStart") != null || operationLog.get("operationTimeEnd") != null) {
			Date operationTimeStart = GetDate.stringToDate(operationLog.get("operationTimeStart").toString());
			Date operationTimeEnd = GetDate.stringToDate(operationLog.get("operationTimeEnd").toString());
			criteria.and("operationTime").gte(operationTimeStart).lte(operationTimeEnd);
		}
		query.addCriteria(criteria);
		query.skip(limitStart).limit(limitEnd);
		query.with(new Sort(Sort.Direction.DESC, "operationTime"));
		List<UserOperationLog> list = userOperationLogMongDao.find(query);
		return list;
	}


}
