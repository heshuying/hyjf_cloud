/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.impl;

import com.hyjf.am.resquest.message.JcCustomerServerRequest;
import com.hyjf.cs.message.bean.ic.JcCustomerService;
import com.hyjf.cs.message.mongo.ic.JcCustomerServiceDao;
import com.hyjf.cs.message.service.JcCustomerServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yaoyong
 * @version JcCustomerServerServiceImpl, v0.1 2019/6/24 10:05
 */
@Service
public class JcCustomerServerServiceImpl implements JcCustomerServerService {

    @Autowired
    private JcCustomerServiceDao customerServiceDao;

    @Override
    public long countServerList(JcCustomerServerRequest request) {
        return customerServiceDao.countServerList(request);
    }

    @Override
    public List<JcCustomerService> getServerList(JcCustomerServerRequest request) {
        return customerServiceDao.getServerList(request);
    }

    @Override
    public Integer insertServer(JcCustomerService customerService) {
        customerServiceDao.save(customerService);
        return 1;
    }

    @Override
    public JcCustomerService getCustomerServer(String id) {
        return customerServiceDao.getCustomerServer(id);
    }

    @Override
    public Integer updateCustomerServer(JcCustomerService customerService) {
        customerServiceDao.save(customerService);
        return 1;
    }

    @Override
    public JcCustomerService getCustomerService() {
        return customerServiceDao.getCustomerService();
    }
}
