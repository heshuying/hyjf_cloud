/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize.trade;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * @author fuqiang
 * @version BorrowTenderCustomizeMapper, v0.1 2018/7/17 9:45
 */
public interface BorrowTenderCustomizeMapper {
	/**
	 * 获取utm注册用户投资次数
	 * @param list utm注册用户userid集合
	 * @param dayStart
	 * @param dayEnd
	 * @return
	 */
	Integer getUtmTenderNum(@Param("list") List<Integer> list, @Param("dayStart") String dayStart,
			@Param("dayEnd") String dayEnd);

	/**
	 * 获取utm注册用户HZT投资额
	 * @param list utm注册用户userid集合
	 * @param dayStart
	 * @param dayEnd
	 * @return
	 */
	BigDecimal getHztTenderPrice(@Param("list") List<Integer> list, @Param("dayStart") String dayStart,
			@Param("dayStart") String dayEnd);

	/**
	 * 获取utm注册用户HXF投资额
	 * @param list utm注册用户userid集合
	 * @param dayStart
	 * @param dayEnd
	 * @return
	 */
	BigDecimal getHxfTenderPrice(@Param("list") List<Integer> list, @Param("dayStart") String dayStart,
			@Param("dayStart") String dayEnd);

	/**
	 * 获取utm注册用户HXF投资额
	 * @param list utm注册用户userid集合
	 * @param dayStart
	 * @param dayEnd
	 * @return
	 */
	BigDecimal getRtbTenderPrice(@Param("list") List<Integer> list, @Param("dayStart") String dayStart,
			@Param("dayStart") String dayEnd);
}
