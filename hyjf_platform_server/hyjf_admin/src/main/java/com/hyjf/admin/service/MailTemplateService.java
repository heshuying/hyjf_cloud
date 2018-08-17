/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.resquest.config.MailTemplateRequest;
import com.hyjf.am.vo.config.SmsMailTemplateVO;

import java.util.List;

/**
 * @author fuqiang
 * @version MailTemplateClient, v0.1 2018/6/25 14:47
 */
public interface MailTemplateService {
    /**
     * 查询所有邮件模板
     *
     * @return
     */
    List<SmsMailTemplateVO> findAll();

    /**
     * 根据条件查询邮件模板
     *
     * @param request
     * @return
     */
    List<SmsMailTemplateVO> findMailTemplate(MailTemplateRequest request);

    /**
     * 新增邮件模板
     *
     * @param request
     * @return
     */
    void insertMailTemplate(MailTemplateRequest request);

    /**
     * 修改邮件模板
     * @param request
     */
    void updateMailTemplate(MailTemplateRequest request);

    /**
     * 关闭模板
     * @param request
     */
    void closeAction(MailTemplateRequest request);

    /**
     * 开启模板
     * @param request
     */
    void openAction(MailTemplateRequest request);
}
