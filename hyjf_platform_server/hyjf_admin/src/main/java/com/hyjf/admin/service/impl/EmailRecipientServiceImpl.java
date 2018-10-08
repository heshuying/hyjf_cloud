package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.service.EmailRecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lisheng
 * @version EmailRecipientServiceImpl, v0.1 2018/10/8 10:43
 */
@Service
public class EmailRecipientServiceImpl implements EmailRecipientService {
    @Autowired
    AmAdminClient amAdminClient;

}
