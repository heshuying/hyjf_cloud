/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.MailTemplateService;
import com.hyjf.am.resquest.config.MailTemplateRequest;
import com.hyjf.am.vo.config.SmsMailTemplateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fuqiang
 * @version MailTemplateServiceImpl, v0.1 2018/6/25 14:48
 */
@Service
public class MailTemplateServiceImpl implements MailTemplateService {
    @Autowired
    private AmConfigClient amConfigClient;

    @Override
    public List<SmsMailTemplateVO> findAll() {
        return amConfigClient.findMailAll();
    }

    @Override
    public List<SmsMailTemplateVO> findMailTemplate(MailTemplateRequest request) {
        return amConfigClient.findMailTemplate(request);
    }

    @Override
    public void insertMailTemplate(MailTemplateRequest request) {
        amConfigClient.insertMailTemplate(request);
    }

    @Override
    public void updateMailTemplate(MailTemplateRequest request) {
        amConfigClient.updateMailTemplate(request);
    }

    @Override
    public void closeAction(MailTemplateRequest request) {
        amConfigClient.closeAction(request);
    }

    @Override
    public void openAction(MailTemplateRequest request) {
        amConfigClient.openAction(request);
    }
}
