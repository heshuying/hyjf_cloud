/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize.trade;

/**
 * @author wangjun
 * @version PoundageDetailCustomizeMapper, v0.1 2018/6/21 15:51
 */
public interface PoundageDetailCustomizeMapper {

    /**
     * 插入手续费分账明细数据
     */
    void insertTimerPoundageDetailList();

    /**
     * 插入手续费分账总数据
     */
    void insertTimerPoundageList();
}
