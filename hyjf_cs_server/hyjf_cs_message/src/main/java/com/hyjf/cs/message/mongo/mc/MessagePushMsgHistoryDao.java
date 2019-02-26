package com.hyjf.cs.message.mongo.mc;

import com.hyjf.am.resquest.admin.MessagePushHistoryRequest;
import com.hyjf.am.resquest.config.MessagePushErrorRequest;
import com.hyjf.am.vo.config.MessagePushTagVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.mc.MessagePushMsgHistory;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author xiasq
 * @version MessagePushMsgHistoryDao, v0.1 2018/5/4 10:49
 */
@Repository
public class MessagePushMsgHistoryDao extends BaseMongoDao<MessagePushMsgHistory> {

	public List<MessagePushMsgHistory> getMsgHistoryListByMsgCode(String msgCode, Integer startTime, Integer endTime) {
		Query query = new Query();
		// 发送成功
		Criteria criteria = new Criteria();
		criteria.and("msgSendStatus").is(CustomConstants.MSG_PUSH_SEND_STATUS_1);
		if (StringUtils.isNoneEmpty(msgCode)) {
			criteria.and("msgCode").is(msgCode);
		}
		if (startTime != null) {
			criteria.and("sendTime").gte(startTime);
			if (endTime != null) {
				criteria.lte(endTime);
			}
		}else{
			if (endTime != null) {
				criteria.and("sendTime").lte(endTime);
			}
		}
		query.addCriteria(criteria);
		return mongoTemplate.find(query, MessagePushMsgHistory.class);
	}

	@Override
	protected Class<MessagePushMsgHistory> getEntityClass() {
		return MessagePushMsgHistory.class;
	}

	/**
	 * 获得消息列表数量
	 * @param tagId 类型:0表示通知，1表示用户消息
	 * @param userId
	 * @param platform
	 * @return
	 */
	public Integer countMsgHistoryRecord(Integer tagId, Integer userId, String platform) {
		Criteria criteria = new Criteria();
		Criteria criteria1 = new Criteria();
		if (tagId != null) {
			criteria.and("msgDestinationType").is(tagId);
			criteria1.and("msgDestinationType").is(tagId);
		}
		criteria.and("msgUserId").is(0);
		criteria.and("msgSendStatus").is(CustomConstants.MSG_PUSH_SEND_STATUS_1);
		criteria1.and("msgSendStatus").is(CustomConstants.MSG_PUSH_SEND_STATUS_1);
		if (userId != null) {
			criteria1.and("msgUserId").is(userId);
		}
		if (platform != null) {
			criteria.and("msgTerminal").regex(platform);
			criteria1.and("msgTerminal").regex(platform);
		}
		Criteria cr = new Criteria();
		Query query = new Query(cr.orOperator(criteria, criteria1));
		return (int) mongoTemplate.count(query, MessagePushMsgHistory.class);
	}

	/**
	 * 获得消息列表
	 * @param tagId 类型:0表示通知，1表示用户消息
	 * @param userId
	 * @param platform
	 * @param limitStart
	 * @param limitEnd
	 * @return
	 */
	public List<MessagePushMsgHistory> getMsgHistoryList(Integer tagId, Integer userId, String platform, int limitStart,
			int limitEnd) {
		Criteria criteria = new Criteria();
		Criteria criteria1 = new Criteria();
		if (tagId != null) {
			criteria.and("msgDestinationType").is(tagId);
			criteria1.and("msgDestinationType").is(tagId);
		}
		criteria.and("msgUserId").is(0);
		criteria.and("msgSendStatus").is(CustomConstants.MSG_PUSH_SEND_STATUS_1);
		criteria1.and("msgSendStatus").is(CustomConstants.MSG_PUSH_SEND_STATUS_1);
		if (userId != null) {
			criteria1.and("msgUserId").is(userId);
		}
		if (platform != null) {
			criteria.and("msgTerminal").regex(platform);
			criteria1.and("msgTerminal").regex(platform);
		}
		Criteria cr = new Criteria();
		Query query = new Query(cr.orOperator(criteria, criteria1));
		query.with(new Sort(Sort.Direction.DESC, "sendTime"));
		query.skip(limitStart).limit(limitEnd);
		return mongoTemplate.find(query, MessagePushMsgHistory.class);
	}

