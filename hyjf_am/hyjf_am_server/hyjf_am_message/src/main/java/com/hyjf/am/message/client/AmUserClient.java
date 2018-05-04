package com.hyjf.am.message.client;

import com.hyjf.am.vo.user.UserAliasVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;

/**
 * @author xiasq
 * @version AmUserClient, v0.1 2018/4/19 12:44
 */
public interface AmUserClient {

	UserVO findUserByMobile(String mobile);

	UserVO findUserById(int userId);

	UserInfoVO findUserInfoById(int userId);

    UserAliasVO getAliasByMobile(String mobile);
}
