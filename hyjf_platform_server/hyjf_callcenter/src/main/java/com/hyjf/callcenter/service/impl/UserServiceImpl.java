package com.hyjf.callcenter.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.beans.UserBean;
import com.hyjf.callcenter.client.AmCallcenterBaseClient;
import com.hyjf.callcenter.service.UserService;

/**
 * @author libin
 * @version UserServiceImpl, v0.1 2018/6/5
 */
@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private AmCallcenterBaseClient amCallcenterBaseClient;

	@Override
	public UserVO getUser(UserBean bean) {
		return amCallcenterBaseClient.getUser(bean);
	}
}
