package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.AdminTransferExceptionLogCustomize;

import java.util.List;
import java.util.Map;

public interface AdminTransferExceptionLogCustomizeMapper {

	/**
	 * 获取优惠券列表
	 * 
	 * @return
	 */
	List<AdminTransferExceptionLogCustomize> selectTransferExceptionList(Map<String, Object> paraMap);
	
	/**
	 * 获取优惠券列表记录数
	 * 
	 * @return
	 */
	Integer countTransferException(Map<String, Object> paraMap);
	
	/**
	 * 取得优惠券编号
	 * @param paraMap
	 * @return
	 */
	String getCouponUserCode(Map<String, Object> paraMap);
}