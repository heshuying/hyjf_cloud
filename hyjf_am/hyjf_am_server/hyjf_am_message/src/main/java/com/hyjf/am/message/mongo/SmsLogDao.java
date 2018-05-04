package com.hyjf.am.message.mongo;

import com.hyjf.am.message.bean.SmsLogWithBLOBs;

/**
 * @author xiasq
 * @version SmsLogDao, v0.1 2018/5/4 9:45
 */
public class SmsLogDao extends BaseMongoDao<SmsLogWithBLOBs> {
	@Override
	protected Class<SmsLogWithBLOBs> getEntityClass() {
		return SmsLogWithBLOBs.class;
	}
}
