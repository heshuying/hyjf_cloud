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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
		if (request.getStartTime() != null && request.getEndTime() != null) {
			criteria.andEventTimeGreaterThanOrEqualTo(GetDate.dateToString2(request.getStartTime()));
			criteria.andEventTimeLessThanOrEqualTo(GetDate.dateToString2(request.getEndTime()));
		}
		if (request.getCurrPage() > 0 && request.getPageSize() > 0) {
			int limitStart = (request.getCurrPage() - 1) * (request.getPageSize());
			int limitEnd = request.getPageSize();
			example.setLimitStart(limitStart);
			example.setLimitEnd(limitEnd);
		}
		example.setOrderByClause("event_time DESC, create_time DESC");
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

	@Override
	public int selectCount(EventsRequest request) {
		EventExample example = new EventExample();
		EventExample.Criteria criteria = example.createCriteria();
		if (request.getStartTime() != null && request.getEndTime() != null) {
			criteria.andEventTimeGreaterThanOrEqualTo(GetDate.dateToString2(request.getStartTime()));
			criteria.andEventTimeLessThanOrEqualTo(GetDate.dateToString2(request.getEndTime()));
		}
		example.setOrderByClause("event_time DESC, create_time DESC");
		return eventMapper.countByExample(example);
	}

	@Override
	public String selectMinEventTime() {
		return contentEventsCustomizeMapper.selectMinEventTime();
	}


}
