/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize.trade;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author fuqiang
 * @version BorrowTenderCustomizeMapper, v0.1 2018/7/17 9:45
 */
public interface BorrowTenderCustomizeMapper {
	/**
	 * 获取utm注册用户投资次数
	 * 
	 * @param list
	 * @return
	 */
	Integer getUtmTenderNum(@Param("list") List<Integer> list, @Param("dayStart") String dayStart,
			@Param("dayEnd") String dayEnd);
}
