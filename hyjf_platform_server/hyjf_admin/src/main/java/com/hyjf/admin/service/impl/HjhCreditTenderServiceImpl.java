/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.admin.client.HjhCreditTenderClient;
import com.hyjf.admin.service.HjhCreditTenderService;
import com.hyjf.am.response.admin.HjhCreditTenderResponse;
import com.hyjf.am.resquest.admin.HjhCreditTenderRequest;

/**
 * @author libin
 * @version HjhCreditTenderServiceImpl.java, v0.1 2018年7月11日 下午3:02:16
 */
@Service
public class HjhCreditTenderServiceImpl implements HjhCreditTenderService{
	
    @Autowired
    private HjhCreditTenderClient hjhCreditTenderClient;
    
	/**
	 * 获取详细列表
	 * 
	 * @param DebtCreditCustomize
	 * @return
	 */
	@Override
	public HjhCreditTenderResponse getHjhCreditTenderListByParam(HjhCreditTenderRequest form) {
		HjhCreditTenderResponse response = hjhCreditTenderClient.getHjhCreditTenderListByParam(form);
		return response;
	}

}
