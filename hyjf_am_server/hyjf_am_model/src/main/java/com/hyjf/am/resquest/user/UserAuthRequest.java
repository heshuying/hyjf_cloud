package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.am.vo.user.HjhUserAuthLogVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserVO;

/**
 * @Description
 * @Author pangchengchao
 * @Version v0.1
 * @Date
 */
public class UserAuthRequest extends BaseVO {
	private HjhUserAuthLogVO hjhUserAuthLogVO;
	private UserVO user;
	private HjhUserAuthVO hjhUserAuth;

	public HjhUserAuthLogVO getHjhUserAuthLogVO() {
		return hjhUserAuthLogVO;
	}

	public void setHjhUserAuthLogVO(HjhUserAuthLogVO hjhUserAuthLogVO) {
		this.hjhUserAuthLogVO = hjhUserAuthLogVO;
	}

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

	public HjhUserAuthVO getHjhUserAuth() {
		return hjhUserAuth;
	}

	public void setHjhUserAuth(HjhUserAuthVO hjhUserAuth) {
		this.hjhUserAuth = hjhUserAuth;
	}
}
