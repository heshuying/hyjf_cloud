package com.hyjf.cs.message.mongo;

import com.hyjf.cs.message.bean.SmsLog;
import org.springframework.stereotype.Repository;

/**
 * @author xiasq
 * @version SmsLogDao, v0.1 2018/5/4 9:45
 */
@Repository
public class SmsLogDao extends BaseMongoDao<SmsLog> {
	@Override
	protected Class<SmsLog> getEntityClass() {
		return SmsLog.class;
	}
}
