/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.message.impl;

import com.hyjf.am.resquest.message.SmsLogRequest;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.mc.SmsLog;
import com.hyjf.cs.message.bean.mc.SmsOntime;
import com.hyjf.cs.message.mongo.mc.SmsLogDao;
import com.hyjf.cs.message.mongo.mc.SmsOntimeMongoDao;
import com.hyjf.cs.message.service.message.SmsLogService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

	private static Logger logger = LoggerFactory.getLogger(SmsLogServiceImpl.class);
	@Autowired
	private SmsLogDao smsLogDao;
	@Autowired
	private SmsOntimeMongoDao smsOntimeMongoDao;

	@Override
	public List<SmsLog> findSmsLog(SmsLogRequest request) {
		String mobile = request.getMobile();
		String type = request.getType();
		String postTimeBegin = request.getPostTimeBegin();
		String postTimeEnd = request.getPostTimeEnd();
		Integer status = request.getStatus();
		Query query = new Query();
		Criteria criteria = new Criteria();
		if (StringUtils.isNotBlank(mobile)) {
			criteria.and("mobile").is(mobile);
		}
		if (StringUtils.isNotBlank(type)) {
			criteria.and("type").is(type);
		}
		if (StringUtils.isNotBlank(postTimeBegin) && StringUtils.isNotBlank(postTimeEnd)) {
			Integer begin = GetDate.dateString2Timestamp(postTimeBegin + " 00:00:00");
			Integer end = GetDate.dateString2Timestamp(postTimeEnd + " 23:59:59");
			criteria.and("posttime").gte(begin).lte(end);
		}
		if (status != null) {
			criteria.and("status").is(status);
		}
		query.addCriteria(criteria);
		int currPage = request.getCurrPage();
		int pageSize = request.getPageSize();
		if (currPage > 0) {
			int limitStart = (currPage - 1) * pageSize;
			query.skip(limitStart).limit(pageSize);
		}
		query.with(new Sort(Sort.Direction.DESC, "posttime"));
		return smsLogDao.find(query);
	}

	@Override
	public List<SmsOntime> queryTime(SmsLogRequest request) {
		String mobile = request.getMobile();
		String postTimeBegin = request.getPostTimeBegin();
		String postTimeEnd = request.getPostTimeEnd();
		Integer status = request.getStatus();
		Query query = new Query();
		Criteria criteria = new Criteria();
		if (StringUtils.isNotBlank(mobile)) {
			criteria.and("mobile").is(mobile);
		}
		if (StringUtils.isNotBlank(postTimeBegin) && StringUtils.isNotBlank(postTimeEnd)) {
			Integer begin = GetDate.dateString2Timestamp(postTimeBegin + " 00:00:00");
			Integer end = GetDate.dateString2Timestamp(postTimeEnd + " 23:59:59");
			criteria.and("endtime").gte(begin).lte(end);
		}

		if (status != null) {
			criteria.and("status").is(status);
		}
		query.addCriteria(criteria);
		int currPage = request.getCurrPage();
		int pageSize = request.getPageSize();
		if (currPage > 0) {
			int limitStart = (currPage - 1) * pageSize;
			query.skip(limitStart).limit(pageSize);
		}
		query.with(new Sort(Sort.Direction.DESC, "posttime"));
		return smsOntimeMongoDao.find(query);
	}

	@Override
	public Integer queryLogCount(SmsLogRequest request) {
		String mobile = request.getMobile();
		String type = request.getType();
		String postTimeBegin = request.getPostTimeBegin();
		String postTimeEnd = request.getPostTimeEnd();
		Integer status = request.getStatus();
		Query query = new Query();
		Criteria criteria = new Criteria();
		if (StringUtils.isNotBlank(mobile)) {
			criteria.and("mobile").is(mobile);
		}
		if (StringUtils.isNotBlank(type)) {
			criteria.and("type").is(type);
		}
		if (StringUtils.isNotEmpty(postTimeBegin) && StringUtils.isNotEmpty(postTimeEnd) ) {
			Integer begin = GetDate.dateString2Timestamp(postTimeBegin + " 00:00:00");
			Integer end = GetDate.dateString2Timestamp(postTimeEnd + " 23:59:59");
			criteria.and("posttime").gte(begin).lte(end);
		}
		if (null !=  request.getPosttime() && null != request.getPost_time_end()) {
			logger.info("上月开始时间postTimeBegin："+request.getPosttime()+"-----上月结束时间postTimeEnd："+request.getPost_time_end());
			criteria.and("posttime").gte(request.getPosttime()).lte(request.getPost_time_end());
		}
		if (status != null && status != 2) {
			criteria.and("status").is(status);
		}
		query.addCriteria(criteria);
		long count = smsLogDao.count(query);
		return Math.toIntExact(count);
	}

	@Override
	public List<SmsLog> findSmsLogList() {
		return smsLogDao.findAll();
	}

	@Override
	public int queryOntimeCount(SmsLogRequest request) {
		String mobile = request.getMobile();
		String postTimeBegin = request.getPostTimeBegin();
		String postTimeEnd = request.getPostTimeEnd();
		Integer status = request.getStatus();
		Query query = new Query();
		Criteria criteria = new Criteria();
		if (StringUtils.isNotBlank(mobile)) {
			criteria.and("mobile").is(mobile);
		}
		if (StringUtils.isNotBlank(postTimeBegin) && StringUtils.isNotBlank(postTimeEnd)) {
			Integer begin = GetDate.dateString2Timestamp(postTimeBegin + " 00:00:00");
			Integer end = GetDate.dateString2Timestamp(postTimeEnd + " 23:59:59");
			criteria.and("endtime").gte(begin).lte(end);
		}

		if (status != null) {
			criteria.and("status").is(status);
		}
		query.addCriteria(criteria);
		query.with(new Sort(Sort.Direction.DESC, "posttime"));
		return smsOntimeMongoDao.count(query).intValue();
	}
}
