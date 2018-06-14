package com.hyjf.callcenter.beans;

import com.hyjf.am.vo.callcenter.CallCenterServiceUsersVO;

import java.io.Serializable;
import java.util.List;

/**
 * 查询用户查询条件Bean
 * @author wangjun
 * @version hyjf 1.0
 * @since hyjf 1.0 2018年06月13日
 */
public class JsonBean extends BaseFormBean implements Serializable  {
	
	private static final long serialVersionUID = 256965480942133226L;

	List<CallCenterServiceUsersVO> userJsonArray;

	/**
	 * userJsonArray
	 * @return the userJsonArray
	 */

	public List<CallCenterServiceUsersVO> getUserJsonArray() {
		return userJsonArray;
	}

	/**
	 * @param userJsonArray the userJsonArray to set
	 */

	public void setUserJsonArray(List<CallCenterServiceUsersVO> userJsonArray) {
		this.userJsonArray = userJsonArray;
	}

	/**
	 * 执行前每个方法前需要添加BusinessDesc描述
	 * @return
	 * @author LiuBin
	 */

	@Override
	public String toString() {
		return "JsonBean [userJsonArray=" + userJsonArray + "]";
	}

}
