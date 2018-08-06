/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.coupon.impl;

import com.hyjf.am.trade.dao.mapper.auto.CouponRecoverMapper;
import com.hyjf.am.trade.dao.model.auto.CouponRecover;
import com.hyjf.am.trade.service.front.coupon.CouponRecoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yaoy
 * @version CouponRecoverServiceImpl, v0.1 2018/6/26 10:54
 */
@Service
public class CouponRecoverServiceImpl implements CouponRecoverService {

    @Autowired
    CouponRecoverMapper couponRecoverMapper;
    @Override
    public int updateByPrimaryKeySelective(CouponRecover cr) {
        int result = couponRecoverMapper.updateByPrimaryKeySelective(cr);
        if (result >= 0) {
            return result;
        }
        return 0;
    }
}

