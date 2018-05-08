/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.SmsTemplateMapper;
import com.hyjf.am.config.dao.model.auto.SmsTemplate;
import com.hyjf.am.config.dao.model.auto.SmsTemplateExample;
import com.hyjf.am.config.redis.RedisUtil;
import com.hyjf.am.config.service.SmsTemplateService;
import com.hyjf.common.constants.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author fuqiang
 * @version SmsTemplateServiceImpl, v0.1 2018/5/8 10:24
 */
@Service
public class SmsTemplateServiceImpl implements SmsTemplateService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SmsTemplateMapper smsTemplateMapper;

    @Override
    public SmsTemplate findSmsTemplateByCode(String tplCode) {
        SmsTemplate smsTemplate = (SmsTemplate) redisUtil.get(RedisKey.SMS_TEMPLATE);
        if (smsTemplate == null) {
            SmsTemplateExample example = new SmsTemplateExample();
            SmsTemplateExample.Criteria criteria = example.createCriteria();
            criteria.andTplCodeEqualTo(tplCode);
            List<SmsTemplate> smsTemplateList = smsTemplateMapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(smsTemplateList)) {
                smsTemplate = smsTemplateList.get(0);
                redisUtil.setEx(RedisKey.SMS_TEMPLATE, smsTemplate, 1, TimeUnit.DAYS);
                return smsTemplate;
            }
        }
        return smsTemplate;
    }
}
