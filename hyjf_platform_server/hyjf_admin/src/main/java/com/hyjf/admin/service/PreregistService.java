/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.user.AdminPreRegistListResponse;
import com.hyjf.am.resquest.user.AdminPreRegistListRequest;


/**
 * @author DongZeShan
 * @version LoginController.java, v0.1 2018年6月15日 上午9:32:30
 */

public interface PreregistService  {
	



	/**
	 * 获取预注册数据列表
	 * 
	 * @return
	 */
	public AdminPreRegistListResponse getRecordList(AdminPreRegistListRequest adminPreRegistListRequest);
	
	/**
     * 获取预注册页面信息
     * 
     * @return
     */
    public AdminPreRegistListResponse getPreRegist(AdminPreRegistListRequest adminPreRegistListRequest);
    
    /**
     * 编辑保存预注册页面信息
     * 
     * @return
     */
    public AdminPreRegistListResponse savePreRegist(AdminPreRegistListRequest adminPreRegistListRequest);


}
