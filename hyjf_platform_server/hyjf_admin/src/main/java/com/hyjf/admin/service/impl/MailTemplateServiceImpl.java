/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.service.MailTemplateService;
import com.hyjf.am.resquest.config.MailTemplateRequest;
import com.hyjf.am.vo.config.SmsMailTemplateVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fuqiang
 * @version MailTemplateServiceImpl, v0.1 2018/6/25 14:48
 */
@Service
public class MailTemplateServiceImpl implements MailTemplateService {
    @Override
    public List<SmsMailTemplateVO> findAll() {
        return null;
    }

    @Override
    public List<SmsMailTemplateVO> findMailTemplate(MailTemplateRequest request) {
        return null;
    }

    @Override
    public List<SmsMailTemplateVO> insertMailTemplate(MailTemplateRequest request) {
        return null;
    }
}
