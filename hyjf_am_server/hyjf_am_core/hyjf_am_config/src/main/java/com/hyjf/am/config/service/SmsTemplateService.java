/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.SmsTemplate;

/**
 * @author fuqiang
 * @version SmsTemplateService, v0.1 2018/5/8 10:23
 */
public interface SmsTemplateService {
    /**
     * 根据tplCode查询短信模板
     *
     * @param tplCode
     * @return
     */
    SmsTemplate findSmsTemplateByCode(String tplCode);
}
