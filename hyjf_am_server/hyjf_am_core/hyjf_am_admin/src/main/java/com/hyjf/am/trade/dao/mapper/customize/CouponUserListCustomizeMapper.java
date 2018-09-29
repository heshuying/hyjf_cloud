package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.CouponUserListCustomize;

import java.util.List;
import java.util.Map;

/**
 * 
 * 此处为类说明
 * @author jijun
 * @date 20180705
 */
public interface CouponUserListCustomizeMapper {

	/**
	 * 获取优惠券列表
	 * @return
	 */
	List<CouponUserListCustomize> selectCouponUserList(Map<String, Object> paraMap);
	
	
	/**
	 * 获取优惠券列表记录数
	 * @return
	 */
	Integer countCouponUserList(Map<String, Object> paraMap);
	
	

}