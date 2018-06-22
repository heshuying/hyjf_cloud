package com.hyjf.cs.message.mongo;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.message.bean.MessagePushMsgHistory;

/**
 * @author xiasq
 * @version MessagePushMsgHistoryDao, v0.1 2018/5/4 10:49
 */
@Repository
public class MessagePushMsgHistoryDao extends BaseMongoDao<MessagePushMsgHistory> {

	public List<MessagePushMsgHistory> getMsgHistoryListByMsgCode(String msgCode, Integer startTime, Integer endTime) {
		Query query = new Query();
		Criteria criteria = Criteria.where("msgSendStatus").is(CustomConstants.MSG_PUSH_SEND_STATUS_1);// 发送成功
		if (StringUtils.isNoneEmpty(msgCode)) {
			criteria.and("msgCode").is(msgCode);
		}
		if (startTime != null) {
			criteria.and("sendTime").gte(startTime);
		}
		if (endTime != null) {
			criteria.and("sendTime").lte(endTime);
		}
		return mongoTemplate.find(query, MessagePushMsgHistory.class);
	}

	@Override
	protected Class<MessagePushMsgHistory> getEntityClass() {
		return MessagePushMsgHistory.class;
	}
}
