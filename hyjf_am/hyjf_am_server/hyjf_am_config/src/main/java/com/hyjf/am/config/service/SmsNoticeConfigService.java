/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.SmsNoticeConfig;

/**
 * @author fuqiang
 * @version SmsNoticeConfigService, v0.1 2018/5/8 9:57
 */
public interface SmsNoticeConfigService {
    /**
     * 根据name查询SmsNoticeConfig
     *
     * @param tplCode
     * @return
     */
    SmsNoticeConfig findSmsNoticeByCode(String tplCode);
}
