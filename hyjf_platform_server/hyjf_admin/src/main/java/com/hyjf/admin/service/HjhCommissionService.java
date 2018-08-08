/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.HjhCommissionResponse;
import com.hyjf.am.resquest.admin.HjhCommissionRequest;

/**
 * @author libin
 * @version HjhCommissionService.java, v0.1 2018年8月7日 下午2:44:47
 */
public interface HjhCommissionService {
	/**
	 * 汇计划提成列表查询
	 *
	 * @param instCodeSrch
	 * @return List<HjhAssetTypeVO>
	 */
	HjhCommissionResponse selectHjhCommissionList(HjhCommissionRequest form);

}
