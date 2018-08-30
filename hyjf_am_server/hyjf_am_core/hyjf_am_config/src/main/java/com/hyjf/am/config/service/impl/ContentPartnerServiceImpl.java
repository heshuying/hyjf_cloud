/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.LinkMapper;
import com.hyjf.am.config.dao.model.auto.Link;
import com.hyjf.am.config.dao.model.auto.LinkExample;
import com.hyjf.am.config.service.ContentPartnerService;
import com.hyjf.am.resquest.admin.ContentPartnerRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fuqiang
 * @version ContentPartnerServiceImpl, v0.1 2018/7/12 10:39
 */
@Service
public class ContentPartnerServiceImpl implements ContentPartnerService {
	@Autowired
	private LinkMapper linkMapper;

	@Override
	public List<Link> searchAction(ContentPartnerRequest request) {
		LinkExample example = new LinkExample();
		LinkExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(request.getWebname())) {
			criteria.andWebnameEqualTo(request.getWebname());
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
		example.setOrderByClause("`partner_type` ASC,`order` Asc,`create_time` Desc");
		return linkMapper.selectByExample(example);
	}

	@Override
	public void insertAction(ContentPartnerRequest request) {
		Link link = new Link();
		BeanUtils.copyProperties(request, link);
		linkMapper.insert(link);
	}

	@Override
	public void updateAction(ContentPartnerRequest request) {
		Link link = new Link();
		BeanUtils.copyProperties(request, link);
		linkMapper.updateByPrimaryKey(link);
	}

	@Override
	public Link getRecord(Integer id) {
		return linkMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Link> getbyPartnerType(Integer type) {
		LinkExample example = new LinkExample();
		LinkExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo( 1);// 启用状态
		criteria.andTypeEqualTo(2);// 合作伙伴
		if (type != null) {
			criteria.andPartnerTypeEqualTo(type);
		}
		example.setOrderByClause("`partner_type` ASC,`order` Asc,`create_time` Desc");
		return linkMapper.selectByExample(example);
	}

	@Override
	public void deleteById(Integer id) {
		linkMapper.deleteByPrimaryKey(id);
	}
}
