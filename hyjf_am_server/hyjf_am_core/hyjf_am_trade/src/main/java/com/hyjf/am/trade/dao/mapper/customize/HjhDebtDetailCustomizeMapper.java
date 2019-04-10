/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.dao.model.auto.HjhDebtDetail;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 汇计划债权详情CustomizeMapper
 * @author liuyang
 * @version HjhDebtDetailCustomizeMapper, v0.1 2018/6/26 17:57
 */
public interface HjhDebtDetailCustomizeMapper {

    /**
     * 根据计划加入订单号,查询供清算使用的债权明细
     *
     * @param accedeOrderId
     * @return
     */
    public List<HjhDebtDetail> selectDebtDetailForLiquidation(String accedeOrderId);

    /**
     * 将已经清算的债权清算状态修改为1
     * @param orderId
     * @return
     */
    public int updateDetailDelFlagToOne(String orderId);

    /**
     * 查询当前计息周期的债权详情
     *
     * @param orderId
     * @return
     */
    public HjhDebtDetail selectDebtDetailCurRepayPeriod(String orderId);

    /**
     * 根据出借订单号和还款期数 查询债权详情
     * @param orderId
     * @param repayPeriod
     * @return
     */
    public HjhDebtDetail selectDebtDetailLastRepay(String orderId, int repayPeriod);

    /**
     * 查询出借订单号未还款的债权详情
     * @param orderId
     * @return
     */
    public List<HjhDebtDetail> selectDebtDetailNoRepay(String orderId);

    /**
     * 检索应还时间>当前时间的债权
     * @param orderId
     * @return
     */
    HjhDebtDetail selectDebtDetailCurPeriod(String orderId);

    /**
     * 更新计划加入订单的清算进度,清算服务费,清算时公允价值,计划订单的当前公允价值
     * @param hjhAccede
     * @return
     */
    int updateLiquidationHjhAccede(HjhAccede hjhAccede);

    /**
     * 每小时计算计划加入订单的清算进度,清算服务费,计划订单的当前公允价值
     * @param hjhAccede
     * @return
     */
    int updateCalculateHjhAccede(HjhAccede hjhAccede);

    /**
     * 检索还款日为T日（预估日）  当前有效的债权
     * @param expectTime
     * @return
     */
    List<HjhDebtDetail> selectDebtDetailToDate(@Param("expectTime") String expectTime);
}
