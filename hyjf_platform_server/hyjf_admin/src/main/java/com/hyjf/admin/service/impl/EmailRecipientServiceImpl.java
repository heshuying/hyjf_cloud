package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.service.EmailRecipientService;
import com.hyjf.am.response.EmailRecipientResponse;
import com.hyjf.am.resquest.admin.EmailRecipientRequest;
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

    @Override
    public EmailRecipientResponse getRecordList(EmailRecipientRequest recipientRequest) {
        return amAdminClient.getRecordList(recipientRequest);
    }

    @Override
    public EmailRecipientResponse getRecordById(EmailRecipientRequest recipientRequest) {
        return amAdminClient.getRecordById(recipientRequest);
    }

    @Override
    public EmailRecipientResponse updateEmailRecipient(EmailRecipientRequest recipientRequest) {
        return  amAdminClient.updateEmailRecipient(recipientRequest);
    }

    @Override
    public EmailRecipientResponse forbiddenAction(EmailRecipientRequest recipientRequest) {
        return amAdminClient.forbiddenAction(recipientRequest);
    }

    @Override
    public EmailRecipientResponse insertAction(EmailRecipientRequest recipientRequest) {
        return amAdminClient.insertAction(recipientRequest);
    }
}
