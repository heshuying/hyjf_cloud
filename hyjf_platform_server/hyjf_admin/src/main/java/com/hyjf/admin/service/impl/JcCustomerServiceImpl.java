/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.CsMessageClient;
import com.hyjf.admin.service.JcCustomerService;
import com.hyjf.am.response.admin.CustomerServerResponse;
import com.hyjf.am.resquest.message.JcCustomerServerRequest;
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

    @Override
    public CustomerServerResponse addCustomerServer(JcCustomerServerRequest request) {
        return csMessageClient.addCustomerServer(request);
    }

    @Override
    public CustomerServerResponse getCustomerServer(String id) {
        return csMessageClient.getCustomerServer(id);
    }

    @Override
    public CustomerServerResponse updateCustomerServer(JcCustomerServerRequest request) {
        return csMessageClient.updateCustomerServer(request);
    }
}
