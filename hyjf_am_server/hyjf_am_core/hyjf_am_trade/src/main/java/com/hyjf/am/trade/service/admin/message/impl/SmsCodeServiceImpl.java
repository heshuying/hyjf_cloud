/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.message.impl;

import com.hyjf.am.resquest.admin.SmsCodeUserRequest;
import com.hyjf.am.trade.dao.mapper.customize.admin.SmsCodeCustomizeMapper;
import com.hyjf.am.trade.dao.model.customize.SmsCodeCustomize;
import com.hyjf.am.trade.service.admin.message.SmsCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fq
 * @version SmsCodeServiceImpl, v0.1 2018/8/20 19:07
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
