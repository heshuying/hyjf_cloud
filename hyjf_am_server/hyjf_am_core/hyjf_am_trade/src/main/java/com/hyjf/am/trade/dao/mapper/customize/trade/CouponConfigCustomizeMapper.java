package com.hyjf.am.trade.dao.mapper.customize.trade;

import com.hyjf.am.trade.dao.model.customize.trade.CouponConfigCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author yaoyong
 */
public interface CouponConfigCustomizeMapper {

	/**
	 * 获取记录数
	 * @param mapParam
	 * @return
	 */
	int countCouponConfig(Map<String, Object> mapParam);

	/**
	 * 查询列表
	 * @param mapParam
	 * @return
	 */
	List<CouponConfigCustomize> selectRecordList(Map<String, Object> mapParam);
}