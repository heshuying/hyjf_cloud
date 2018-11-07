/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.WrbBorrowListCustomize;
import com.hyjf.am.trade.dao.model.customize.WrbInvestRecordCustomize;
import com.hyjf.am.trade.dao.model.customize.WrbRecoverCustomize;
import com.hyjf.am.vo.api.WrbDaySumCustomize;
import org.apache.ibatis.annotations.Param;
import com.hyjf.am.trade.dao.model.customize.WrbBorrowTenderCustomize;
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
    WrbDaySumCustomize getDaySum(@Param("timeStart")Integer timeStart, @Param("timeEnd") Integer timeEnd);

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
}
