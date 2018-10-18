/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.mc;

import com.hyjf.am.resquest.message.MessagePushTemplateStaticsRequest;
import com.hyjf.cs.message.bean.mc.MessagePushTemplateStatics;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author fuqiang
 * @version MessagePushTemplateStaticsDao, v0.1 2018/6/22 9:27
 */
@Repository
public class MessagePushTemplateStaticsDao extends BaseMongoDao<MessagePushTemplateStatics> {

	/**
	 * 根据消息编码获取统计数据
	 * 
	 * @param msgCode
	 * @return
	 */
	public List<MessagePushTemplateStatics> getMessagePushTemplateStaticsByMsgCode(String msgCode) {
		if (msgCode != null) {
			Query query = new Query();
			Criteria criteria = Criteria.where("msgCode").is(msgCode);
			query.addCriteria(criteria);
			return mongoTemplate.find(query, getEntityClass());
		} else {
			return mongoTemplate.findAll(getEntityClass());
		}
	}

	/**
	 * 根据时间获取模板统计数据
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<MessagePushTemplateStatics> getTemplateStaticsListByTime(String startTime, String endTime) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		if (startTime != null) {
			criteria.and("createTime").gte(startTime);
			if(endTime != null){
				criteria.lte(endTime);
			}
		}else if (endTime != null) {
			criteria.and("createTime").lte(endTime);
		}

		query.addCriteria(criteria);
		return mongoTemplate.find(query, getEntityClass());
	}

	/**
	 *查询模板消息统计报表
	 * @param request
	 * @return
	 */
	public List<MessagePushTemplateStatics> selectTemplateStatics(MessagePushTemplateStaticsRequest request) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		if (StringUtils.isNotBlank(request.getStartDateSrch()) && StringUtils.isNotBlank(request.getEndDateSrch())) {
			criteria.and("sendTime").gte(request.getStartDateSrch() + " 00:00:00")
					.lte(request.getEndDateSrch() + " 23:59:59");
		}
		if (StringUtils.isNotBlank(request.getMsgTitleSrch())) {
			criteria.and("msgTitle").is(request.getMsgTitleSrch());
		}
		if (StringUtils.isNotBlank(request.getMsgCodeSrch())) {
			criteria.and("msgCode").is(request.getMsgCodeSrch());
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
		return mongoTemplate.find(query, getEntityClass());
	}

	@Override
	protected Class<MessagePushTemplateStatics> getEntityClass() {
		return MessagePushTemplateStatics.class;
	}


}
