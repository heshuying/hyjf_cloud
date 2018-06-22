/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.HolidaysConfigVO;

import java.util.List;

/**
 * @author yaoy
 * @version HolidaysConfigClient, v0.1 2018/6/20 16:10
 */
public interface HolidaysConfigClient {
    /**
     * 查询假期配置表
     * @return
     * @param orderByClause
     */
    List<HolidaysConfigVO> selectHolidaysConfig(String orderByClause);
}
