/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.hyjf.cs.message.bean.MessagePushTemplateStatics;
import org.springframework.stereotype.Repository;

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

	@Override
	protected Class<MessagePushTemplateStatics> getEntityClass() {
		return MessagePushTemplateStatics.class;
	}

}
