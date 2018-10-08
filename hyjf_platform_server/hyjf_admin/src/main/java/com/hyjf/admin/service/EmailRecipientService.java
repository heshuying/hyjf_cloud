package com.hyjf.admin.service;

import com.hyjf.am.response.EmailRecipientResponse;
import com.hyjf.am.resquest.admin.EmailRecipientRequest;

/**
 * @author lisheng
 * @version EmailRecipientService, v0.1 2018/10/8 10:42
 */

public interface EmailRecipientService {
    /**
     * 查询邮件配置列表数据
     *
     * @return
     */
    EmailRecipientResponse getRecordList(EmailRecipientRequest recipientRequest);
    /**
     * 查询邮件配置列表数据详情
     *
     * @return
     */
    EmailRecipientResponse getRecordById(EmailRecipientRequest recipientRequest);
    /**
     * 修改邮件配置
     * @return
     */
    EmailRecipientResponse updateEmailRecipient(EmailRecipientRequest recipientRequest);
    /**
     * 禁用邮件配置状态
     * @return
     */
    EmailRecipientResponse forbiddenAction(EmailRecipientRequest recipientRequest);
    /**
     * 添加邮件配置
     * @return
     */
    EmailRecipientResponse insertAction(EmailRecipientRequest recipientRequest);


}
