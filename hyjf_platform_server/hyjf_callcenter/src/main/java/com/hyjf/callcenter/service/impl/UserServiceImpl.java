package com.hyjf.callcenter.service.impl;

import com.hyjf.am.vo.trade.RUserVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.beans.UserBean;
import com.hyjf.callcenter.client.AmTradeClient;
import com.hyjf.callcenter.client.AmUserClient;
import com.hyjf.callcenter.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author libin
 * @version UserServiceImpl, v0.1 2018/6/5
 */
@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
	private AmTradeClient amTradeClient;

	@Override
	public UserVO getUser(UserBean bean) {
		return amUserClient.getUser(bean);
	}

	/**
	 * 根据用户ID查询推荐人信息
	 * @param userId
	 * @return
	 */
	@Override
	public RUserVO getRefereerInfoByUserId(Integer userId){
		return amTradeClient.getRefereerInfoByUserId(userId);
	}
}
