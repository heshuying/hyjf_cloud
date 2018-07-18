package com.hyjf.admin.client;

import com.hyjf.am.response.config.AdminUtmReadPermissionsResponse;
import com.hyjf.am.response.user.KeyCountResponse;
import com.hyjf.am.resquest.config.AdminUtmReadPermissionsRequest;
import com.hyjf.am.resquest.user.KeyCountRequest;

public interface KeyCountClient {

	public KeyCountResponse searchAction(KeyCountRequest request);

}
