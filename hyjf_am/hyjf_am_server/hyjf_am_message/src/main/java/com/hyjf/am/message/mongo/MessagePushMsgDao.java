package com.hyjf.am.message.mongo;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.hyjf.am.message.bean.MessagePushMsg;
import org.springframework.stereotype.Repository;

/**
 * @author xiasq
 * @version MessagePushMsgDao, v0.1 2018/5/4 10:49
 */
@Repository
public class MessagePushMsgDao extends BaseMongoDao<MessagePushMsg> {

	public MessagePushMsg findById(int id) {
		Query query = new Query();
		Criteria criteria = Criteria.where("id").is(id);
		query.addCriteria(criteria);
		return mongoTemplate.findOne(query, getEntityClass());
	}

	@Override
	protected Class<MessagePushMsg> getEntityClass() {
		return MessagePushMsg.class;
	}
}
