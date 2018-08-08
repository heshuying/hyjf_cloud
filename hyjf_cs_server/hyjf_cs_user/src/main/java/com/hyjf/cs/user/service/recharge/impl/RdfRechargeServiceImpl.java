package com.hyjf.cs.user.service.recharge.impl;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.service.recharge.RdfRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RdfRechargeServiceImpl extends BaseUserServiceImpl implements RdfRechargeService {

    @Autowired
    private AmUserClient amUserClient;

    /**
     * 通过手机号获取用户
     * @param mobile
     * @return
     */
	@Override
	public UserVO findUserByMobile(String mobile) {
        return amUserClient.findUserByMobile(mobile);
	}

}
