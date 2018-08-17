/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.task;

import com.hyjf.am.resquest.admin.CouponRepayRequest;
import com.hyjf.am.trade.dao.model.auto.CouponRepayMonitor;
import com.hyjf.am.trade.dao.model.customize.AdminCouponRepayMonitorCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author yaoy
 * @version CouponRepayMonitorService, v0.1 2018/6/22 9:40
 */
public interface CouponRepayMonitorService {
    /**
     * 根据日期查询表
     * @param nowDay
     * @return
     */
    List<CouponRepayMonitor> selectCouponRepayMonitor(String nowDay);

    List<AdminCouponRepayMonitorCustomize> selectCouponRepayMonitorPage(Map<String, Object> paraMap);

    /**
     * 插入表
     * @param couponRepayMonitor
     * @return
     */
    int insertCouponRepayMonitor(CouponRepayMonitor couponRepayMonitor);

    /**
     * 更新表
     * @param couponRepayMonitor
     * @return
     */
    int updateCouponRepayMonitor(CouponRepayMonitor couponRepayMonitor);

    /**
     * 加息卷统计
     * @param form
     * @return
     */
    Integer countRecordTotal(CouponRepayRequest form);

    List<AdminCouponRepayMonitorCustomize> selectInterestSum(Map<String, Object> paraMap);
}
