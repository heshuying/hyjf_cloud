/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.dao.mapper.customize.market;

import java.util.List;
import java.util.Map;

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
     * @param map
     * @return
     */
    SellDaily selectPrimaryDivisionSum(Map<String, Object> map);

    /**
     * 根据类型查询合计  type:3
     * @param dateStr
     * @return
     */
    SellDaily selectAllSum(String dateStr);

    /**
     * 批量插入
     * @param currentMonthTotalTenderList
     */
    void batchInsertSellDaily(List<SellDaily> currentMonthTotalTenderList);

    /**
     * 计算第四、六、十列速率,第十六列净资金流
     */
    void calculateRate(String dateStr);
}
