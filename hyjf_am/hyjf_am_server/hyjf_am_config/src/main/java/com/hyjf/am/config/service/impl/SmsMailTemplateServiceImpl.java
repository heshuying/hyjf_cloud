/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.SmsMailTemplateMapper;
import com.hyjf.am.config.dao.model.auto.SmsMailTemplate;
import com.hyjf.am.config.dao.model.auto.SmsMailTemplateExample;
import com.hyjf.am.config.redis.RedisUtil;
import com.hyjf.am.config.service.SmsMailTemplateService;
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
    private RedisUtil redisUtil;

    @Autowired
    private SmsMailTemplateMapper smsMailTemplateMapper;

    @Override
    public SmsMailTemplate findSmsMailTemplateByCode(String mailCode) {
        SmsMailTemplate smsMailTemplate = (SmsMailTemplate) redisUtil.get(RedisKey.SMS_MAIL_TEMPLATE);
        if (smsMailTemplate == null) {
            SmsMailTemplateExample example = new SmsMailTemplateExample();
            example.createCriteria().andMailValueEqualTo(mailCode);
            List<SmsMailTemplate> smsMailTemplateList = smsMailTemplateMapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(smsMailTemplateList)) {
                smsMailTemplate = smsMailTemplateList.get(0);
                redisUtil.setEx(RedisKey.SMS_MAIL_TEMPLATE, smsMailTemplate, 1, TimeUnit.DAYS);
                return smsMailTemplate;
            }
        }
        return null;
    }
}
