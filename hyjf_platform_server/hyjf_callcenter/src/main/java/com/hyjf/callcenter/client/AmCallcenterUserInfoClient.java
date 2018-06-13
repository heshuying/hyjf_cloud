package com.hyjf.callcenter.client;

import java.util.List;
import java.util.Map;

import com.hyjf.am.resquest.callcenter.CallCenterUserInfoRequest;
import com.hyjf.am.vo.callcenter.CallCenterUserBaseVO;
import com.hyjf.callcenter.beans.customizebean.CallcenterUserBaseCustomize;

/**
 * @author libin
 * @version AmCallcenterUserInfoClient, v0.1 2018/6/6 10:02
 */
public interface AmCallcenterUserInfoClient {
	
	/**
	 * 查询呼叫中心未分配客服的用户（复投用户筛选）
	 * @param user
	 * @return
	 */
	List<CallCenterUserBaseVO> selectNoServiceFuTouUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest);
	/**
	 * 查询呼叫中心未分配客服的用户（流失用户筛选）
	 * @param user
	 * @return
	 */
	List<CallCenterUserBaseVO> selectNoServiceLiuShiUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest);
	/**
	 * 查询呼叫中心未分配客服的用户
	 * @param user
	 * @return
	 */
	List<CallCenterUserBaseVO> selectNoServiceUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest);

}
