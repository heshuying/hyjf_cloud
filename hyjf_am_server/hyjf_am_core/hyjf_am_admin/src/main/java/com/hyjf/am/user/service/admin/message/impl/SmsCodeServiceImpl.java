/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.message.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.resquest.admin.SmsCodeUserRequest;
import com.hyjf.am.user.dao.mapper.customize.SmsCodeCustomizeMapper;
import com.hyjf.am.user.dao.model.customize.SmsCodeCustomize;
import com.hyjf.am.user.service.admin.message.SmsCodeService;

/**
 * @author fq
 * @version SmsCodeServiceImpl, v0.1 2018/8/20 20:30
 */
@Service
public class SmsCodeServiceImpl implements SmsCodeService {
    @Autowired
    private SmsCodeCustomizeMapper smsCodeCustomizeMapper;

    @Override
    public List<SmsCodeCustomize> queryUser(SmsCodeUserRequest request) {
        return smsCodeCustomizeMapper.queryUser(request);
    }
}
