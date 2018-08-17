/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.mc;

import com.hyjf.am.resquest.message.MessagePushTemplateStaticsRequest;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.mc.MessagePushTemplateStatics;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author fuqiang
 * @version MessagePushTemplateStaticsDao, v0.1 2018/6/22 9:27
 */
@Repository
public class MessagePushTemplateStaticsDao extends BaseMongoDao<MessagePushTemplateStatics> {

	/**
	 * 根据消息编码获取统计数据
	 * 
	 * @param msgCode
	 * @return
	 */
	public List<MessagePushTemplateStatics> getMessagePushTemplateStaticsByMsgCode(String msgCode) {
		if (msgCode != null) {
			Query query = new Query();
			Criteria criteria = Criteria.where("msgCode").is(msgCode);
			query.addCriteria(criteria);
			return mongoTemplate.find(query, getEntityClass());
		} else {
			return mongoTemplate.findAll(getEntityClass());
		}
	}

	/**
	 * 根据时间获取模板统计数据
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<MessagePushTemplateStatics> getTemplateStaticsListByTime(Integer startTime, Integer endTime) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		if (startTime != null) {
			criteria.and("createTime").gte(startTime);
		}
		if (endTime != null) {
			criteria.and("createTime").lte(endTime);
		}
		query.addCriteria(criteria);
		return mongoTemplate.find(query, getEntityClass());
	}

	/**
	 *查询模板消息统计报表
	 * @param request
	 * @return
	 */
	public List<MessagePushTemplateStatics> selectTemplateStatics(MessagePushTemplateStaticsRequest request) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		if (request.getStartDateSrch() != null) {
			Integer startTime = GetDate.dateString2Timestamp(request.getStartDateSrch());
			criteria.and("createTime").gte(startTime);
		}
		if (request.getEndDateSrch() != null) {
			Integer endTime = GetDate.dateString2Timestamp(request.getEndDateSrch());
			criteria.and("createTime").lte(endTime);
		}
		if (request.getMsgTitleSrch() != null) {
			criteria.and("msgTitle").regex(request.getMsgTitleSrch());
		}
		if (request.getMsgCodeSrch() != null) {
			criteria.and("msgCode").is(request.getMsgCodeSrch());
		}
		if (request.getTagIdSrch() != null) {
			criteria.and("tagId").is(request.getTagIdSrch());
		}
		int currPage = request.getCurrPage();
		int pageSize = request.getPageSize();
		int limitStart = (currPage - 1) * pageSize;
		int limitEnd = limitStart + pageSize;
		query.addCriteria(criteria);
		query.skip(limitStart).limit(limitEnd);
		return mongoTemplate.find(query, getEntityClass());
	}

	@Override
	protected Class<MessagePushTemplateStatics> getEntityClass() {
		return MessagePushTemplateStatics.class;
	}


}
