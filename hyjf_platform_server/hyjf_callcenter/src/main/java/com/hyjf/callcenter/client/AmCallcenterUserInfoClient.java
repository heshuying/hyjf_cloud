package com.hyjf.callcenter.client;

import java.util.List;
import java.util.Map;

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
	List<CallcenterUserBaseCustomize> selectNoServiceFuTouUsersList(Map<String, Object> user);
	/**
	 * 查询呼叫中心未分配客服的用户（流失用户筛选）
	 * @param user
	 * @return
	 */
	List<CallcenterUserBaseCustomize> selectNoServiceLiuShiUsersList(Map<String, Object> user);
	/**
	 * 查询呼叫中心未分配客服的用户
	 * @param user
	 * @return
	 */
	List<CallcenterUserBaseCustomize> selectNoServiceUsersList(Map<String, Object> user);

}
