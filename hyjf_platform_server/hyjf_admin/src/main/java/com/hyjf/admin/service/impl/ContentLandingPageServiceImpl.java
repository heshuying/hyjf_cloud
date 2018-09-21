/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.request.ContentLandingPageRequestBean;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.ContentLandingPageService;
import com.hyjf.am.response.config.LandingPageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tanyy
 * @version ContentLandingPageServiceImpl, v0.1 2018/7/16 14:14
 */
@Service
public class ContentLandingPageServiceImpl implements ContentLandingPageService {
	@Autowired
	private AmConfigClient amConfigClient;

	@Override
	public LandingPageResponse searchAction(ContentLandingPageRequestBean requestBean) {
		return amConfigClient.searchAction(requestBean);
	}

	@Override
	public LandingPageResponse insertAction(ContentLandingPageRequestBean requestBean) {
		return amConfigClient.insertAction(requestBean);
	}

	@Override
	public LandingPageResponse updateAction(ContentLandingPageRequestBean requestBean) {
		return amConfigClient.updateAction(requestBean);
	}


	@Override
	public LandingPageResponse deleteById(Integer id) {
		return amConfigClient.deleteLandingPageById(id);
	}

	@Override
	public LandingPageResponse getRecord(Integer id){
		return amConfigClient.getLandingPageRecord(id);
	}

}
