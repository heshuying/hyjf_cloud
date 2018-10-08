/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.admin.BorrowFullRequest;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.customize.BorrowFullCustomize;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version BorrowFullCustomizeMapper, v0.1 2018/7/6 13:37
 */
public interface BorrowFullCustomizeMapper {
    /**
     * 复审记录 总数COUNT
     *
     * @param borrowFullRequest
     * @return
     */
    Integer countBorrowFull(BorrowFullRequest borrowFullRequest);

    /**
     * 复审记录
     *
     * @param borrowFullRequest
     * @return
     */
    List<BorrowFullCustomize> selectBorrowFullList(BorrowFullRequest borrowFullRequest);

    /**
     * 复审记录 总数COUNT
     *
     * @param borrowNid
     * @return
     */
    Integer countFullList(@Param("borrowNid") String borrowNid);

    /**
     * 复审中的列表
     *
     * @param borrowNid
     * @param limitStart
     * @param limitEnd
     * @return
     */
    List<BorrowFullCustomize> selectFullList(@Param("borrowNid") String borrowNid, @Param("limitStart") int limitStart, @Param("limitEnd") int limitEnd);

    /**
     * 复审详细
     *
     * @param borrowNid
     * @return
     */
    BorrowFullCustomize selectFullInfo(@Param("borrowNid") String borrowNid);

    /**
     * 合计
     *
     * @param borrowNid
     * @return
     */
    BorrowFullCustomize sumAmount(@Param("borrowNid") String borrowNid);

    /**
     * 获取自动复审的记录
     *
     * @param borrowFullRequest
     * @return
     */
    List<Borrow> selectAutoFullList(BorrowFullRequest borrowFullRequest);

    /**
     * 查看新的管理费率和收益差率
     *
     * @param params
     * @return
     */
    Map<String, Object> selectFeeMapByParams(Map<String, Object> params);

    /**
     * 检索项目的服务费率
     *
     * @param params
     * @return
     */
    String selectServiceRateByParams(Map<String, Object> params);

    /**
     * 检索项目的管理费率
     *
     * @param params
     * @return
     */
    String selectManChargeRateByParams(Map<String, Object> params);

    /**
     * 检索项目的收益差率
     *
     * @param params
     * @return
     */
    String selectReturnRateByParams(Map<String, Object> params);

    /**
     * 取得金额合计
     *
     * @param borrowFullRequest
     * @return
     */
    BorrowFullCustomize sumAccount(BorrowFullRequest borrowFullRequest);


    /**
     * 检索项目的逾期费率
     *
     * @param params
     * @return
     */
    String selectLateInterestRateByParams(Map<String, Object> params);

    /**
     * 检索项目的逾期天数
     *
     * @param params
     * @return
     */
    String selectLateFreeDaysByParams(Map<String, Object> params);

    /**
     * 根据用户id查询用户姓名
     *
     * @param userId
     * @return
     */
    String getUserNameByUserId(@Param("userId") Integer userId);
}
