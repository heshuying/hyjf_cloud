/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import java.util.List;

import com.hyjf.am.config.dao.model.auto.Event;
import com.hyjf.am.resquest.admin.EventsRequest;

/**
 * 公司记事
 * 
 * @author fuqiang
 * @version EventService, v0.1 2018/7/12 9:09
 */
public interface EventService {
	/**
	 * 根据调价查询公司纪事
	 *
	 * @param request
	 * @return
	 */
	List<Event> searchAction(EventsRequest request);

	/**
	 * 添加公司纪事
	 *
	 * @param request
	 */
	void insertAction(EventsRequest request);

	/**
	 * 修改公司纪事
	 *
	 * @param request
	 */
	void updateAction(EventsRequest request);

	/**
	 * 根据id获取公司纪事
	 *
	 * @param id
	 * @return
	 */
	Event getRecord(Integer id);

	/**
	 * 根据id删除公司纪事
	 *
	 * @param id
	 */
    void deleteById(Integer id);


	/**
	 * 根据时间查询公司纪事
	 * @param begin
	 * @param end
	 * @param year
	 * @return
	 */
	 List<Event> getEvents(int begin, int end,int year);



}