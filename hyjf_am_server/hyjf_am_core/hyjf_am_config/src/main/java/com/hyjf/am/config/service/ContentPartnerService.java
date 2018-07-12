/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import java.util.List;

import com.hyjf.am.config.dao.model.auto.Link;
import com.hyjf.am.resquest.admin.ContentPartnerRequest;

/**
 * @author fuqiang
 * @version ContentPartnerService, v0.1 2018/7/12 10:38
 */
public interface ContentPartnerService {
	/**
	 * 根据条件查询公司管理-合作伙伴
	 *
	 * @param request
	 * @return
	 */
	List<Link> searchAction(ContentPartnerRequest request);

	/**
	 * 添加公司管理-合作伙伴
	 *
	 * @param request
	 */
	void insertAction(ContentPartnerRequest request);

	/**
	 * 修改公司管理-合作伙伴
	 *
	 * @param request
	 */
	void updateAction(ContentPartnerRequest request);

	/**
	 * 根据id查询公司管理-合作伙伴
	 *
	 * @param id
	 * @return
	 */
	Link getRecord(Integer id);
}
