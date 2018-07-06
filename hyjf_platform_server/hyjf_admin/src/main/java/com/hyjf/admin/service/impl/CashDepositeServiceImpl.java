package com.hyjf.admin.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.admin.client.AssetListClient;
import com.hyjf.admin.service.CashDepositeService;

@Service
public class CashDepositeServiceImpl implements CashDepositeService{

	@Autowired
	private AssetListClient assetListClient;
	
	@Override
	public void updateCashDepositeStatus(String assetId, String menuHide) throws Exception {
        if(StringUtils.isNotBlank(assetId)){
        	assetListClient.updateCashDepositeStatus(assetId,menuHide);
        }
    }

}
