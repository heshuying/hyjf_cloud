/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.ContentQualifyMapper;
import com.hyjf.am.config.dao.model.auto.ContentQualify;
import com.hyjf.am.config.dao.model.auto.ContentQualifyExample;
import com.hyjf.am.config.service.ContentQualifyService;
import com.hyjf.am.resquest.admin.ContentQualifyRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

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
		if (request.getStartTime() != null && request.getEndTime() != null) {
			criteria.andCreateTimeGreaterThanOrEqualTo(request.getStartTime());
			criteria.andCreateTimeLessThanOrEqualTo(request.getEndTime());
		}
		if (request.getStatus() != null) {
			criteria.andStatusEqualTo(request.getStatus());
		}
		if (request.getCurrPage() > 0 && request.getPageSize() > 0) {
			int limitStart = (request.getCurrPage() - 1) * (request.getPageSize());
			int limitEnd = request.getPageSize();
			example.setLimitStart(limitStart);
			example.setLimitEnd(limitEnd);
		}
		example.setOrderByClause("create_time DESC");
		return contentQualifyMapper.selectByExample(example);
	}

	@Override
	public int insertAction(ContentQualifyRequest request) {
		if (request != null) {
			ContentQualify contentQualify = new ContentQualify();
			BeanUtils.copyProperties(request, contentQualify);
			return contentQualifyMapper.insertSelective(contentQualify);
		}
		return 0;
	}

	@Override
	public int updateAction(ContentQualifyRequest request) {
		if (request != null) {
			ContentQualify contentQualify = new ContentQualify();
			BeanUtils.copyProperties(request, contentQualify);
			return contentQualifyMapper.updateByPrimaryKeySelective(contentQualify);
		}
		return 0;
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
	public int delete(Integer id) {
		return contentQualifyMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int selectCount(ContentQualifyRequest request) {
		request.setCurrPage(0);
		List<ContentQualify> list = searchAction(request);
		if (!CollectionUtils.isEmpty(list)) {
			return list.size();
		}
		return 0;
	}
}
