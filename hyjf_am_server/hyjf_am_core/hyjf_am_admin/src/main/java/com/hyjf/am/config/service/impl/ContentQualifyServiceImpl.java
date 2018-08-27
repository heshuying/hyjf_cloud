/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyjf.am.config.dao.mapper.auto.ContentQualifyMapper;
import com.hyjf.am.config.dao.model.auto.ContentQualify;
import com.hyjf.am.config.dao.model.auto.ContentQualifyExample;
import com.hyjf.am.config.service.ContentQualifyService;
import com.hyjf.am.resquest.admin.ContentQualifyRequest;
import com.hyjf.common.util.GetDate;

/**
 * @author fuqiang
 * @version ContentQualifyServiceImpl, v0.1 2018/7/11 10:13
 */
@Service
public class ContentQualifyServiceImpl implements ContentQualifyService {
	@Autowired
	private ContentQualifyMapper contentQualifyMapper;

	@Override
	public List<ContentQualify> searchAction(ContentQualifyRequest request) {
		ContentQualifyExample example = new ContentQualifyExample();
		ContentQualifyExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(request.getName())) {
			criteria.andNameEqualTo(request.getName());
		}
		if (StringUtils.isNotBlank(request.getStartTime())) {
			criteria.andCreateTimeGreaterThanOrEqualTo(GetDate.str2Date(request.getStartTime(), GetDate.date_sdf));
		}
		if (StringUtils.isNotBlank(request.getEndTime())) {
			criteria.andCreateTimeLessThanOrEqualTo(GetDate.str2Date(request.getEndTime(), GetDate.date_sdf));
		}
		if (request.getStatus() != null) {
			criteria.andStatusEqualTo(request.getStatus());
		}
		example.setOrderByClause("create_time DESC");
		return contentQualifyMapper.selectByExample(example);
	}

	@Override
	public void insertAction(ContentQualifyRequest request) {
		if (request != null) {
			ContentQualify contentQualify = new ContentQualify();
			BeanUtils.copyProperties(request, contentQualify);
			contentQualifyMapper.insert(contentQualify);
		}
	}

	@Override
	public void updateAction(ContentQualifyRequest request) {
		if (request != null) {
			ContentQualify contentQualify = new ContentQualify();
			BeanUtils.copyProperties(request, contentQualify);
			contentQualifyMapper.updateByPrimaryKey(contentQualify);
		}
	}

	@Override
	public ContentQualify getRecord(Integer id) {
		ContentQualifyExample example = new ContentQualifyExample();
		example.createCriteria().andIdEqualTo(id);
		List<ContentQualify> list = contentQualifyMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void delete(Integer id) {
		contentQualifyMapper.deleteByPrimaryKey(id);
	}
}
