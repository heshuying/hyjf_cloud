/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.Link;
import com.hyjf.am.resquest.admin.ContentPartnerRequest;

import java.util.List;

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
	int insertAction(ContentPartnerRequest request);

	/**
	 * 修改公司管理-合作伙伴
	 *
	 * @param request
	 */
	int updateAction(ContentPartnerRequest request);

	/**
	 * 根据id查询公司管理-合作伙伴
	 *
	 * @param id
	 * @return
	 */
	Link getRecord(Integer id);

	/**
	 * 根据合作类型查询公司管理-合作伙伴
	 *
	 * @param type
	 * @return
	 */
    Link getbyPartnerType(Integer type);

	/**
	 * 根据id删除合作伙伴
	 *
	 * @param id
	 */
	int deleteById(Integer id);

	/**
	 * 获取符合条件的条数
	 * @param request
	 * @return
	 */
    int selectCount(ContentPartnerRequest request);
}
