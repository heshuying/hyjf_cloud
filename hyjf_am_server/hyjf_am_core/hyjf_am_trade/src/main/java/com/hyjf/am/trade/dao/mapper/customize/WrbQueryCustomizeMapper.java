/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.*;
import com.hyjf.am.vo.api.WrbDaySumCustomize;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author fq
 * @version WrbQueryCustomizeMapper, v0.1 2018/9/25 11:25
 */
public interface WrbQueryCustomizeMapper {
    /**
     * 根据标的ID查询标的列表
     * @param params
     * @return
     */
    List<WrbBorrowListCustomize> searchBorrowListByNid(Map<String,Object> params);


    /**
     * 获取某天投资情况汇总
     * @param timeStart
     * @param timeEnd
     * @return
     */
    WrbDaySumCustomize getDaySum(@Param("timeStart")Date timeStart, @Param("timeEnd") Date timeEnd);

    /**
     * 获取投资记录
     * @param params
     * @return
     */
    List<WrbInvestRecordCustomize> getInvestRecord(Map<String, Object> params);

    /**
     * 获取还款信息
     * @param nid
     * @return
     */
    List<WrbRecoverCustomize> getRecover(String nid);

    /**
     * 获取分期还款信息
     * @param nid
     * @return
     */
    List<WrbRecoverCustomize> getRecoverPlan(String nid);


    /**
     * 查询标的投资情况
     * @param borrowNid
     * @param date
     * @return
     */
    List<WrbBorrowTenderCustomize> selectWrbBorrowTender(@Param("borrowNid") String borrowNid, @Param("investTime")Date date);

    /**
     * 根据标的号和日期查询投资情况
     * @param borrowNid
     * @param date
     * @return
     */
    WrbBorrowTenderSumCustomize getBorrowTenderByBorrowNidAndTime(@Param("borrowNid") String borrowNid, @Param("investTime")Date date);
}
