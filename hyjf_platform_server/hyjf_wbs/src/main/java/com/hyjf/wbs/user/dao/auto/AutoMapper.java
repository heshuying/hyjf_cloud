package com.hyjf.wbs.user.dao.auto;

import com.hyjf.wbs.user.dao.mapper.auto.BankOpenAccountLogMapper;
import com.hyjf.wbs.user.dao.mapper.auto.UserInfoMapper;
import com.hyjf.wbs.user.dao.mapper.auto.UtmRegMapper;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("amUserAutoMapper")
public class AutoMapper {

    @Autowired
    protected BankOpenAccountLogMapper bankOpenAccountLogMapper;

    @Autowired
    protected UserInfoMapper userInfoMapper;

    @Autowired
    protected UtmRegMapper utmRegMapper;
}

