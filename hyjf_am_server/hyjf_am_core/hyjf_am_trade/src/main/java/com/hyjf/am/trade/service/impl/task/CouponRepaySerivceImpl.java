/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.task;

import com.hyjf.am.trade.dao.mapper.customize.trade.CouponRecoverCustomizeMapper;
import com.hyjf.am.trade.dao.model.customize.trade.CouponRecoverCustomize;
import com.hyjf.am.trade.service.task.CouponRepaySerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaoy
 * @version CouponRepaySerivceImpl, v0.1 2018/6/21 18:14
 */
@Service
public class CouponRepaySerivceImpl implements CouponRepaySerivce {

    @Autowired
    private CouponRecoverCustomizeMapper couponRecoverCustomizeMapper;

    @Override
    public List<CouponRecoverCustomize> selectCouponInterestWaitToday(long timeStart, long timeEnd) {
        Map<String,Object> map = new HashMap<>();
        map.put("timeStart",timeStart);
        map.put("timeEnd",timeEnd);
        List<CouponRecoverCustomize> couponRecoverCustomizes = couponRecoverCustomizeMapper.selectCouponInterestWaitToday(map);
        return couponRecoverCustomizes;
    }

    @Override
    public BigDecimal selectCouponInterestReceivedToday(long timeStart, long timeEnd) {
        Map<String,Object> map = new HashMap<>();
        map.put("timeStart",timeStart);
        map.put("timeEnd",timeEnd);
        BigDecimal interestReceived = couponRecoverCustomizeMapper.selectCouponInterestReceivedToday(map);
        return interestReceived;
    }
}
