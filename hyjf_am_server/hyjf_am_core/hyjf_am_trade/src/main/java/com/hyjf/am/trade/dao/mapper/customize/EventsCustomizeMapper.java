package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.ContentEventsCustomize;
import com.hyjf.am.trade.dao.model.customize.Event;

/**
 * @author lisheng
 * @version EventsCustomizeMapper, v0.1 2018/8/2 14:01
 */

public interface EventsCustomizeMapper {
    /**
     * 根据条件查询列表
     * @param
     * @return
     */
    Event selectZong(ContentEventsCustomize contentEventsCustomize);
    /**
     * 根据 查询投资百分比
     * @param
     * @return
     */
    Event selectPercentage(ContentEventsCustomize contentEventsCustomize);
}
