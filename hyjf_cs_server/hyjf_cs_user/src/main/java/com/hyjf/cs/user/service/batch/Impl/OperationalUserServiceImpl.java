/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.batch.Impl;

import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.service.batch.OperationalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yaoyong
 * @version OperationalUserServiceImpl, v0.1 2018/12/18 17:24
 */
@Service
public class OperationalUserServiceImpl implements OperationalUserService {

    @Autowired
    private AmUserClient amUserClient;

    @Override
    public void countRegist() {
        amUserClient.countRegist();
    }

    @Override
    public void fddCertificate() {
        amUserClient.fddCertificate();
    }
}
