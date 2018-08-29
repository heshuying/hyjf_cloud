/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.Job;
import com.hyjf.am.resquest.admin.JobRequest;

import java.util.List;

/**
 * @author fuqiang
 * @version JobService, v0.1 2018/7/12 14:33
 */
public interface JobService {
	/**
	 * 根据条件查询公司管理-招贤纳士
	 *
	 * @param request
	 * @return
	 */
	List<Job> searchAction(JobRequest request);

	/**
	 * 添加公司管理-招贤纳士
	 *
	 * @param request
	 */
	int insertAction(JobRequest request);

	/**
	 * 修改公司管理-招贤纳士
	 *
	 * @param request
	 */
	int updateAction(JobRequest request);

	/**
	 * 根据id查询公司管理-招贤纳士
	 *
	 * @param id
	 * @return
	 */
	Job getRecord(Integer id);

	/**
	 *查询满足条件的件数
	 * @param request
	 * @return
	 */
    int selectCount(JobRequest request);

	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	int delete(Integer id);
}
