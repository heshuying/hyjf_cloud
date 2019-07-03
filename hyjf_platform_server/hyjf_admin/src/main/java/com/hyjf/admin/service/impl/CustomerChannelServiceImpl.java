package com.hyjf.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.CustomerChannelService;
import com.hyjf.am.response.admin.AdminAuthConfigResponse;
import com.hyjf.am.response.admin.CustomerChannelResponse;
import com.hyjf.am.resquest.admin.CustomerChannelRequest;

@Service
public class CustomerChannelServiceImpl implements CustomerChannelService{
	 @Autowired
	 AmConfigClient amConfigClient;
	@Override
	public CustomerChannelResponse getCustomerChannelList(CustomerChannelRequest request) {
		return amConfigClient.getCustomerChannelList( request);
	}

	@Override
	public CustomerChannelResponse insetCustomerChannel(CustomerChannelRequest request) {
		return amConfigClient.insetCustomerChannel( request);
	}

	@Override
	public CustomerChannelResponse updateCustomerChannel(CustomerChannelRequest request) {
		return amConfigClient.updateCustomerChannel( request);
	}


}
