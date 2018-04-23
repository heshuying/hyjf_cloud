package com.hyjf.am.user.response;


import com.hyjf.am.user.vo.UserVO;

/**
 * @author xiasq
 * @version UserResponse, v0.1 2018/1/21 22:38
 */
public class UserResponse extends Response<UserVO> {

	public static final String USER_EXISTS = "100";
	public static final String USER_EXISTS_MSG = "用户已经存在";

}
