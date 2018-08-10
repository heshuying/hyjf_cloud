/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.content;

import com.hyjf.am.response.config.WhereaboutsPageResponse;
import com.hyjf.am.resquest.admin.WhereaboutsPageRequest;
import com.hyjf.am.user.dao.model.auto.WhereaboutsPageConfig;
import com.hyjf.am.user.service.BaseService;

/**
 * @author tanyy
 * @version WhereaboutsPageService, v0.1 2018/7/16 14:33
 */
public interface WhereaboutsPageService extends BaseService{
	/**
	 * 根据条件查询移动端着陆页管理
	 *
	 * @param request
	 * @return
	 */
	WhereaboutsPageResponse searchAction(WhereaboutsPageRequest request);

	/**
	 * 添加移动端着陆页管理
	 *
	 * @param request
	 */
	void insertAction(WhereaboutsPageRequest request);

	/**
	 * 修改移动端着陆页管理
	 *
	 * @param request
	 */
	void updateAction(WhereaboutsPageRequest request);

	/**
	 * 修改移动端着陆页管理状态
	 *
	 * @param request
	 */
	void statusAction(WhereaboutsPageRequest request);

	/**
	 * 通过id获取对象
	 *
	 * @param id
	 */
	WhereaboutsPageConfig getRecord(Integer id);

    /**
     * 删除
     *
     * @param id
     */
    void deleteById(Integer id);
}
