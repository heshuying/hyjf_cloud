/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.SmsMailTemplate;

/**
 * @author fuqiang
 * @version SmsMailTemplateService, v0.1 2018/5/8 16:57
 */
public interface SmsMailTemplateService {
    /**
     * 查询邮件模板
     * @param mailCode
     * @return
     */
    SmsMailTemplate findSmsMailTemplateByCode(String mailCode);
}
