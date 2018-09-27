/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.request.WhereaboutsPageRequestBean;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.service.WhereaboutsPageService;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.config.WhereaboutsPageResponse;
import com.hyjf.common.file.UploadFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author tanyy
 * @version WhereaboutsPageServiceImpl, v0.1 2018/7/16 14:14
 */
@Service
public class WhereaboutsPageServiceImpl extends BaseServiceImpl implements WhereaboutsPageService {
	@Autowired
	private AmUserClient amUserClient;

	@Autowired
	SystemConfig systemConfig;

	@Value("${file.domain.url}")
	private String FILEDOMAILURL;

	@Override
	public WhereaboutsPageResponse searchAction(WhereaboutsPageRequestBean requestBean) {
		return amUserClient.searchAction(requestBean);
	}

	@Override
	public WhereaboutsPageResponse insertAction(WhereaboutsPageRequestBean requestBean) {
		return amUserClient.insertAction(requestBean);
	}

	@Override
	public WhereaboutsPageResponse updateAction(WhereaboutsPageRequestBean requestBean) {
		return amUserClient.updateAction(requestBean);
	}
	@Override
	public WhereaboutsPageResponse updateStatus(WhereaboutsPageRequestBean requestBean) {
		return amUserClient.updateStatus(requestBean);
	}

	@Override
	public WhereaboutsPageResponse deleteById(Integer id) {
		return amUserClient.deleteById(id);
	}

	@Override
	public 	WhereaboutsPageResponse getWhereaboutsPageConfigById(WhereaboutsPageRequestBean form){
		form.setDomain(UploadFileUtils.getDoPath(FILEDOMAILURL));
		WhereaboutsPageResponse response = amUserClient.getWhereaboutsPageConfigById(form);
		return response;

	}
    @Override
    public StringResponse checkUtmId(Integer utmId){
        return amUserClient.checkUtmId(utmId);
    }
    @Override
    public StringResponse checkReferrer(String referrer){
        return amUserClient.checkReferrer(referrer);
    }
}
