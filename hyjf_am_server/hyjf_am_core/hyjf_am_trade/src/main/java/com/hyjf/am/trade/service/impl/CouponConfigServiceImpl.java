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
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author yaoy
 * @version CouponConfigServiceImpl, v0.1 2018/6/19 19:30
 */
@Service
public class CouponConfigServiceImpl implements CouponConfigService {

	@Autowired
	private CouponConfigMapper couponConfigMapper;

	/**
	 * 根据优惠券编号查找优惠券配置
	 * 
	 * @param couponCode
	 * @return
	 */
	@Override
	public CouponConfig selectCouponConfig(String couponCode) {
		CouponConfigExample cExample = new CouponConfigExample();
		cExample.createCriteria().andCouponCodeEqualTo(couponCode);
		List<CouponConfig> couponConfigList = couponConfigMapper.selectByExample(cExample);
		if (!CollectionUtils.isEmpty(couponConfigList)) {
            return couponConfigList.get(0);
        }
		return null;
	}
}
