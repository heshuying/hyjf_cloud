package com.hyjf.am.config.dao.mapper.customize;

import java.util.List;

import com.hyjf.am.config.dao.model.auto.Event;
import com.hyjf.am.config.dao.model.customize.ContentEventsCustomize;

public interface ContentEventsCustomizeMapper {

	/**
	 * 根据条件查询列表
	 * @param 
	 * @return
	 */
	List<Event> selectContentEvents(ContentEventsCustomize contentEventsCustomize);


}