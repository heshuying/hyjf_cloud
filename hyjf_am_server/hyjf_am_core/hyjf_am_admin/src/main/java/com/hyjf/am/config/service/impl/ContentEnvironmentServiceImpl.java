/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.ContentEnvironmentMapper;
import com.hyjf.am.config.dao.model.auto.ContentEnvironment;
import com.hyjf.am.config.dao.model.auto.ContentEnvironmentExample;
import com.hyjf.am.config.service.ContentEnvironmentService;
import com.hyjf.am.resquest.admin.ContentEnvironmentRequest;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

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
			criteria.andNameEqualTo(request.getName());
		}
		if (request.getStatus() != null) {
			criteria.andStatusEqualTo(request.getStatus());
		}
		if (request.getImgType() != null) {
			criteria.andImgTypeEqualTo(request.getImgType());
		}
		if (request.getStartTime() != null && request.getEndTime() != null) {
			criteria.andCreateTimeGreaterThanOrEqualTo(GetDate.getDayStartOfSomeDay(request.getStartTime()));
			criteria.andCreateTimeLessThanOrEqualTo(GetDate.getDayEndOfSomeDay(request.getEndTime()));
		}
		if (request.getCurrPage() > 0 && request.getPageSize() > 0) {
			int limitStart = (request.getCurrPage() - 1) * (request.getPageSize());
			int limitEnd = request.getPageSize();
			example.setLimitStart(limitStart);
			example.setLimitEnd(limitEnd);
		}
		example.setOrderByClause("update_time");
		return environmentMapper.selectByExample(example);
	}

	@Override
	public int insertAction(ContentEnvironmentRequest request) {
		ContentEnvironment target = new ContentEnvironment();
		BeanUtils.copyProperties(request, target);
		return environmentMapper.insertSelective(target);
	}

	@Override
	public int updateAction(ContentEnvironmentRequest request) {
		ContentEnvironment target = new ContentEnvironment();
		BeanUtils.copyProperties(request, target);
		return environmentMapper.updateByPrimaryKeySelective(target);
	}

	@Override
	public ContentEnvironment getRecord(Integer id) {
		ContentEnvironmentExample example = new ContentEnvironmentExample();
		example.createCriteria().andIdEqualTo(id);
		return environmentMapper.selectByPrimaryKey(id);
	}

	@Override
	public int deleteById(Integer id) {
		return environmentMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int selectCount(ContentEnvironmentRequest request) {
		request.setCurrPage(0);
		List<ContentEnvironment> list = searchAction(request);
		if (!CollectionUtils.isEmpty(list)) {
			return list.size();
		}
		return 0;
	}
}
