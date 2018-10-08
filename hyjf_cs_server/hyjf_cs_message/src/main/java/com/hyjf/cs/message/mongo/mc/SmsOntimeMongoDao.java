/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.mc;

import com.hyjf.cs.message.bean.mc.SmsOntime;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.stereotype.Repository;

/**
 * @author fuqiang
 * @version SmsOntimeMongoDao, v0.1 2018/6/22 11:08
 */
@Repository
public class SmsOntimeMongoDao extends BaseMongoDao<SmsOntime> {
	@Override
	protected Class<SmsOntime> getEntityClass() {
		return SmsOntime.class;
	}
}
