/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.user.KeyCountResponse;
import com.hyjf.am.resquest.user.KeyCountRequest;

/**
 * @author tanyy
 * @version KeyCountService.java, v0.1 2018年7月17日 下午3:04:29
 */
public interface KeyCountService {

	/**
	 * 根据条件查询列表
	 *
	 * @param request
	 * @return
	 */
	KeyCountResponse searchAction(KeyCountRequest request);

}
