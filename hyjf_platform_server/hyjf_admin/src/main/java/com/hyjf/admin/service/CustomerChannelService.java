package com.hyjf.admin.service;

import com.hyjf.am.response.admin.AdminAuthConfigResponse;
import com.hyjf.am.response.admin.CustomerChannelResponse;
import com.hyjf.am.resquest.admin.CustomerChannelRequest;

public interface CustomerChannelService {

	CustomerChannelResponse getCustomerChannelList(CustomerChannelRequest request);

	AdminAuthConfigResponse insetCustomerChannel(CustomerChannelRequest request);

	AdminAuthConfigResponse updateAuthConfig(CustomerChannelRequest request);

}
