package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.ProductSearchForPage;

/**
 * @author zhangqingqing
 */
public interface HtlCommonCustomizeMapper {

	/**
	 *  获取用户本金
	 * @return
	 */
	ProductSearchForPage selectUserPrincipal(ProductSearchForPage productSearchForPage);
}