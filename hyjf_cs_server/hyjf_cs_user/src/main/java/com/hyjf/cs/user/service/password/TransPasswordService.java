/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.password;

import com.hyjf.am.vo.user.UserVO;

/**
 * @author libin
 * 第三方专用
 * @version TransPasswordService.java, v0.1 2018年7月19日 上午9:30:08
 */
public interface TransPasswordService {
	
    /**
     * 通过手机号获取用户
     * @param newPW
     * @return
     */
	UserVO findUserByMobile(String mobile);
}
