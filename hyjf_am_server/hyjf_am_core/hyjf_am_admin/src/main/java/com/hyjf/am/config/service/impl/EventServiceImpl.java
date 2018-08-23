/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.EventMapper;
import com.hyjf.am.config.dao.mapper.customize.ContentEventsCustomizeMapper;
import com.hyjf.am.config.dao.model.auto.Event;
import com.hyjf.am.config.dao.model.auto.EventExample;
import com.hyjf.am.config.dao.model.customize.ContentEventsCustomize;
import com.hyjf.am.config.service.EventService;
import com.hyjf.am.resquest.admin.EventsRequest;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fuqiang
 * @version EventServiceImpl, v0.1 2018/7/12 9:09
 */
@Service
public class EventServiceImpl implements EventService {
	@Autowired
	private EventMapper eventMapper;

	@Autowired
	private ContentEventsCustomizeMapper contentEventsCustomizeMapper;
	@Override
	public List<Event> searchAction(EventsRequest request) {
		EventExample example = new EventExample();
		EventExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(request.getStartTime())) {
			criteria.andCreateTimeGreaterThanOrEqualTo(GetDate.str2Date(request.getStartTime(), GetDate.date_sdf));
		}
		if (StringUtils.isNotBlank(request.getEndTime())) {
			criteria.andCreateTimeLessThanOrEqualTo(GetDate.str2Date(request.getEndTime(), GetDate.date_sdf));
		}
		example.setOrderByClause("act_time DESC, create_time DESC");
		return eventMapper.selectByExample(example);
	}

	@Override
	public void insertAction(EventsRequest request) {
		Event event = new Event();
		BeanUtils.copyProperties(request, event);
		eventMapper.insert(event);
	}

	@Override
	public void updateAction(EventsRequest request) {
		Event event = new Event();
		BeanUtils.copyProperties(request, event);
		eventMapper.updateByPrimaryKey(event);
	}

	@Override
	public Event getRecord(Integer id) {
		return eventMapper.selectByPrimaryKey(id);
	}

	@Override
	public void deleteById(Integer id) {
		eventMapper.deleteByPrimaryKey(id);
	}

    @Override
    public List<Event> getEvents(int begin, int end, int year) {
		ContentEventsCustomize contentEventsCustomize=new ContentEventsCustomize();
		contentEventsCustomize.setStartCreate(begin);
		contentEventsCustomize.setEndCreate(end);
		contentEventsCustomize.setEventYear(year);
		return contentEventsCustomizeMapper.selectContentEvents(contentEventsCustomize);

    }



}
