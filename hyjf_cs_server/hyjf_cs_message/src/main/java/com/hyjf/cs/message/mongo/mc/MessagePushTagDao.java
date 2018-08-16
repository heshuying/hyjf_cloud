/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.mc;

import com.hyjf.cs.message.bean.mc.MessagePushTag;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dangzw
 * @version MessagePushTagDao, v0.1 2018/8/15 9:33
 */
@Repository
public class MessagePushTagDao extends BaseMongoDao<MessagePushTag> {

    @Override
    protected Class<MessagePushTag> getEntityClass() {
        return MessagePushTag.class;
    }

    /**
     * 获取列表
     *
     * @return
     */
    public List<MessagePushTag> getTagList() {
        Criteria criteria = new Criteria();
        Query query = new Query();
        // 条件查询
        criteria.and("status").is(1);
        query.with(new Sort(Sort.Direction.ASC, "sort"));
        return mongoTemplate.find(query, MessagePushTag.class);
    }

}
