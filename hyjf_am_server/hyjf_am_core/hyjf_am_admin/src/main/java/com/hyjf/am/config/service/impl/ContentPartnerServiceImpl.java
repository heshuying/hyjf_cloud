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

import com.hyjf.am.config.dao.mapper.auto.LinkMapper;
import com.hyjf.am.config.dao.model.auto.Link;
import com.hyjf.am.config.dao.model.auto.LinkExample;
import com.hyjf.am.config.service.ContentPartnerService;
import com.hyjf.am.resquest.admin.ContentPartnerRequest;
import com.hyjf.common.util.GetDate;

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
		if (StringUtils.isNotBlank(request.getStartTime())) {
			criteria.andCreateTimeGreaterThanOrEqualTo(GetDate.str2Date(request.getStartTime(), GetDate.date_sdf));
		}
		if (StringUtils.isNotBlank(request.getEndTime())) {
			criteria.andCreateTimeLessThanOrEqualTo(GetDate.str2Date(request.getEndTime(), GetDate.date_sdf));
		}
		if (request.getStatus() != null) {
			criteria.andStatusEqualTo(request.getStatus());
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
	public Link getbyPartnerType(Integer type) {
		LinkExample example = new LinkExample();
		example.createCriteria().andPartnerTypeEqualTo(type);
		List<Link> linkList = linkMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(linkList)) {
			return linkList.get(0);
		}
		return null;
	}

	@Override
	public void deleteById(Integer id) {
		linkMapper.deleteByPrimaryKey(id);
	}
}
