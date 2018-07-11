/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.admin.beans.request.STZHWhiteListRequestBean;
import com.hyjf.am.response.trade.STZHWhiteListResponse;

/**
 * @author fuqiang
 * @version StzfWhiteConfigClient, v0.1 2018/7/10 9:44
 */
public interface StzfWhiteConfigClient {
	/**
	 * 受托支付白名单列表
	 *
	 * @return
	 */
	STZHWhiteListResponse selectSTZHWhiteList(STZHWhiteListRequestBean requestBean);

	/**
	 * 添加受托支付白名
	 *
	 * @param requestBean
	 * @return
	 */
	STZHWhiteListResponse insertSTZHWhiteList(STZHWhiteListRequestBean requestBean);

	/**
	 * 修改受托支付白名
	 *
	 * @param requestBean
	 * @return
	 */
	STZHWhiteListResponse updateSTZHWhiteList(STZHWhiteListRequestBean requestBean);
}
