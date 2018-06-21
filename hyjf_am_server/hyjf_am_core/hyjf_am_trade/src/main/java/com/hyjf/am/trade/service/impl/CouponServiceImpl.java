package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.customize.trade.CouponCustomizeMapper;
import com.hyjf.am.trade.dao.model.customize.trade.CouponCustomize;
import com.hyjf.am.trade.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 优惠券相关
 * @Author sunss
 * @Version v0.1
 * @Date 2018/6/19 16:52
 */
@Service
public class CouponServiceImpl implements CouponService {

	@Autowired
	private CouponCustomizeMapper couponCustomizeMapper;

	/**
	 * @param couponGrantId
	 * @param userId
	 * @Description 根据用户ID和优惠券编号查询优惠券
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/19 16:51
	 */
	@Override
	public CouponCustomize getCouponUser(String couponGrantId, Integer userId) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("couponGrantId", couponGrantId);
		paraMap.put("userId", userId);
		List<CouponCustomize> list = this.couponCustomizeMapper.getCouponUser(paraMap);
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}
}
