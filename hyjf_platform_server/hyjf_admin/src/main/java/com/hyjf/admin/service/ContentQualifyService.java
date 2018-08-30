/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.ContentQualifyRequestBean;
import com.hyjf.am.response.admin.ContentQualifyResponse;

/**
 * 内容管理 -资质荣誉
 * 
 * @author fuqiang
 * @version ContentQualifyService, v0.1 2018/7/11 9:33
 */
public interface ContentQualifyService {
	/**
	 * 公司管理-资质荣誉列表查询
	 *
	 * @param requestBean
	 * @return
	 */
	ContentQualifyResponse searchAction(ContentQualifyRequestBean requestBean);

	/**
	 * 添加公司管理-资质荣誉
	 *
	 * @param requestBean
	 * @return
	 */
	int insertAction(ContentQualifyRequestBean requestBean);

	/**
	 * 修改公司管理-资质荣誉
	 *
	 * @param requestBean
	 * @return
	 */
	int updateAction(ContentQualifyRequestBean requestBean);

	/**
	 * 修改公司管理-资质荣誉状态
	 * 
	 * @param requestBean
	 * @return
	 */
	int updateStatus(ContentQualifyRequestBean requestBean);

	/**
	 * 删除公司管理-资质荣誉状态
	 *
	 * @param id
	 * @return
	 */
	int deleteById(Integer id);
}
