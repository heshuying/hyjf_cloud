package com.hyjf.cs.message.mongo.mc;

import com.hyjf.am.resquest.admin.MessagePushHistoryRequest;
import com.hyjf.cs.message.bean.mc.MessagePushMsgHistory;
import com.hyjf.cs.message.bean.mc.MessagePushTag;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lisheng
 * @version MessagePushTagMongoDao, v0.1 2018/8/14 15:09
 */
@Repository
public class MessagePushTagMongoDao extends BaseMongoDao<MessagePushTag> {
    /**
     * 获取所有标签信息
     * @return
     */
    public List<MessagePushTag> getPushTagList(){
        Criteria criteria = new Criteria();
        Query query = new Query(criteria);
        return mongoTemplate.find(query,MessagePushTag.class);
    }

    /**
     * 获取标签信息
     * @return
     */
    public List<MessagePushTag> getTagList(){
        Criteria criteria = new Criteria();
        criteria.is("status").equals(1);
        Query query = new Query(criteria);
        query.with(new Sort(Sort.Direction.ASC, "sort"));
        return mongoTemplate.find(query,MessagePushTag.class);
    }

    @Override
    protected Class<MessagePushTag> getEntityClass() {
        return MessagePushTag.class;
    }


}
