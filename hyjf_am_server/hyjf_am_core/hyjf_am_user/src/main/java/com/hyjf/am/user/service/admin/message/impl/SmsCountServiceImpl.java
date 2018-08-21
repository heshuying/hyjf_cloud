/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.message.impl;

import com.hyjf.am.resquest.user.SmsCountRequest;
import com.hyjf.am.user.dao.mapper.customize.SmsCountCustomizeMapper;
import com.hyjf.am.user.dao.model.customize.OADepartmentCustomize;
import com.hyjf.am.user.dao.model.customize.SmsCountCustomize;
import com.hyjf.am.user.service.admin.message.SmsCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fq
 * @version SmsCountServiceImpl, v0.1 2018/8/20 16:28
 */
@Service
public class SmsCountServiceImpl implements SmsCountService {
    @Autowired
    private SmsCountCustomizeMapper smsCountCustomizeMapper;

    @Override
    public List<SmsCountCustomize> querySmsCountLlist(SmsCountRequest request) {
        return smsCountCustomizeMapper.querySmsCountLlist(request);
    }

    @Override
    public Integer querySmsCountNumberTotal(SmsCountRequest request) {
        return smsCountCustomizeMapper.querySmsCountNumberTotal(request);
    }

    @Override
    public List<OADepartmentCustomize> queryDepartmentInfo() {
        return smsCountCustomizeMapper.queryDepartmentInfo();
    }
}
