package com.hyjf.cs.message.mongo.mc;

import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.message.bean.mc.MessagePushMsgHistory;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xiasq
 * @version MessagePushMsgHistoryDao, v0.1 2018/5/4 10:49
 */
@Repository
public class MessagePushMsgHistoryDao extends BaseMongoDao<MessagePushMsgHistory> {

	public List<MessagePushMsgHistory> getMsgHistoryListByMsgCode(String msgCode, Integer startTime, Integer endTime) {
		Query query = new Query();
		// 发送成功
		Criteria criteria = Criteria.where("msgSendStatus").is(CustomConstants.MSG_PUSH_SEND_STATUS_1);
		if (StringUtils.isNoneEmpty(msgCode)) {
			criteria.and("msgCode").is(msgCode);
		}
		if (startTime != null) {
			criteria.and("sendTime").gte(startTime);
		}
		if (endTime != null) {
			criteria.and("sendTime").lte(endTime);
		}
		query.addCriteria(criteria);
		return mongoTemplate.find(query, MessagePushMsgHistory.class);
	}

	@Override
	protected Class<MessagePushMsgHistory> getEntityClass() {
		return MessagePushMsgHistory.class;
	}

	/**
	 * 获得消息列表数量
	 * @param tagId 类型:0表示通知，1表示用户消息
	 * @param userId
	 * @param platform
	 * @return
	 */
	public Integer countMsgHistoryRecord(Integer tagId, Integer userId, String platform) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		Criteria criteria1 = new Criteria();
		if (tagId != null) {
			criteria.and("msgDestinationType").equals(tagId);
			criteria1.and("msgDestinationType").equals(tagId);
		}
		if (userId != null) {
			criteria1.and("msgUserId").equals(userId);
		}
		if (platform != null) {
			criteria.and("msgTerminal").regex(platform);
			criteria1.and("msgTerminal").regex(platform);
		}
		Criteria cr = new Criteria();
		query = new Query(cr.orOperator(criteria, criteria1));
		return (int) mongoTemplate.count(query, MessagePushMsgHistory.class);
	}
}
