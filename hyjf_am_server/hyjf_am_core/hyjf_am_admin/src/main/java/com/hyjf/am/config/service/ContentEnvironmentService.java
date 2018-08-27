/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import java.util.List;

import com.hyjf.am.config.dao.model.auto.ContentEnvironment;
import com.hyjf.am.resquest.admin.ContentEnvironmentRequest;

/**
 * @author fuqiang
 * @version ContentEnvironmentService, v0.1 2018/7/11 14:49
 */
public interface ContentEnvironmentService {
	/**
	 * 根据条件查询内容管理-办公环境
	 *
	 * @param request
	 * @return
	 */
	List<ContentEnvironment> searchAction(ContentEnvironmentRequest request);

	/**
	 * 添加内容管理-办公环境
	 *
	 * @param request
	 */
	void insertAction(ContentEnvironmentRequest request);

	/**
	 * 修改内容管理-办公环境
	 *
	 * @param request
	 */
	void updateAction(ContentEnvironmentRequest request);

	/**
	 * 根据id获取内容管理-办公环境
	 *
	 * @param id
	 * @return
	 */
	ContentEnvironment getRecord(Integer id);

	/**
	 * 删除内容管理-办公环境状态
	 *
	 * @param id
	 */
    void deleteById(Integer id);
}
