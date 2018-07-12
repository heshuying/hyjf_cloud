/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.config.dao.mapper.auto.ContentEnvironmentMapper;
import com.hyjf.am.config.dao.model.auto.ContentEnvironment;
import com.hyjf.am.config.dao.model.auto.ContentEnvironmentExample;
import com.hyjf.am.config.service.ContentEnvironmentService;
import com.hyjf.am.resquest.admin.ContentEnvironmentRequest;
import com.hyjf.common.util.GetDate;

/**
 * @author fuqiang
 * @version ContentEnvironmentServiceImpl, v0.1 2018/7/11 14:49
 */
@Service
public class ContentEnvironmentServiceImpl implements ContentEnvironmentService {
	@Autowired
	private ContentEnvironmentMapper environmentMapper;

	@Override
	public List<ContentEnvironment> searchAction(ContentEnvironmentRequest request) {
		ContentEnvironmentExample example = new ContentEnvironmentExample();
		ContentEnvironmentExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(request.getName())) {
			criteria.andNameLike("%" + request.getName() + "%");
		}
		if (request.getStatus() != null) {
			criteria.andStatusEqualTo(request.getStatus());
		}
		if (request.getImgType() != null) {
			criteria.andImgTypeEqualTo(request.getImgType());
		}
		if (StringUtils.isNotBlank(request.getStartTime())) {
			criteria.andCreateTimeGreaterThanOrEqualTo(GetDate.str2Date(request.getStartTime(), GetDate.date_sdf));
		}
		if (StringUtils.isNotBlank(request.getEndTime())) {
			criteria.andCreateTimeLessThanOrEqualTo(GetDate.str2Date(request.getEndTime(), GetDate.date_sdf));
		}
		example.setOrderByClause("update_time");
		return environmentMapper.selectByExample(example);
	}

	@Override
	public void insertAction(ContentEnvironmentRequest request) {
		ContentEnvironment target = new ContentEnvironment();
		BeanUtils.copyProperties(request, target);
		environmentMapper.insert(target);
	}

	@Override
	public void updateAction(ContentEnvironmentRequest request) {
		ContentEnvironment target = new ContentEnvironment();
		BeanUtils.copyProperties(request, target);
		environmentMapper.updateByPrimaryKey(target);
	}

	@Override
	public ContentEnvironment getRecord(Integer id) {
		ContentEnvironmentExample example = new ContentEnvironmentExample();
		example.createCriteria().andIdEqualTo(id);
		return environmentMapper.selectByPrimaryKey(id);
	}
}