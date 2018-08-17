package com.hyjf.am.response.user;


import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.UserVO;

/**
 * @author xiasq
 * @version UserResponse, v0.1 2018/1/21 22:38
 */
public class UserResponse extends Response<UserVO> {

	public static final String USER_EXISTS = "100";
	public static final String USER_EXISTS_MSG = "用户已经存在";

	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
