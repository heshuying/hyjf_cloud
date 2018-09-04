package com.hyjf.cs.message.mongo.mc;

import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.mc.MessagePush;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xiasq
 * @version MessagePushMsgDao, v0.1 2018/5/4 10:49
 */
@Repository
public class MessagePushMsgDao extends BaseMongoDao<MessagePush> {

	public MessagePush findById(String id) {
		Query query = new Query();
		Criteria criteria = Criteria.where("id").is(id);
		query.addCriteria(criteria);
		return mongoTemplate.findOne(query, getEntityClass());
	}

	public List<MessagePush> findAllMessage() {
		Query query = new Query();
		Criteria criteria1 = Criteria.where("msgSendStatus").is(CustomConstants.MSG_PUSH_MSG_STATUS_0)
				.and("msgSendType").is(CustomConstants.MSG_PUSH_SEND_TYPE_0);
		Criteria criteria2 = Criteria.where("msgSendStatus").is(CustomConstants.MSG_PUSH_MSG_STATUS_0)
				.and("msgSendType").is(CustomConstants.MSG_PUSH_SEND_TYPE_1).and("preSendTime")
				.lte(GetDate.getNowTime10());
		Criteria criteria = new Criteria().orOperator(criteria1, criteria2);
		query.addCriteria(criteria);
		return mongoTemplate.find(query, getEntityClass());
	}

	/**
	 * 根据时间取发送记录
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<MessagePush> getMsgStaticsListByTime(Integer startTime, Integer endTime) {
		Query query = new Query();
		Criteria criteria = Criteria.where("msgSendStatus").is(CustomConstants.MSG_PUSH_MSG_STATUS_1);
		if (startTime != null) {
			criteria.and("sendTime").gte(startTime);
		}
		if (endTime != null) {
			criteria.and("endTime").lte(endTime);
		}
		query.addCriteria(criteria);
		return mongoTemplate.find(query, getEntityClass());
	}

	@Override
	protected Class<MessagePush> getEntityClass() {
		return MessagePush.class;
	}

}
