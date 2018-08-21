/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.exception;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.BidApplyQueryBean;
import com.hyjf.admin.service.BaseAdminService;

/**
 * @author libin
 * @version BidApplyQueryService.java, v0.1 2018年8月21日 上午9:20:08
 */
public interface BidApplyQueryService extends BaseAdminService{
	
	/**
	 * 投资人投标申请查询
	 * @param form
	 * @return
	 * @author libin
	 * @date 2018年8月16日 上午10:06:50
	 */
	public JSONObject bidApplyQuerySearch(BidApplyQueryBean form);

}
