/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.JobMapper;
import com.hyjf.am.config.dao.model.auto.Job;
import com.hyjf.am.config.dao.model.auto.JobExample;
import com.hyjf.am.config.service.JobService;
import com.hyjf.am.resquest.admin.JobRequest;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author fuqiang
 * @version JobServiceImpl, v0.1 2018/7/12 14:34
 */
@Service
public class JobServiceImpl implements JobService {
	@Autowired
	private JobMapper jobMapper;

	@Override
	public List<Job> searchAction(JobRequest request) {
		JobExample example = new JobExample();
		JobExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(request.getOfficeName())) {
			criteria.andOfficeNameEqualTo(request.getOfficeName());
		}
		if (request.getStatus() != null) {
			criteria.andStatusEqualTo(request.getStatus());
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
		example.setOrderByClause("create_time DESC");
		return jobMapper.selectByExample(example);
	}

	@Override
	public int insertAction(JobRequest request) {
		Job job = new Job();
		BeanUtils.copyProperties(request, job);
		return jobMapper.insertSelective(job);
	}

	@Override
	public int updateAction(JobRequest request) {
		Job job = new Job();
		BeanUtils.copyProperties(request, job);
		return jobMapper.updateByPrimaryKeySelective(job);
	}

	@Override
	public Job getRecord(Integer id) {
		return jobMapper.selectByPrimaryKey(id);
	}

	@Override
	public int selectCount(JobRequest request) {
		request.setCurrPage(0);
		List<Job> list = searchAction(request);
		if (!CollectionUtils.isEmpty(list)) {
			return list.size();
		}
		return 0;
	}

	@Override
	public int delete(Integer id) {
		return jobMapper.deleteByPrimaryKey(id);
	}
}
