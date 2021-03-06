/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.ContentQualify;
import com.hyjf.am.resquest.admin.ContentQualifyRequest;

import java.util.List;

/**
 * @author fuqiang
 * @version ContentQualifyService, v0.1 2018/7/11 10:12
 */
public interface ContentQualifyService {
	/**
	 * 根据条件查询公司管理-资质荣誉
	 *
	 * @param request
	 * @return
	 */
	List<ContentQualify> searchAction(ContentQualifyRequest request);

	/**
	 * 添加公司管理-资质荣誉
	 *
	 * @param request
	 */
	void insertAction(ContentQualifyRequest request);

	/**
	 * 修改公司管理-资质荣誉
	 *
	 * @param request
	 */
	void updateAction(ContentQualifyRequest request);

	/**
	 * 根据id查询公司管理-资质荣誉
	 *
	 * @param id
	 * @return
	 */
	ContentQualify getRecord(Integer id);

	/**
	 * 根据id删除公司管理-资质荣誉
	 *
	 * @param id
	 */
    void delete(Integer id);

	/**
	 * 查询符合条件的条数
	 * @param request
	 * @return
	 */
	int selectCount(ContentQualifyRequest request);
}
