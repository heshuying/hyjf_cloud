/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.CouponConfig;
import com.hyjf.am.trade.dao.model.customize.trade.CouponConfigCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author yaoy
 * @version CouponConfigService, v0.1 2018/6/19 19:25
 */
public interface CouponConfigService {
    /**
     * 根据优惠券编号查找优惠券配置
     * @param couponCode
     * @return
     */
    CouponConfig selectCouponConfig(String couponCode);

    /**
     * 获取记录数
     * @param mapParam
     * @return
     */
    int countRecord(Map<String, Object> mapParam);

    /**
     * 获取列表
     * @param mapParam
     * @param offset
     * @param limit
     * @return
     */
    List<CouponConfigCustomize> getRecordList(Map<String, Object> mapParam, int offset, int limit);

    /**
     * 获取编辑页面
     * @param id
     * @return
     */
    CouponConfig getCouponConfig(int id);

    /**
     * 保存编辑信息
     * @param couponConfig
     * @return
     */
    Map<String,Object> saveCouponConfig(CouponConfig couponConfig);

    /**
     * 添加发行优惠券信息
     * @param couponConfig
     * @return
     */
    Map<String,Object> insertAction(CouponConfig couponConfig);

    /**
     *删除发行信息
     * @param id
     * @return
     */
    Map<String,Object> deleteCouponConfig(int id);


}
