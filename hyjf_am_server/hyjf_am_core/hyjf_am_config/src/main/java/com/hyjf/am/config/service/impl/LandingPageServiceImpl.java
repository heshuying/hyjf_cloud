/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.LandingPageMapper;
import com.hyjf.am.config.dao.model.auto.LandingPage;
import com.hyjf.am.config.dao.model.auto.LandingPageExample;
import com.hyjf.am.config.service.LandingPageService;
import com.hyjf.am.response.config.LandingPageResponse;
import com.hyjf.am.resquest.admin.LandingPageRequest;
import com.hyjf.am.vo.config.LandingPageVo;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author tanyy
 * @version LandingPageServiceImpl, v0.1 2018/7/16 14:34
 */
@Service
public class LandingPageServiceImpl implements LandingPageService {
	@Autowired
	private LandingPageMapper landingPageMapper;

	@Override
	public LandingPageResponse searchAction(LandingPageRequest request) {
		LandingPageResponse response = new LandingPageResponse();
		LandingPageExample example = new LandingPageExample();
		LandingPageExample.Criteria criteria = example.createCriteria();
		// 条件查询
		if (request.getChannelNameSrch() != null) {
			criteria.andChannelNameLike("%" + request.getChannelNameSrch() + "%");
		}
		if (StringUtils.isNotEmpty(request.getPageNameSrch())) {
			criteria.andPageNameLike("%" + request.getPageNameSrch() + "%");
		}
		if(StringUtils.isNotEmpty(request.getStartTime())){
			criteria.andCreateTimeGreaterThanOrEqualTo(GetDate.str2Date(request.getStartTime(), GetDate.date_sdf));
		}
		if(StringUtils.isNotEmpty(request.getEndTime())){
			criteria.andCreateTimeLessThanOrEqualTo(GetDate.str2Date(request.getEndTime(), GetDate.date_sdf));
		}

		example.setOrderByClause("create_time Desc");
		int count = landingPageMapper.countByExample(example);
		response.setCount(count);
		if(count>0){
			Paginator paginator = new Paginator(request.getCurrPage(), count);
			example.setLimitStart(paginator.getOffset());
			example.setLimitEnd(paginator.getLimit());
			List<LandingPage> list = landingPageMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(list)) {
				List<LandingPageVo> voList = CommonUtils.convertBeanList(list, LandingPageVo.class);
				response.setResultList(voList);
			}
		}
		return response;
	}

	@Override
	public void insertAction(LandingPageRequest request) {
		LandingPage record = new LandingPage();
		BeanUtils.copyProperties(request, record);
		landingPageMapper.insertSelective(record);
	}

	@Override
	public void updateAction(LandingPageRequest request) {
		LandingPage record = new LandingPage();
		BeanUtils.copyProperties(request, record);
		landingPageMapper.updateByPrimaryKey(record);
	}

	@Override
	public LandingPage getRecord(Integer id) {
		return landingPageMapper.selectByPrimaryKey(id);
	}
	@Override
	public void deleteById(Integer id){
		 landingPageMapper.deleteByPrimaryKey(id);
	}
}
