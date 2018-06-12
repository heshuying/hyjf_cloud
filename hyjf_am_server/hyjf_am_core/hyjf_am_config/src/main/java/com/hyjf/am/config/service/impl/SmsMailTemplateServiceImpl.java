/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.SmsMailTemplateMapper;
import com.hyjf.am.config.dao.model.auto.SmsMailTemplate;
import com.hyjf.am.config.dao.model.auto.SmsMailTemplateExample;
import com.hyjf.am.config.service.SmsMailTemplateService;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author fuqiang
 * @version SmsMailTemplateServiceImpl, v0.1 2018/5/8 16:58
 */
@Service
public class SmsMailTemplateServiceImpl implements SmsMailTemplateService {

    @Autowired
    private SmsMailTemplateMapper smsMailTemplateMapper;

    @Override
    public SmsMailTemplate findSmsMailTemplateByCode(String mailCode) {
        SmsMailTemplate smsMailTemplate = RedisUtils.getObj(RedisKey.SMS_MAIL_TEMPLATE, SmsMailTemplate.class);
        if (smsMailTemplate == null) {
            SmsMailTemplateExample example = new SmsMailTemplateExample();
            example.createCriteria().andMailValueEqualTo(mailCode);
            List<SmsMailTemplate> smsMailTemplateList = smsMailTemplateMapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(smsMailTemplateList)) {
                smsMailTemplate = smsMailTemplateList.get(0);
                RedisUtils.setObjEx(RedisKey.SMS_MAIL_TEMPLATE, smsMailTemplate, 24*60*60);
                return smsMailTemplate;
            }
        }
        return null;
    }
}
