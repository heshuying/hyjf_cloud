/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.TimerPoundageDetailListCustomize;

import java.util.List;

/**
 * @author wangjun
 * @version PoundageDetailCustomizeMapper, v0.1 2018/6/21 15:51
 */
public interface PoundageDetailCustomizeMapper {

    List<TimerPoundageDetailListCustomize> selectTimerPoundageDetailList();

    /**
     * 插入手续费分账明细数据
     */
    void insertTimerPoundageDetailList(List<TimerPoundageDetailListCustomize> list);

    /**
     * 插入手续费分账总数据
     */
    void insertTimerPoundageList();
}
