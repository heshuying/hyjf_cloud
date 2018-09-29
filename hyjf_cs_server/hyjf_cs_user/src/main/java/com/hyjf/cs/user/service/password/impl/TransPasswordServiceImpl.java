/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.password.impl;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.service.password.TransPasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author libin
 * @version TransPasswordServiceImpl.java, v0.1 2018年7月19日 上午9:31:11
 */
@Service
public class TransPasswordServiceImpl extends BaseUserServiceImpl implements TransPasswordService{
	
	private static Logger logger = LoggerFactory.getLogger(TransPasswordServiceImpl.class);

    @Autowired
    private AmUserClient amUserClient;
    
    /**
     * 通过手机号获取用户
     * @param
     * @return
     */
	@Override
	public UserVO findUserByMobile(String mobile) {
		UserVO user = this.amUserClient.findUserByMobile(mobile);
		return user;
	}

}
