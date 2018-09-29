package com.hyjf.callcenter.service;

import com.hyjf.am.vo.callcenter.CallCenterServiceUsersVO;
import com.hyjf.am.vo.callcenter.CallCenterUserBaseVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.beans.UserBean;

import java.util.List;

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
	List<CallCenterUserBaseVO> getNoServiceUsersList(UserBean bean);

	/**
	 * 更新呼叫中心用户分配客服的状态
	 * @param userList
	 * @return Integer
	 * @author wangjun
	 */
	Integer executeRecord(List<CallCenterServiceUsersVO> userList);

	/**
	 * 查询会员基本信息
	 * @param user
	 * @return List<CallcenterUserBaseCustomize>
	 * @author libin
	 */
	List<CallCenterUserBaseVO> getUserBaseList(UserVO user);

	/**
	 * 查询会员详细信息
	 * @param user
	 * @return List<CellcenterUserDetailCustomize>
	 * @author libin
	 */
	List<CallCenterUserBaseVO> getUserDetailList(UserVO user);

}
