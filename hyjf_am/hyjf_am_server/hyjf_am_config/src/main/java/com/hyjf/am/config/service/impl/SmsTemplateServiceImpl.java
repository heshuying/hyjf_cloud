/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.SmsTemplateMapper;
import com.hyjf.am.config.dao.model.auto.SmsTemplate;
import com.hyjf.am.config.dao.model.auto.SmsTemplateExample;
import com.hyjf.am.config.service.SmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author fuqiang
 * @version SmsTemplateServiceImpl, v0.1 2018/5/8 10:24
 */
@Service
public class SmsTemplateServiceImpl implements SmsTemplateService {

    @Autowired
    private SmsTemplateMapper smsTemplateMapper;

    @Override
    public SmsTemplate findSmsTemplateByCode(String tplCode) {
        SmsTemplateExample example = new SmsTemplateExample();
        SmsTemplateExample.Criteria criteria = example.createCriteria();
        criteria.andTplCodeEqualTo(tplCode);
        List<SmsTemplate> smsTemplateList = smsTemplateMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(smsTemplateList)) {
            return smsTemplateList.get(0);
        }
        return null;
    }
}