	/**
	 * 获得具体信息
	 * @param msgId
	 * @return
	 */
	public MessagePushMsgHistory getMsgPushMsgHistoryById(String msgId) {
		Query query = new Query(new Criteria().and("id").is(msgId));
		return mongoTemplate.findOne(query, MessagePushMsgHistory.class);
	}

	/**
	 * 更新历史记录信息
	 * @param msgHistory
	 */
	public void updateMsgPushMsgHistory(MessagePushMsgHistory msgHistory) {
		mongoTemplate.save(msgHistory);
	}

	/**
	 * 获取历史记录条数
	 * @return
	 */
	public Integer countRecordList(MessagePushHistoryRequest form){
		Criteria criteria = new Criteria();
		if (form.getHistoryTagIdSrch()!=null) {
			criteria.and("tagId").is(form.getHistoryTagIdSrch());

		}
		if (StringUtils.isNotEmpty(form.getHistoryTitleSrch())) {
			criteria.and("msgTitle").regex(form.getHistoryTitleSrch());
		}
		if (StringUtils.isNotEmpty(form.getHistoryCodeSrch())) {
			criteria.and("msgCode").regex(form.getHistoryCodeSrch());
		}
		if (StringUtils.isNotEmpty(form.getHistoryCreateUserNameSrch())) {
			//	criteria.andCreateUserNameLike(form.getHistoryCreateUserNameSrch());
			criteria.and("msgDestination").regex(form.getHistoryCreateUserNameSrch());
		}
		if (StringUtils.isNotEmpty(form.getHistoryTerminalSrch())) {
			criteria.and("msgTerminal").regex(form.getHistoryTerminalSrch());
		}
		if (form.getHistorySendStatusSrch() != null) {
			criteria.and("msgSendStatus").is(form.getHistorySendStatusSrch());
		}
		if (form.getStartSendTimeSrch() != null || form.getEndSendTimeSrch() != null) {
			int startTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(form.getStartSendTimeSrch() + " 00:00:00");
			int endTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(form.getEndSendTimeSrch() + " 23:59:59");
			criteria.and("sendTime").gte(startTime).lte(endTime);
		}
		if (form.getHistoryFirstReadTerminalSrch() != null) {
				criteria.and("msgFirstreadPlat").is(Integer.valueOf(form.getHistoryFirstReadTerminalSrch()));
		}
		criteria.and("msgDestinationType").ne(CustomConstants.MSG_PUSH_SEND_STATUS_0);
		Query query = new Query(criteria);
		return (int)mongoTemplate.count(query,MessagePushMsgHistory.class);
	}
	/**
	 * 获取历史记录信息
	 * @return
	 */
	public List<MessagePushMsgHistory> getRecordList(MessagePushHistoryRequest form,Integer offset,Integer limit){
		Criteria criteria = new Criteria();
		if (form.getHistoryTagIdSrch()!=null) {
			criteria.and("tagId").is(form.getHistoryTagIdSrch());

		}
		if (StringUtils.isNotEmpty(form.getHistoryTitleSrch())) {
			criteria.and("msgTitle").regex(form.getHistoryTitleSrch());
		}
		if (StringUtils.isNotEmpty(form.getHistoryCodeSrch())) {
			criteria.and("msgCode").regex(form.getHistoryCodeSrch());
		}
		if (StringUtils.isNotEmpty(form.getHistoryCreateUserNameSrch())) {
			//	criteria.andCreateUserNameLike(form.getHistoryCreateUserNameSrch());
			criteria.and("msgDestination").regex(form.getHistoryCreateUserNameSrch());
		}
		if (StringUtils.isNotEmpty(form.getHistoryTerminalSrch())) {
			criteria.and("msgTerminal").regex(form.getHistoryTerminalSrch());
		}
		if (form.getHistorySendStatusSrch() != null) {
			criteria.and("msgSendStatus").is(form.getHistorySendStatusSrch());
		}
		if (form.getStartSendTimeSrch() != null || form.getEndSendTimeSrch() != null) {
			int startTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(form.getStartSendTimeSrch() + " 00:00:00");
			int endTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(form.getEndSendTimeSrch() + " 23:59:59");
			criteria.and("sendTime").gte(startTime).lte(endTime);
		}
		if (form.getHistoryFirstReadTerminalSrch() != null) {
			try {
				criteria.and("msgFirstreadPlat").is(Integer.parseInt(form.getHistoryFirstReadTerminalSrch()));
			} catch (NumberFormatException e) {
			}
		}
		criteria.and("msgDestinationType").ne(CustomConstants.MSG_PUSH_SEND_STATUS_0);
		Query query = new Query(criteria);
		query.skip(offset).limit(limit);
		query.with(new Sort(Sort.Direction.DESC, "createTime"));
		return mongoTemplate.find(query,MessagePushMsgHistory.class);
	}

