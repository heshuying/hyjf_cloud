package com.hyjf.wbs.user.dao.auto;

import com.hyjf.wbs.user.dao.mapper.auto.BankOpenAccountLogMapper;
import com.hyjf.wbs.user.dao.mapper.auto.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("amUserAutoMapper")
public class AutoMapper {

    @Autowired
    protected BankOpenAccountLogMapper bankOpenAccountLogMapper;

    @Autowired
    protected UserInfoMapper userInfoMapper;


}

