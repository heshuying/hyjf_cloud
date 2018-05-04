package com.hyjf.am.message.mongo;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.hyjf.am.message.bean.MessagePush;
import org.springframework.stereotype.Repository;

/**
 * @author xiasq
 * @version MessagePushMsgDao, v0.1 2018/5/4 10:49
 */
@Repository
public class MessagePushMsgDao extends BaseMongoDao<MessagePush> {

	public MessagePush findById(int id) {
		Query query = new Query();
		Criteria criteria = Criteria.where("id").is(id);
		query.addCriteria(criteria);
		return mongoTemplate.findOne(query, getEntityClass());
	}

	@Override
	protected Class<MessagePush> getEntityClass() {
		return MessagePush.class;
	}
}
