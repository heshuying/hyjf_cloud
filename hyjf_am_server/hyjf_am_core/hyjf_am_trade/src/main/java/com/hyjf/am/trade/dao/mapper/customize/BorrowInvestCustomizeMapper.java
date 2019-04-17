/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.admin.BorrowInvestRequest;
import com.hyjf.am.trade.dao.model.customize.BorrowInvestCustomize;
import com.hyjf.am.trade.dao.model.customize.BorrowListCustomize;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version BorrowInvestCustomizeMapper, v0.1 2018/7/10 9:24
 */
public interface BorrowInvestCustomizeMapper {
    /**
     * 出借明细列表
     *
     * @param borrowInvestRequest
     * @return
     */
    List<BorrowInvestCustomize> selectBorrowInvestList(BorrowInvestRequest borrowInvestRequest);

    /**
     * 出借明细记录 总数COUNT
     *
     * @param borrowInvestRequest
     * @return
     */
    int countBorrowInvest(BorrowInvestRequest borrowInvestRequest);

    /**
     * 导出出借明细列表
     *
     * @param borrowInvestRequest
     * @return
     */
    List<BorrowInvestCustomize> exportBorrowInvestList(BorrowInvestRequest borrowInvestRequest);

    /**
     * 出借金额合计
     *
     * @param borrowInvestRequest
     * @return
     */
    String selectBorrowInvestAccount(BorrowInvestRequest borrowInvestRequest);

    /**
     * 出借金额合计值取得
     *
     * @param borrowInvestRequest
     * @return
     */
    String sumBorrowInvest(BorrowInvestRequest borrowInvestRequest);

    /**
     * 获取借款列表
     *
     * @param borrowNid
     * @return
     */
    List<BorrowListCustomize> selectBorrowList(@Param("borrowNid") String borrowNid);

    /**
     * 获取用户出借数量
     * @param userId
     * @return
     */
    Integer selectTenderCount(Integer userId);

    /**
     * 获取五一活动期间出借总额
     * @return
     * @param startTime
     * @param endTime
     */
    BigDecimal getActivityInvestAmount(Date startTime, Date endTime);

    BigDecimal getAnnualInvestAmount(Map<String, Object> map);

    BigDecimal getPlanAnnualAmount(Map<String, Object> map);
}
