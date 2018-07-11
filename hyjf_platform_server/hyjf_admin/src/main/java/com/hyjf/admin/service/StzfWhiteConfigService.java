/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.STZHWhiteListRequestBean;
import com.hyjf.am.response.trade.STZHWhiteListResponse;
import com.hyjf.am.vo.user.HjhInstConfigVO;

/**
 * 受托支付白名单
 * 
 * @author fuqiang
 * @version StzfWhiteConfigService, v0.1 2018/7/10 9:20
 */
public interface StzfWhiteConfigService {

	/**
	 * 受托支付白名单列表
	 *
	 * @return
	 */
	STZHWhiteListResponse selectSTZHWhiteList(STZHWhiteListRequestBean requestBean);

	/**
	 * 新增受托支付白名单
	 *
	 * @param requestBean
	 * @return
	 */
	STZHWhiteListResponse insertSTZHWhiteList(STZHWhiteListRequestBean requestBean);

	/**
	 * 修改受托支付白名单
	 *
	 * @param requestBean
	 * @return
	 */
	STZHWhiteListResponse updateSTZHWhiteList(STZHWhiteListRequestBean requestBean);

	/**
	 * 获取机构信息
	 *
	 * @param instcode
	 * @return
	 */
	HjhInstConfigVO selectHjhInstConfig(String instcode);
}
