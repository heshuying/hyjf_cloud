package com.hyjf.cs.message.mongo.mc;

import com.hyjf.cs.message.bean.mc.SmsLog;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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

	/**
	 * 查询所有短信发送记录
	 * 
	 * @return
	 */
	public List<SmsLog> findAll() {
		return mongoTemplate.findAll(SmsLog.class);
	}

	/**
	 * 根据条件查询所有短信发送记录
	 *
	 * @return
	 */
	@Override
    public List<SmsLog> find(Query query) {
		return mongoTemplate.find(query, SmsLog.class);
	}
}
