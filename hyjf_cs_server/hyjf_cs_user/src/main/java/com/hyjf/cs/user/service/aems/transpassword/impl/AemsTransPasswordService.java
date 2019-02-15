/**
 * Description:用户充值
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: GOGTZ-T
 * @version: 1.0
 *           Created at: 2015年12月4日 下午1:45:13
 *           Modification History:
 *           Modified by :
 */
package com.hyjf.cs.user.service.aems.transpassword.impl;

import com.hyjf.cs.user.service.BaseUserService;

public interface AemsTransPasswordService extends BaseUserService {

	/**
	 * 更新用户是否设置了交易密码
	 * @param userId
	 * @return
	 */
	void updateUserIsSetPassword(int userId);

}
