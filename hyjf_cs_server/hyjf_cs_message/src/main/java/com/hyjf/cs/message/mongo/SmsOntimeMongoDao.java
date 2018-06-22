/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo;

import org.springframework.stereotype.Repository;

import com.hyjf.cs.message.bean.SmsOntime;

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
