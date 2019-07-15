package com.hyjf.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.TemplateDisposeService;
import com.hyjf.am.response.admin.TemplateDisposeResponse;
import com.hyjf.am.resquest.admin.TemplateDisposeRequest;

@Service
public class TemplateDisposeServiceImpl implements TemplateDisposeService {
    @Autowired
    private AmUserClient amUserClient;
	@Override
	public TemplateDisposeResponse templateDisposeList(TemplateDisposeRequest templateDisposeRequest) {
		return amUserClient.templateDisposeList(templateDisposeRequest);
	}

	@Override
	public TemplateDisposeResponse updateTemplateDispose(TemplateDisposeRequest templateDisposeRequest) {
		return amUserClient.updateTemplateDispose(templateDisposeRequest);
	}

	@Override
	public TemplateDisposeResponse insertTemplateDispose(TemplateDisposeRequest templateDisposeRequest) {
		return amUserClient.insertTemplateDispose(templateDisposeRequest);
	}

	@Override
	public TemplateDisposeResponse deleteTemplateDispose(TemplateDisposeRequest templateDisposeRequest) {
		return amUserClient.deleteTemplateDispose(templateDisposeRequest);
	}



}
