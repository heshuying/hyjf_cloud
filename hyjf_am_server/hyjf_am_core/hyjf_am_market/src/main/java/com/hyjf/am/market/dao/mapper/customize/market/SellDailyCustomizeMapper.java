/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.dao.mapper.customize.market;

import com.hyjf.am.market.dao.model.auto.SellDaily;

/**
 * @author yaoyong
 * @version SellDailyCustomizeMapper, v0.1 2018/11/19 9:22
 */
public interface SellDailyCustomizeMapper {
    /**
     * 根据类型查询合计  type:1
     * @param formatDateStr
     * @return
     */
    SellDaily selectOCSum(String formatDateStr);

    /**
     * 根据类型查询合计  type:2
     * @param dateStr
     * @param drawOrder
     * @return
     */
    SellDaily selectPrimaryDivisionSum(String dateStr, int drawOrder);

    /**
     * 根据类型查询合计  type:3
     * @param dateStr
     * @return
     */
    SellDaily selectAllSum(String dateStr);
}
