package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.CashDepositeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CashDepositeServiceImpl implements CashDepositeService{

	@Autowired
	public AmTradeClient amTradeClient;
	
	@Override
	public void updateCashDepositeStatus(String assetId, String menuHide) throws Exception {
        if(StringUtils.isNotBlank(assetId)){
        	amTradeClient.updateCashDepositeStatus(assetId,menuHide);
        }
    }

}
