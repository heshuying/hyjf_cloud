package com.hyjf.callcenter.serviceImpl;

import com.hyjf.callcenter.beans.UserBean;
import com.hyjf.callcenter.beans.Users;
import com.hyjf.callcenter.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
/**
 * @author libin
 * @version UserServiceImpl, v0.1 2018/6/5
 */
@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public Users getUser(UserBean bean) {
		// TODO Auto-generated method stub
		return null;
	}
}
