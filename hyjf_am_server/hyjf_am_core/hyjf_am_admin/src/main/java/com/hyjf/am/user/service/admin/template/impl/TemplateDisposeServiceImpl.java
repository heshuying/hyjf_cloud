package com.hyjf.am.user.service.admin.template.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.response.admin.TemplateDisposeResponse;
import com.hyjf.am.resquest.admin.TemplateDisposeRequest;
import com.hyjf.am.user.dao.mapper.auto.TemplateDisposeMapper;
import com.hyjf.am.user.dao.model.auto.TemplateDisposeExample;
import com.hyjf.am.user.dao.model.auto.TemplateDisposeExample.Criteria;
import com.hyjf.am.user.service.admin.template.TemplateDisposeService;


@Service
public class TemplateDisposeServiceImpl implements TemplateDisposeService {
	@Autowired
	TemplateDisposeMapper templateDisposeMapper;

	@Override
	public TemplateDisposeResponse templateDisposeList(TemplateDisposeRequest templateDisposeRequest) {
		TemplateDisposeExample example=new TemplateDisposeExample();
		Criteria cr = example.createCriteria();
		if(templateDisposeRequest.getId()!=null)
		if(templateDisposeRequest.getUtmId()!=null)
		if(templateDisposeRequest.getStatus()!=null)
		if(templateDisposeRequest.getTempName()!=null&&!templateDisposeRequest.getTempName().isEmpty())

		templateDisposeMapper.countByExample(example);
		return null;
	}

	@Override
	public TemplateDisposeResponse updateTemplateDispose(TemplateDisposeRequest templateDisposeRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TemplateDisposeResponse deleteTemplateDispose(TemplateDisposeRequest templateDisposeRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TemplateDisposeResponse insertTemplateDispose(TemplateDisposeRequest templateDisposeRequest) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
