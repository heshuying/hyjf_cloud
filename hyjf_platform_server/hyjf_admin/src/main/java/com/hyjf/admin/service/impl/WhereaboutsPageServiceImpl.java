/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.request.ContentLandingPageRequestBean;
import com.hyjf.admin.beans.request.WhereaboutsPageRequestBean;
import com.hyjf.admin.client.ContentLandingPageClient;
import com.hyjf.admin.client.WhereaboutsPageClient;
import com.hyjf.admin.service.ContentLandingPageService;
import com.hyjf.admin.service.WhereaboutsPageService;
import com.hyjf.am.response.config.LandingPageResponse;
import com.hyjf.am.response.config.WhereaboutsPageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tanyy
 * @version WhereaboutsPageServiceImpl, v0.1 2018/7/16 14:14
 */
@Service
public class WhereaboutsPageServiceImpl implements WhereaboutsPageService {
	@Autowired
	private WhereaboutsPageClient whereaboutsPageClient;

	@Override
	public WhereaboutsPageResponse searchAction(WhereaboutsPageRequestBean requestBean) {
		return whereaboutsPageClient.searchAction(requestBean);
	}

	@Override
	public WhereaboutsPageResponse insertAction(WhereaboutsPageRequestBean requestBean) {
		return whereaboutsPageClient.insertAction(requestBean);
	}

	@Override
	public WhereaboutsPageResponse updateAction(WhereaboutsPageRequestBean requestBean) {
		return whereaboutsPageClient.updateAction(requestBean);
	}
	@Override
	public WhereaboutsPageResponse updateStatus(WhereaboutsPageRequestBean requestBean) {
		return whereaboutsPageClient.updateStatus(requestBean);
	}

	@Override
	public WhereaboutsPageResponse deleteById(Integer id) {
		return whereaboutsPageClient.deleteById(id);
	}
}