	/**
	 * 获取列表记录数
	 *
	 * @return
	 */
	public Integer getRecordCount(MessagePushErrorRequest request) {
		Criteria criteria = new Criteria();
		// 条件查询
		if (StringUtils.isNoneBlank(request.getMsgTitleSrch())) {
			criteria.and("msgTitle").is(request.getMsgTitleSrch());
		}
		if(StringUtils.isNoneBlank(request.getTagIdSrch())){
			criteria.and("tagId").is(Integer.valueOf(request.getTagIdSrch()));
		}
		if(StringUtils.isNoneBlank(request.getMsgCodeSrch())){
			criteria.and("msgCode").is(request.getMsgCodeSrch());
		}

		// 发送时间
		if (request.getStartDateSrch() != null || request.getEndDateSrch() != null) {
			int startTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(request.getStartDateSrch() + " 00:00:00");
			int endTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(request.getEndDateSrch() + " 23:59:59");
			criteria.and("sendTime").gte(startTime).lte(endTime);
		}
		criteria.and("msgSendStatus").is(2);//发送失败
		Query query = new Query(criteria);
		return (int) mongoTemplate.count(query, MessagePushMsgHistory.class);
	}

	/**
	 * 获取列表
	 *
	 * @return
	 */
	public List<MessagePushMsgHistory> getRecordList(MessagePushErrorRequest request, int limitStart, int limitEnd) {
		Criteria criteria = new Criteria();
		Query query = new Query();
		// 条件查询
		if (StringUtils.isNotEmpty(request.getMsgTitleSrch())) {
			criteria.and("msgTitle").is(request.getMsgTitleSrch());
		}
		if(StringUtils.isNotEmpty(request.getTagIdSrch())){
			criteria.and("tagId").is(Integer.valueOf(request.getTagIdSrch()));
		}
		if(StringUtils.isNotEmpty(request.getMsgCodeSrch())){
			criteria.and("msgCode").is(request.getMsgCodeSrch());
		}
		// 发送时间
		if (request.getStartDateSrch() != null || request.getEndDateSrch() != null) {
			int startTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(request.getStartDateSrch() + " 00:00:00");
			int endTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(request.getEndDateSrch() + " 23:59:59");
			criteria.and("sendTime").gte(startTime).lte(endTime);
		}
		criteria.and("msgSendStatus").is(2);//发送失败
		if (limitStart != -1) {
			query.skip(limitStart).limit(limitEnd);
			query.addCriteria(criteria);
		}
		query.with(new Sort(Sort.Direction.DESC, "create_time"));
		return mongoTemplate.find(query, MessagePushMsgHistory.class);
	}

	/**
	 * 获取单个信息
	 *
	 * @return
	 */
	public MessagePushMsgHistory getRecord(String id) {
		Criteria criteria = new Criteria();
		// 条件查询
		if(StringUtils.isNotBlank(id)){
			//criteria.and("_id").is(new ObjectId(id));
			criteria.and("id").is(id);
		}
		Query query = new Query(criteria);
		MessagePushMsgHistory one = mongoTemplate.findOne(query, MessagePushMsgHistory.class);
		return one;
	}


	public void updateByPrimaryKeySelective(MessagePushMsgHistory record) {
		mongoTemplate.save(record);
	}

	/**
	 * 根据标签类型,获取时间范围内所有的msghistory
	 * @param messagePushTagVO
	 * @param todayStart
	 * @param todayEnd
	 * @return
	 */
	public List<MessagePushMsgHistory> getMessagePushMsgHistorys(MessagePushTagVO messagePushTagVO, Date todayStart, Date todayEnd) {
		Integer tagId = messagePushTagVO.getId();
		// 开始时间
		Integer beginTime = (int)(todayStart.getTime() / 1000L);
		// 结束时间
		Integer endTime = (int)(todayEnd.getTime() / 1000L);
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.and("tagId").is(tagId).and("sendTime").gte(beginTime).lte(endTime);
		query.addCriteria(criteria);
		return mongoTemplate.find(query, MessagePushMsgHistory.class);
	}
}
