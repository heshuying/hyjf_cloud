/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.ProtocolsRequestBean;
import com.hyjf.am.response.trade.FddTempletCustomizeResponse;

/**
 * @author fuqiang
 * @version ProtocolsService, v0.1 2018/7/10 16:54
 */
public interface ProtocolsService {
	/**
	 * 获取协议列表
	 *
	 * @param request
	 * @return
	 */
	FddTempletCustomizeResponse selectFddTempletList(ProtocolsRequestBean request);

	/**
	 * 添加协议列表
	 *
	 * @param requestBean
	 * @return
	 */
	FddTempletCustomizeResponse insertAction(ProtocolsRequestBean requestBean);

	/**
	 * 修改协议列表
	 *
	 * @param requestBean
	 * @return
	 */
	FddTempletCustomizeResponse updateAction(ProtocolsRequestBean requestBean);
}
