/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.message.impl;

import com.hyjf.am.resquest.message.SmsLogRequest;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.SmsLog;
import com.hyjf.cs.message.mongo.SmsLogDao;
import com.hyjf.cs.message.service.message.SmsLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fuqiang
 * @version SmsLogServiceImpl, v0.1 2018/6/23 14:23
 */
@Service
public class SmsLogServiceImpl implements SmsLogService {

	@Autowired
	private SmsLogDao smsLogDao;

	@Override
	public List<SmsLog> findSmsLog(SmsLogRequest request) {
		String mobile = request.getMobile();
		String postTimeBegin = request.getPostTimeBegin();
		String postTimeEnd = request.getPostTimeEnd();
		Integer status = request.getStatus();
		Query query = new Query();
		Criteria criteria = new Criteria();
		if (StringUtils.isNotBlank(mobile)) {
			criteria.and("mobile").is(mobile);
		}
		if (StringUtils.isNotBlank(postTimeBegin)) {
			Integer begin = GetDate.dateString2Timestamp(postTimeBegin);
			criteria.and("posttime").gte(begin);
		}
		if (StringUtils.isNotBlank(postTimeEnd)) {
			Integer end = GetDate.dateString2Timestamp(postTimeEnd);
			criteria.and("posttime").lte(end);
		}
		if (status != null) {
			criteria.and("status").is(status);
		}
		query.addCriteria(criteria);
		return smsLogDao.find(query);
	}
}
