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
		return jobMapper.selectByExample(example);
	}

	@Override
	public void insertAction(JobRequest request) {
		Job job = new Job();
		BeanUtils.copyProperties(request, job);
		jobMapper.insert(job);
	}

	@Override
	public void updateAction(JobRequest request) {
		Job job = new Job();
		BeanUtils.copyProperties(request, job);
		jobMapper.updateByPrimaryKey(job);
	}

	@Override
	public Job getRecord(Integer id) {
		return jobMapper.selectByPrimaryKey(id);
	}
}
