package com.hyjf.am.config.service;

import java.util.List;

import com.hyjf.am.config.dao.model.auto.CustomerServiceChannel;
import com.hyjf.am.resquest.admin.CustomerChannelRequest;

public interface CustomerChannelService {

	int getCustomerChannelCount();

	List<CustomerServiceChannel> getCustomerChannelList( CustomerChannelRequest request,int count);

	int insetCustomerChannel(CustomerChannelRequest request);

	int updateCustomerChannel(CustomerChannelRequest request);

}
