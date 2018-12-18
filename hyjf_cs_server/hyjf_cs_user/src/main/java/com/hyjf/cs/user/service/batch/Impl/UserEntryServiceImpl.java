/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.batch.Impl;

import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.service.batch.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yaoyong
 * @version UserEntryServiceImpl, v0.1 2018/12/18 17:31
 */
@Service
public class UserEntryServiceImpl implements UserEntryService {

    @Autowired
    private AmUserClient amUserClient;

    @Override
    public void entryUpdate() {
        amUserClient.updateEntey();
    }

    @Override
    public void leaveUpdate() {
        amUserClient.updateUserLeave();
    }
}
