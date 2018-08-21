package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.BorrowRepayAgreementCustomize;

import java.util.List;
import java.util.Map;

/**
 * @version BorrowRepayAgreementCustomizeMapper, v0.1 2018/8/14 17:16
 * @Author: Zha Daojian
 */
public interface BorrowRepayAgreementCustomizeMapper {

    /**
     * 垫付协议申请明细列表页--分期列表总数量
     *
     * @param map
     * @return
     */
    int countBorrowRepayPlan(Map map);

    /**
     * 垫付协议申请明细列表页--分期列表
     *
     * @param map
     * @return
     */
    List<BorrowRepayAgreementCustomize> selectBorrowRepayPlan(Map map);

    /**
     * 垫付协议申请明细列表页--列表总数量
     *
     * @param map
     * @return
     */
    int countBorrowRepay(Map map);

    /**
     * 垫付协议申请明细列表页--列表
     *
     * @param map
     * @return
     */
    List<BorrowRepayAgreementCustomize> selectBorrowRepay(Map map);
}
