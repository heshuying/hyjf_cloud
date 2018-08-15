package com.hyjf.cs.message.mongo.mc;

import com.hyjf.am.resquest.admin.MessagePushNoticesRequest;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.mc.MessagePushMsg;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lisheng
 * @version MessagePushNoticesMongoDao, v0.1 2018/8/14 15:06
 */
@Repository
public class MessagePushMsgMongoDao extends BaseMongoDao<MessagePushMsg> {
    /**
     * 获取记录条数
     * @return
     */
    public Integer countRecordList(MessagePushNoticesRequest form){
        Criteria criteria = new Criteria();
        if (StringUtils.isNotEmpty(form.getEndSendTimeSrch())) {
            criteria.and("msgDestinationType").equals(form.getEndSendTimeSrch());

        }
        Query query = new Query(criteria);
        return (int)mongoTemplate.count(query,MessagePushMsg.class);
    }
    /**
     * 获取发送消息记录列表
     * @param request
     * @return
     */
    public List<MessagePushMsg> getRecordList(MessagePushNoticesRequest request, Integer offset, Integer limit){
        Criteria criteria = new Criteria();
        Query query = new Query(criteria);
        return mongoTemplate.find(query,MessagePushMsg.class);
    }

    /**
     * 获取单条数据
     * @param request
     * @return
     */
    public MessagePushMsg getRecord(MessagePushNoticesRequest request){
        Criteria criteria = new Criteria();
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query,MessagePushMsg.class);
    }
    /**
     * 插入数据
     * @param request
     * @return
     */
    public void insertRecord(MessagePushNoticesRequest request){
        MessagePushMsg messagePushMsg = CommonUtils.convertBean(request, MessagePushMsg.class);
        mongoTemplate.save(messagePushMsg);
    }

    /**
     * 删除数据
     * @return
     */
    public void deleteRecord(Integer id){
        Query query = new Query();
        Criteria criteria = new Criteria();
        query.addCriteria(criteria);
        this.del(query);
    }


    /**
     * 修改数据
     * @return
     */
    public void updateRecord(MessagePushMsg record){
        Query query = new Query();
        Criteria criteria = new Criteria();
        //criteria.and("date").is(hjhPlanCapital.getDate());
        //criteria.and("planNid").is(hjhPlanCapital.getPlanNid());

        Update update = new Update();
        update.inc("updateTime", GetDate.getNowTime10()).set("delFlg", 0);

        this.update(query, update);
    }

    @Override
    protected Class<MessagePushMsg> getEntityClass() {

        return MessagePushMsg.class;
    }
}
