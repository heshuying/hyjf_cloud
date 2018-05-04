package com.hyjf.am.message.mongo;

import com.hyjf.am.message.bean.MessagePushMsgHistory;

/**
 * @author xiasq
 * @version MessagePushMsgHistoryDao, v0.1 2018/5/4 10:49
 */
public class MessagePushMsgHistoryDao extends BaseMongoDao<MessagePushMsgHistory> {

	@Override
	protected Class<MessagePushMsgHistory> getEntityClass() {
		return MessagePushMsgHistory.class;
	}
}
