/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.mc;

import com.hyjf.am.resquest.config.MessagePushPlatStaticsRequest;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.mc.MessagePushPlatStatics;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
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
        if (request.getStartDateSrch() != null) {
            Date startTime = GetDate.stringToDate2(request.getStartDateSrch());
            criteria.and("createTime").gte((int) (startTime.getTime() / 1000));
        }
        if (request.getEndDateSrch() != null) {
            Date endTime = GetDate.stringToDate2(request.getEndDateSrch());
            criteria.and("createTime").lte((int) (endTime.getTime() / 1000));
        }
        if (request.getTagIdSrch() != null) {
            criteria.and("tagId").regex(request.getTagIdSrch());
        }
        int currPage = request.getCurrPage();
        int pageSize = request.getPageSize();
        int limitStart = (currPage - 1) * pageSize;
        int limitEnd = limitStart + pageSize;
        query.addCriteria(criteria);
        query.skip(limitStart).limit(limitEnd);
        return mongoTemplate.find(query, getEntityClass());
    }
}
