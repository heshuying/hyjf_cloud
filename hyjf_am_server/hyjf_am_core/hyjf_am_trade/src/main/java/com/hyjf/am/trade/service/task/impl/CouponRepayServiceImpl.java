/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.task.impl;

import com.hyjf.am.trade.dao.mapper.customize.BatchCouponTimeoutCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.BatchTyjRepayCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.CouponRecoverCustomizeMapper;
import com.hyjf.am.trade.dao.model.customize.BatchCouponTimeoutCommonCustomize;
import com.hyjf.am.trade.dao.model.customize.CouponRecoverCustomize;
import com.hyjf.am.trade.service.task.CouponRepayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaoy
 * @version CouponRepayServiceImpl, v0.1 2018/6/21 18:14
 */
@Service
public class CouponRepayServiceImpl implements CouponRepayService {

    @Autowired
    private CouponRecoverCustomizeMapper couponRecoverCustomizeMapper;
    @Autowired
    private BatchCouponTimeoutCustomizeMapper batchCouponTimeoutCustomizeMapper;
    @Autowired
    private BatchTyjRepayCustomizeMapper batchTyjRepayCustomizeMapper;

    @Override
    public List<CouponRecoverCustomize> selectCouponInterestWaitToday(long timeStart, long timeEnd) {
        Map<String, Object> map = new HashMap<>();
        map.put("timeStart", timeStart);
        map.put("timeEnd", timeEnd);
        List<CouponRecoverCustomize> couponRecoverCustomizes = couponRecoverCustomizeMapper.selectCouponInterestWaitToday(map);
        return couponRecoverCustomizes;
    }

    @Override
    public BigDecimal selectCouponInterestReceivedToday(long timeStart, long timeEnd) {
        Map<String, Object> map = new HashMap<>();
        map.put("timeStart", timeStart);
        map.put("timeEnd", timeEnd);
        BigDecimal interestReceived = couponRecoverCustomizeMapper.selectCouponInterestReceivedToday(map);
        return interestReceived;
    }

    @Override
    public List<BatchCouponTimeoutCommonCustomize> selectCouponQuota(int threeBeginDate, int threeEndDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("threeBeginDate", threeBeginDate);
        map.put("threeEndDate", threeEndDate);
        List<BatchCouponTimeoutCommonCustomize> batchCouponTimeoutCommonCustomizes = batchCouponTimeoutCustomizeMapper.selectCouponQuota(map);
        return null;
    }

    @Override
    public List<String> selectNidForCouponOnly(Map<String, Object> paramMap) {
        return batchTyjRepayCustomizeMapper.selectNidForTyj(paramMap);
    }
}
