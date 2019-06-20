/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.user;

import java.util.List;

import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.vo.config.CustomerServiceGroupConfigVO;
import com.hyjf.am.vo.config.ElectricitySalesDataPushListVO;


/**
 * @author dzs
 * @version ElectricitySalesDataPushListResponse, v0.1 2018/6/22 14:30
 */
public class ElectricitySalesDataPushListResponse extends AdminResponse<ElectricitySalesDataPushListVO> {
	private List<CustomerServiceGroupConfigVO> groupList;

	public List<CustomerServiceGroupConfigVO> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<CustomerServiceGroupConfigVO> groupList) {
		this.groupList = groupList;
	}
   
}
