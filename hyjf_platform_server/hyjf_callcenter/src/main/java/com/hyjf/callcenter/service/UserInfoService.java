package com.hyjf.callcenter.service;

import java.util.List;

import com.hyjf.am.vo.callcenter.CallCenterServiceUsersVO;
import com.hyjf.am.vo.callcenter.CallCenterUserBaseVO;
import com.hyjf.callcenter.beans.UserBean;

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
	public List<CallCenterUserBaseVO> getNoServiceUsersList(UserBean bean);

	/**
	 * 更新呼叫中心用户分配客服的状态
	 * @param userList
	 * @return Integer
	 * @author wangjun
	 */
	Integer executeRecord(List<CallCenterServiceUsersVO> userList);

}
