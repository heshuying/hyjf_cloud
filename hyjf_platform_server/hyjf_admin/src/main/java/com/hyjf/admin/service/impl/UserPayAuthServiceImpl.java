/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.UserPayAuthService;
import com.hyjf.am.response.user.UserPayAuthResponse;
import com.hyjf.am.resquest.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * @author nixiaoling
 * @version UserCenterServiceImpl, v0.1 2018/6/20 15:36
 */
@Service
public class UserPayAuthServiceImpl extends BaseServiceImpl implements UserPayAuthService {


    @Autowired
    private AmUserClient amUserClient;
    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    private AmConfigClient amConfigClient;


    /**
     * 查找用户信息
     *
     * @param request
     * @return
     */
    @Override
    public UserPayAuthResponse selectUserMemberList(UserPayAuthRequest request) {
        return amUserClient.selectUserPayAuthList(request);
    }

}
