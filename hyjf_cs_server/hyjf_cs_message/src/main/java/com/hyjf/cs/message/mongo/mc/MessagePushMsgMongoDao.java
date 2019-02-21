package com.hyjf.cs.message.mongo.mc;

import com.hyjf.am.resquest.admin.MessagePushNoticesRequest;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.util.GetMessageIdUtil;
import com.hyjf.cs.message.bean.mc.MessagePushMsg;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
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
        if (form.getNoticesTagIdSrch() != null) {
            criteria.and("tagId").is(form.getNoticesTagIdSrch());
        }
        if (StringUtils.isNotBlank(form.getNoticesTitleSrch())) {
            criteria.and("msgTitle").regex(form.getNoticesTitleSrch());
        }
        if (StringUtils.isNotBlank(form.getNoticesCodeSrch())) {
            criteria.and("msgCode").regex(form.getNoticesCodeSrch());
        }
        if (StringUtils.isNotBlank(form.getNoticesCreateUserNameSrch())) {
            criteria.and("createUserName").regex(form.getNoticesCreateUserNameSrch());
        }
        if (StringUtils.isNotBlank(form.getNoticesTerminalSrch())) {
            criteria.and("msgTerminal").regex(form.getNoticesTerminalSrch());
        }
        if (form.getNoticesSendStatusSrch() != null) {
            criteria.and("msgSendStatus").is(form.getNoticesSendStatusSrch());
        }

        criteria.and("msgDestinationType").is(0);
        if (form.getStartSendTimeSrch() != null || form.getEndSendTimeSrch() != null) {
            int startTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(form.getStartSendTimeSrch() + " 00:00:00");
            int endTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(form.getEndSendTimeSrch() + " 23:59:59");
            criteria.and("sendTime").gte(startTime).lte(endTime);
        }
        Query query = new Query(criteria);
        return (int)mongoTemplate.count(query,MessagePushMsg.class);
    }
    /**
     * 获取发送消息记录列表
     * @param form
     * @return
     */
    public List<MessagePushMsg> getRecordList(MessagePushNoticesRequest form, Integer offset, Integer limit){
        Criteria criteria = new Criteria();

        if (form.getNoticesTagIdSrch() != null) {
            criteria.and("tagId").is(form.getNoticesTagIdSrch());
        }
        if (StringUtils.isNotBlank(form.getNoticesTitleSrch())) {
            criteria.and("msgTitle").regex(form.getNoticesTitleSrch());
        }
        if (StringUtils.isNotBlank(form.getNoticesCodeSrch())) {
            criteria.and("msgCode").regex(form.getNoticesCodeSrch());
        }
        if (StringUtils.isNotBlank(form.getNoticesCreateUserNameSrch())) {
            criteria.and("createUserName").regex(form.getNoticesCreateUserNameSrch());
        }
        if (StringUtils.isNotBlank(form.getNoticesTerminalSrch())) {
            criteria.and("msgTerminal").regex(form.getNoticesTerminalSrch());
        }
        if (form.getNoticesSendStatusSrch() != null) {
            criteria.and("msgSendStatus").is(form.getNoticesSendStatusSrch());
        }
        if (form.getStartSendTimeSrch() != null || form.getEndSendTimeSrch() != null) {
            int startTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(form.getStartSendTimeSrch() + " 00:00:00");
            int endTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(form.getEndSendTimeSrch() + " 23:59:59");
            criteria.and("sendTime").gte(startTime).lte(endTime);
        }
        criteria.and("msgDestinationType").is(0);
        Query query = new Query(criteria);
        query.skip(offset).limit(limit);
        query.with(new Sort(Sort.Direction.DESC, "createTime"));
        return mongoTemplate.find(query,MessagePushMsg.class);
    }

    /**
     * 获取单条数据
     * @param request
     * @return
     */
    public MessagePushMsg getRecord(MessagePushNoticesRequest request){
        Criteria criteria = new Criteria();
        criteria.and("id").is(request.getIds());
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
        messagePushMsg.setCreateTime(GetDate.getNowTime10());
        messagePushMsg.setCreateUserName(request.getUserName());
        messagePushMsg.setLastupdateTime(GetDate.getNowTime10());
        messagePushMsg.setLastupdateUserName(request.getUserName());
        this.createData(request,messagePushMsg);
        mongoTemplate.save(messagePushMsg);
    }

    /**
     * 删除数据
     * @return
     */
    public void deleteRecord(String id){
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("id").is(id);
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
        query.addCriteria(Criteria.where("id").is(record.getId()));
        Update update = new Update();
        update.set("lastupdateTime", GetDate.getNowTime10());
        update.set("lastupdateUserName", record.getLastupdateUserName());
        if(record.getTagId()!=null){
            update.set("tagId", record.getTagId());
        }
        if(StringUtils.isNotBlank(record.getMsgTitle())){
            update.set("msgTitle", record.getMsgTitle());
        }
        if(StringUtils.isNotBlank(record.getMsgImageUrl())){
            update.set("msgImageUrl", record.getMsgImageUrl());
        }
        if (StringUtils.isNotBlank(record.getMsgActionUrl())) {
            update.set("msgActionUrl", record.getMsgActionUrl());
        }
        if(StringUtils.isNotBlank(record.getMsgContent())){
            update.set("msgContent", record.getMsgContent());
        }
        if(record.getMsgAction()!=null){
            update.set("msgAction", record.getMsgAction());
        }

        if(record.getMsgDestinationType()!=null){
            update.set("msgDestinationType", record.getMsgDestinationType());
        }
        if(record.getMsgSendType()!=null){
            update.set("msgSendType", record.getMsgSendType());
        }
        if(record.getSendTime()!=null){
            update.set("sendTime", record.getSendTime());
        }
        if(record.getPreSendTime()!=null){
            update.set("preSendTime", record.getPreSendTime());
        }
        update.set("msgTerminal",record.getMsgTerminal());
        this.update(query, update);
    }

    @Override
    protected Class<MessagePushMsg> getEntityClass() {
        return MessagePushMsg.class;
    }


    private void createData(MessagePushNoticesRequest form,MessagePushMsg record) {
        if (form.getMsgAction().intValue() == CustomConstants.MSG_PUSH_TEMP_ACT_0) {
            record.setMsgActionUrl("");
        }
        if (form.getMsgAction().intValue() == CustomConstants.MSG_PUSH_TEMP_ACT_1 || form.getMsgAction().intValue() == CustomConstants.MSG_PUSH_TEMP_ACT_3) {
            record.setMsgActionUrl(form.getNoticesActionUrl1());
        }
        if (form.getMsgAction().intValue() == CustomConstants.MSG_PUSH_TEMP_ACT_2) {
            record.setMsgActionUrl(form.getNoticesActionUrl2());
        }
        if (form.getMsgSendType().intValue() == CustomConstants.MSG_PUSH_SEND_TYPE_1) {
            record.setSendTime(GetDate.getMyTimeInMillis());
            if (org.apache.commons.lang.StringUtils.isNotEmpty(form.getNoticesPreSendTimeStr())) {
                try {
                    Integer time = GetDate.strYYYYMMDDHHMMSS2Timestamp2(form.getNoticesPreSendTimeStr());
                    if (time != 0) {
                        record.setPreSendTime(time);
                        record.setSendTime(time);
                    }
                } catch (Exception e) {

                }
            }
        } else {
            record.setPreSendTime(null);
            record.setSendTime(GetDate.getNowTime10());
        }
        String msgCodeKey = GetMessageIdUtil.getNewMsgCode(record.getTagCode());
        String msgCode = msgCodeKey.replace(RedisConstants.MSG_PUSH_CODE, "");
        record.setMsgCode(msgCode);// 设置ID
        record.setMsgSendStatus(CustomConstants.MSG_PUSH_MSG_STATUS_0);// 设置默认状态
        record.setMsgDestinationType(CustomConstants.MSG_PUSH_DESTINATION_TYPE_0);
    }


}
