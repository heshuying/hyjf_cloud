/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin;

import java.util.List;

import com.hyjf.am.resquest.admin.STZHWhiteListRequest;
import com.hyjf.am.trade.dao.model.auto.StzhWhiteList;

/**
 * @author fuqiang
 * @version StzfWhiteConfigService, v0.1 2018/7/10 15:24
 */
public interface StzfWhiteConfigService {
	/**
	 * 获取受托支付白名单列表
	 *
	 * @return
	 */
	List<StzhWhiteList> selectSTZHWhiteList(STZHWhiteListRequest request);

	/**
	 * 添加受托支付白名单
	 *
	 * @param request
	 */
	void insertSTZHWhiteList(STZHWhiteListRequest request);

	/**
	 * 更新受托支付白名单
	 *
	 * @param request
	 */
	void updateSTZHWhiteList(STZHWhiteListRequest request);
}
