/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.HjhCommissionService;
import com.hyjf.am.response.admin.HjhCommissionResponse;
import com.hyjf.am.resquest.admin.HjhCommissionRequest;

/**
 * @author libin
 * @version HjhCommissionServiceImpl.java, v0.1 2018年8月7日 下午2:45:19
 */
@Service
public class HjhCommissionServiceImpl implements HjhCommissionService{
	
	@Autowired
	public AmTradeClient amTradeClient;

	/**
	 * 汇计划提成列表查询
	 *
	 * @param instCodeSrch
	 * @return List<HjhAssetTypeVO>
	 */
	@Override
	public HjhCommissionResponse selectHjhCommissionList(HjhCommissionRequest form) {
		HjhCommissionResponse response = amTradeClient.selectHjhCommissionList(form);
		return response;
	}

	@Override
	public HjhCommissionResponse selecthjhCommissionTotal(HjhCommissionRequest form) {
		HjhCommissionResponse response = amTradeClient.selecthjhCommissionTotal(form);
		return response;
	}

}
