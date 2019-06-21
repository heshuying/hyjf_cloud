/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.request.JcCustomerServerRequest;
import com.hyjf.admin.client.CsMessageClient;
import com.hyjf.admin.service.JcCustomerService;
import com.hyjf.am.response.admin.CustomerServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yaoyong
 * @version JcCustomerServiceImpl, v0.1 2019/6/21 11:04
 */
@Service
public class JcCustomerServiceImpl implements JcCustomerService {

    @Autowired
    private CsMessageClient csMessageClient;

    @Override
    public CustomerServerResponse getServerList(JcCustomerServerRequest request) {
        return csMessageClient.getServerList(request);
    }
}
