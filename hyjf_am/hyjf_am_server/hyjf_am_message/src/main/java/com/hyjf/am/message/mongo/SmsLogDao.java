package com.hyjf.am.message.mongo;

import com.hyjf.am.message.bean.SmsLog;

/**
 * @author xiasq
 * @version SmsLogDao, v0.1 2018/5/4 9:45
 */
public class SmsLogDao extends BaseMongoDao<SmsLog> {
	@Override
	protected Class<SmsLog> getEntityClass() {
		return SmsLog.class;
	}
}
