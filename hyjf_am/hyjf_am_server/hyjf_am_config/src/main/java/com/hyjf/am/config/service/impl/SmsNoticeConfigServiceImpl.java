/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.SmsNoticeConfigMapper;
import com.hyjf.am.config.dao.model.auto.SmsNoticeConfig;
import com.hyjf.am.config.dao.model.auto.SmsNoticeConfigExample;
import com.hyjf.am.config.redis.RedisUtil;
import com.hyjf.am.config.service.SmsNoticeConfigService;
import com.hyjf.common.constants.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author fuqiang
 * @version SmsNoticeConfigServiceImpl, v0.1 2018/5/8 10:08
 */
@Service
public class SmsNoticeConfigServiceImpl implements SmsNoticeConfigService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SmsNoticeConfigMapper smsNoticeConfigMapper;

    @Override
    public SmsNoticeConfig findSmsNoticeByCode(String tplCode) {
        SmsNoticeConfig smsNoticeConfig = (SmsNoticeConfig) redisUtil.get(RedisKey.SMS_NOTICE_CONFIG);
        if (smsNoticeConfig == null) {
            SmsNoticeConfigExample example = new SmsNoticeConfigExample();
            SmsNoticeConfigExample.Criteria criteria = example.createCriteria();
            criteria.andNameEqualTo(tplCode);
            List<SmsNoticeConfig> smsNoticeConfigList = smsNoticeConfigMapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(smsNoticeConfigList)) {
                smsNoticeConfig = smsNoticeConfigList.get(0);
                redisUtil.setEx(RedisKey.SMS_NOTICE_CONFIG, smsNoticeConfig, 1, TimeUnit.DAYS);
                return smsNoticeConfig;
            }
        }
        return smsNoticeConfig;
    }
}
