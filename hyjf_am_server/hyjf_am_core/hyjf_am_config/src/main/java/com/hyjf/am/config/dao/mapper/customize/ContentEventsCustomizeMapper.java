package com.hyjf.am.config.dao.mapper.customize;

import com.hyjf.am.config.dao.model.auto.Event;
import com.hyjf.am.config.dao.model.customize.ContentEventsCustomize;

import java.util.List;

public interface ContentEventsCustomizeMapper {

	/**
	 * 根据条件查询列表
	 * @param 
	 * @return
	 */
	List<Event> selectContentEvents(ContentEventsCustomize contentEventsCustomize);


    String selectMinEventTime();
}