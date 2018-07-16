/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.admin.beans.request.ContentPartnerRequestBean;
import com.hyjf.am.response.config.LinkResponse;
import com.hyjf.am.vo.config.LinkVO;

/**
 * @author fuqiang
 * @version ContentPartnerClient, v0.1 2018/7/12 10:17
 */
public interface ContentPartnerClient {
	/**
	 * 根据条件查询公司管理-合作伙伴
	 *
	 * @param requestBean
	 * @return
	 */
	LinkResponse searchAction(ContentPartnerRequestBean requestBean);

	/**
	 * 添加公司管理-合作伙伴
	 *
	 * @param requestBean
	 * @return
	 */
	LinkResponse insertAction(ContentPartnerRequestBean requestBean);

	/**
	 * 修改公司管理-合作伙伴
	 *
	 * @param requestBean
	 * @return
	 */
	LinkResponse updateAction(ContentPartnerRequestBean requestBean);

	/**
	 * 根据id获取公司管理-合作伙伴
	 *
	 * @param id
	 * @return
	 */
	LinkVO getRecord(Integer id);

	/**
	 * 根据id删除公司管理-合作伙伴
	 *
	 * @param id
	 * @return
	 */
    LinkResponse deleteById(Integer id);
}
