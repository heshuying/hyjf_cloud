/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.auto.CouponConfigMapper;
import com.hyjf.am.trade.dao.model.auto.CouponConfig;
import com.hyjf.am.trade.dao.model.auto.CouponConfigExample;
import com.hyjf.am.trade.service.CouponConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yaoy
 * @version CouponConfigServiceImpl, v0.1 2018/6/19 19:30
 */
@Service
public class CouponConfigServiceImpl implements CouponConfigService {

    @Autowired
    private CouponConfigMapper couponConfigMapper;
    @Override
    public List<CouponConfig> selectCouponConfig(String couponCode) {
        CouponConfigExample cExample = new CouponConfigExample();
        cExample.createCriteria().andCouponCodeEqualTo(couponCode);
        List<CouponConfig> couponConfigList = couponConfigMapper.selectByExample(cExample);
        return couponConfigList;
    }
}
