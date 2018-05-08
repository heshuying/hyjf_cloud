package com.hyjf.cs.message.mongo;

import com.hyjf.cs.message.bean.MessagePushMsgHistory;
import org.springframework.stereotype.Repository;

/**
 * @author xiasq
 * @version MessagePushMsgHistoryDao, v0.1 2018/5/4 10:49
 */
@Repository
public class MessagePushMsgHistoryDao extends BaseMongoDao<MessagePushMsgHistory> {
	@Override
	protected Class<MessagePushMsgHistory> getEntityClass() {
		return MessagePushMsgHistory.class;
	}
}
