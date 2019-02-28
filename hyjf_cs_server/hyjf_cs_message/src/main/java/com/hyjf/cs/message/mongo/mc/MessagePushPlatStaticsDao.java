/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.mc;

import com.hyjf.am.resquest.config.MessagePushPlatStaticsRequest;
import com.hyjf.cs.message.bean.mc.MessagePushPlatStatics;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author fq
 * @version MessagePushPlatStaticsDao, v0.1 2018/8/14 16:28
 */
@Repository
public class MessagePushPlatStaticsDao extends BaseMongoDao<MessagePushPlatStatics> {
    @Override
    protected Class<MessagePushPlatStatics> getEntityClass() {
        return MessagePushPlatStatics.class;
    }

    /**
     * 根据条件查询平台消息统计报表
     * @param request
     * @return
     */
    public List<MessagePushPlatStatics> selectPlatStatics(MessagePushPlatStaticsRequest request) {
        Query query = new Query();
        Criteria criteria = new Criteria();
		if (StringUtils.isNotBlank(request.getStartDateSrch()) && StringUtils.isNotBlank(request.getEndDateSrch())) {
			criteria.and("staDate").gte(request.getStartDateSrch() + " 00:00:00")
					.lte(request.getEndDateSrch() + " 23:59:59");
		}
        if (StringUtils.isNotBlank(request.getTagIdSrch())) {
            criteria.and("tagId").is(request.getTagIdSrch());
        }
        query.addCriteria(criteria);
        if (request.getCurrPage() > 0 && request.getPageSize() > 0) {
            int currPage = request.getCurrPage();
            int pageSize = request.getPageSize();
            int limitStart = (currPage - 1) * pageSize;
            query.skip(limitStart).limit(pageSize);
        }
        query.with(new Sort(Sort.Direction.DESC, "staDate"));
        return mongoTemplate.find(query, getEntityClass());
    }
}
