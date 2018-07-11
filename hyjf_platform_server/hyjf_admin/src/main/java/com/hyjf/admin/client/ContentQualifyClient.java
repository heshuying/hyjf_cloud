/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.admin.beans.request.ContentQualifyRequestBean;
import com.hyjf.am.response.admin.ContentQualifyResponse;
import com.hyjf.am.vo.config.ContentQualifyVO;

/**
 * @author fuqiang
 * @version ContentQualifyClient, v0.1 2018/7/11 9:53
 */
public interface ContentQualifyClient {
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
	ContentQualifyResponse insertAction(ContentQualifyRequestBean requestBean);

	/**
	 * 修改公司管理-资质荣誉
	 *
	 * @param requestBean
	 * @return
	 */
	ContentQualifyResponse updateAction(ContentQualifyRequestBean requestBean);

	/**
	 * 修改公司管理-资质荣誉状态
	 *
	 * @param id
	 * @return
	 */
	ContentQualifyVO getRecord(Integer id);
}
