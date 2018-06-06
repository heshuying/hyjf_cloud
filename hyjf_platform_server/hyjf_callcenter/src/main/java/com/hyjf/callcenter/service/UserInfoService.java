package com.hyjf.callcenter.service;

import java.util.List;

import com.hyjf.callcenter.beans.UserBean;
import com.hyjf.callcenter.beans.customizebean.CallcenterUserBaseCustomize;

/**
 * @author libin
 * @version UserInfoService, v0.1 2018/6/6
 */
public interface UserInfoService {
	
	/**
	 * 查询呼叫中心未分配客服的用户
	 * @param user
	 * @return List<CallcenterUserBaseCustomize>
	 * @author libin
	 */
	public List<CallcenterUserBaseCustomize> getNoServiceUsersList(UserBean bean);

}
